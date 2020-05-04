package io.remedymatch.bedarf.domain.service;

import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageJpaRepository;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.usercontext.UserContextService;
import io.remedymatch.usercontext.UserContextTestFixtures;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static io.remedymatch.bedarf.domain.service.BedarfTestFixtures.beispielBedarf;
import static io.remedymatch.bedarf.domain.service.BedarfTestFixtures.beispielBedarfEntity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
        BedarfSucheService.class, //
        BedarfJpaRepository.class, //
        UserContextService.class, //
        BedarfAnfrageSucheService.class, //
        BedarfAnfrageJpaRepository.class, //
        GeoCalcService.class //
})
@Tag("Spring")
@DisplayName("BedarfSucheService soll")
class BedarfSucheServiceShould {

    @Autowired
    private BedarfSucheService bedarfSucheService;

    @MockBean
    private BedarfJpaRepository bedarfRepository;

    @MockBean
    private BedarfAnfrageJpaRepository bedarfAnfrageJpaRepository;

    @MockBean
    private UserContextService userService;

    @MockBean
    private GeoCalcService geoCalcService;

    @Test
    @DisplayName("alle nicht bediente Bedarfe zurueckliefern")
    void alle_nicht_bediente_Bedarfe_zurueckliefern() {

        val bedarf1Entfernung = BigDecimal.valueOf(100);
        val bedarf2Entfernung = BigDecimal.valueOf(500);

        val bedarf1Entity = beispielBedarfEntity();
        val bedarf1 = beispielBedarf();
        bedarf1.setEntfernung(bedarf1Entfernung);

        val bedarf2Id = new BedarfId(UUID.randomUUID());
        val bedarf2Entity = beispielBedarfEntity();
        bedarf2Entity.setId(bedarf2Id.getValue());
        val bedarf2 = beispielBedarf();
        bedarf2.setId(bedarf2Id);
        bedarf2.setEntfernung(bedarf2Entfernung);

        given(bedarfRepository.findAllByDeletedFalseAndBedientFalse())
                .willReturn(Arrays.asList(bedarf1Entity, bedarf2Entity));
        given(geoCalcService.berechneUserDistanzInKilometer(bedarf1.getStandort())).willReturn(bedarf1Entfernung);
        given(geoCalcService.berechneUserDistanzInKilometer(bedarf2.getStandort())).willReturn(bedarf2Entfernung);

        assertThat(//
                bedarfSucheService.findAlleNichtBedienteBedarfe(), //
                containsInAnyOrder(bedarf1, bedarf2));

        then(bedarfRepository).should().findAllByDeletedFalseAndBedientFalse();
        then(bedarfRepository).shouldHaveNoMoreInteractions();
        then(userService).shouldHaveNoInteractions();
        // XXX 2 verschiedene Aufrufe pruefen...
        then(geoCalcService).should(times(2)).berechneUserDistanzInKilometer(any());
        then(geoCalcService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("alle nicht bediente Bedarfe der UserContext Institution zurueckliefern")
    void alle_nicht_bediente_Bedarfe_der_UserContext_Institution_zurueckliefern() {

        val userContextInstitution = UserContextTestFixtures.beispielUserContextInstitution();

        val bedarf1Entfernung = BigDecimal.valueOf(100);
        val bedarf2Entfernung = BigDecimal.valueOf(500);

        val bedarf1Entity = beispielBedarfEntity();
        val bedarf1 = beispielBedarf();
        bedarf1.setEntfernung(bedarf1Entfernung);

        val bedarf2Id = new BedarfId(UUID.randomUUID());
        val bedarf2Entity = beispielBedarfEntity();
        bedarf2Entity.setId(bedarf2Id.getValue());
        val bedarf2 = beispielBedarf();
        bedarf2.setId(bedarf2Id);
        bedarf2.setEntfernung(bedarf2Entfernung);

        given(bedarfRepository
                .findAllByDeletedFalseAndBedientFalseAndInstitution_Id(userContextInstitution.getId().getValue()))
                .willReturn(Arrays.asList(bedarf1Entity, bedarf2Entity));
        given(userService.getContextInstitution()).willReturn(userContextInstitution);
        given(geoCalcService.berechneUserDistanzInKilometer(bedarf1.getStandort())).willReturn(bedarf1Entfernung);
        given(geoCalcService.berechneUserDistanzInKilometer(bedarf2.getStandort())).willReturn(bedarf2Entfernung);

        assertEquals(Arrays.asList(bedarf1, bedarf2),
                bedarfSucheService.findAlleNichtBedienteBedarfeDerUserInstitution());

        then(bedarfRepository).should()
                .findAllByDeletedFalseAndBedientFalseAndInstitution_Id(userContextInstitution.getId().getValue());
        then(bedarfRepository).shouldHaveNoMoreInteractions();
        then(userService).should().getContextInstitution();
        then(userService).shouldHaveNoMoreInteractions();
        // XXX 2 verschiedene Aufrufe pruefen...
        then(geoCalcService).should(times(2)).berechneUserDistanzInKilometer(any());
        then(userService).shouldHaveNoMoreInteractions();
    }
}
