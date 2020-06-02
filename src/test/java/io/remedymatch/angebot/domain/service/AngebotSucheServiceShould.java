package io.remedymatch.angebot.domain.service;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.geodaten.domain.GeocodingService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static io.remedymatch.angebot.domain.service.AngebotTestFixtures.beispielAngebot;
import static io.remedymatch.angebot.domain.service.AngebotTestFixtures.beispielAngebotEntity;
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
        AngebotSucheService.class, //
        AngebotJpaRepository.class, //
        UserContextService.class, //
        AngebotAnfrageSucheService.class, //
        GeocodingService.class //
})
@Tag("Spring")
@DisplayName("AngebotSucheService soll")
class AngebotSucheServiceShould {

    @Autowired
    private AngebotSucheService angebotSucheService;

    @MockBean
    private AngebotJpaRepository angebotRepository;

    @MockBean
    private UserContextService userService;

    @MockBean
    private GeocodingService geoCalcService;

    @MockBean
    private AngebotAnfrageSucheService angebotAnfrageSucheService;

    @Test
    @DisplayName("alle nicht bediente offentliche Angebote zurueckliefern")
    void alle_nicht_bediente_oeffentliche_Angebote_zurueckliefern() {

        val angebot1Entfernung = BigDecimal.valueOf(100);
        val angebot2Entfernung = BigDecimal.valueOf(500);

        val angebot1Entity = beispielAngebotEntity();
        val angebot1 = beispielAngebot();
        angebot1.setEntfernung(angebot1Entfernung);

        val angebot2Id = new AngebotId(UUID.randomUUID());
        val angebot2Entity = beispielAngebotEntity();
        angebot2Entity.setId(angebot2Id.getValue());
        val angebot2 = beispielAngebot();
        angebot2.setId(angebot2Id);
        angebot2.setEntfernung(angebot2Entfernung);

        given(angebotRepository.findAllByDeletedFalseAndBedientFalseAndOeffentlichTrue())
                .willReturn(Arrays.asList(angebot1Entity, angebot2Entity));
        given(geoCalcService.berechneUserDistanzInKilometer(angebot1.getStandort())).willReturn(angebot1Entfernung);
        given(geoCalcService.berechneUserDistanzInKilometer(angebot2.getStandort())).willReturn(angebot2Entfernung);

        assertThat(//
                angebotSucheService.findAlleNichtBedienteOeffentlicheAngebote(null, true), //
                containsInAnyOrder(angebot1, angebot2));

        then(angebotRepository).should().findAllByDeletedFalseAndBedientFalseAndOeffentlichTrue();
        then(angebotRepository).shouldHaveNoMoreInteractions();
        then(userService).should().getContextInstitutionId();
        then(userService).shouldHaveNoMoreInteractions();
        // XXX 2 verschiedene Aufrufe pruefen...
        then(geoCalcService).should(times(2)).berechneUserDistanzInKilometer(any());
        then(geoCalcService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("alle nicht bediente offentliche Angebote der ArtikelVariante zurueckliefern")
    void alle_nicht_bediente_oeffentliche_Angebote_der_ArtikelVariante_zurueckliefern() {

        val angebot1Entfernung = BigDecimal.valueOf(100);
        val angebot2Entfernung = BigDecimal.valueOf(500);

        val angebot1Entity = beispielAngebotEntity();
        val angebot1 = beispielAngebot();
        angebot1.setEntfernung(angebot1Entfernung);

        val angebot2Id = new AngebotId(UUID.randomUUID());
        val angebot2Entity = beispielAngebotEntity();
        angebot2Entity.setId(angebot2Id.getValue());
        val angebot2 = beispielAngebot();
        angebot2.setId(angebot2Id);
        angebot2.setEntfernung(angebot2Entfernung);

        val artikelVarianteId = angebot1.getArtikelVariante().getId();

        given(angebotRepository.findAllByDeletedFalseAndBedientFalseAndOeffentlichTrueAndArtikelVariante_Id(
                artikelVarianteId.getValue())).willReturn(Arrays.asList(angebot1Entity, angebot2Entity));
        given(geoCalcService.berechneUserDistanzInKilometer(angebot1.getStandort())).willReturn(angebot1Entfernung);
        given(geoCalcService.berechneUserDistanzInKilometer(angebot2.getStandort())).willReturn(angebot2Entfernung);

        assertThat(//
                angebotSucheService.findAlleNichtBedienteOeffentlicheAngebote(artikelVarianteId, true), //
                containsInAnyOrder(angebot1, angebot2));

        then(angebotRepository).should().findAllByDeletedFalseAndBedientFalseAndOeffentlichTrueAndArtikelVariante_Id(
                artikelVarianteId.getValue());
        then(angebotRepository).shouldHaveNoMoreInteractions();
        then(userService).should().getContextInstitutionId();
        then(userService).shouldHaveNoMoreInteractions();
        // XXX 2 verschiedene Aufrufe pruefen...
        then(geoCalcService).should(times(2)).berechneUserDistanzInKilometer(any());
        then(geoCalcService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("alle nicht bediente Angebote der UserContext Institution zurueckliefern")
    void alle_nicht_bediente_Angebote_der_UserContext_Institution_zurueckliefern() {

        val userContextStandort = UserContextTestFixtures.beispielUserContextPerson().getAktuellesStandort();
        val userContextInstitution = userContextStandort.getInstitution();

        val angebot1Entfernung = BigDecimal.valueOf(100);
        val angebot2Entfernung = BigDecimal.valueOf(500);

        val angebot1Entity = beispielAngebotEntity();
        val angebot1 = beispielAngebot();
        angebot1.setEntfernung(angebot1Entfernung);

        val angebot2Id = new AngebotId(UUID.randomUUID());
        val angebot2Entity = beispielAngebotEntity();
        angebot2Entity.setId(angebot2Id.getValue());
        val angebot2 = beispielAngebot();
        angebot2.setId(angebot2Id);
        angebot2.setEntfernung(angebot2Entfernung);

        given(angebotAnfrageSucheService.findeAlleOffenenAnfragenFuerAngebotIds(any()))
                .willReturn(new ArrayList<AngebotAnfrage>());

        given(angebotRepository
                .findAllByDeletedFalseAndBedientFalseAndInstitution_Id(userContextInstitution.getId().getValue()))
                .willReturn(Arrays.asList(angebot1Entity, angebot2Entity));
        given(userService.getContextInstitutionId()).willReturn(userContextInstitution.getId());
        given(geoCalcService.berechneUserDistanzInKilometer(angebot1.getStandort())).willReturn(angebot1Entfernung);
        given(geoCalcService.berechneUserDistanzInKilometer(angebot2.getStandort())).willReturn(angebot2Entfernung);

        assertEquals(Arrays.asList(angebot1, angebot2),
                angebotSucheService.findAlleNichtBedienteAngeboteDerUserInstitution());

        then(angebotRepository).should()
                .findAllByDeletedFalseAndBedientFalseAndInstitution_Id(userContextInstitution.getId().getValue());
        then(angebotRepository).shouldHaveNoMoreInteractions();
        then(userService).should().getContextInstitutionId();
        then(userService).shouldHaveNoMoreInteractions();
        // XXX 2 verschiedene Aufrufe pruefen...
        then(geoCalcService).should(times(2)).berechneUserDistanzInKilometer(any());
        then(userService).shouldHaveNoMoreInteractions();
    }
}
