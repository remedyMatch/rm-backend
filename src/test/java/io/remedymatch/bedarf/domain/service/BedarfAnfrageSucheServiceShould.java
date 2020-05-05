package io.remedymatch.bedarf.domain.service;

import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageJpaRepository;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.geodaten.domain.GeocodingService;
import io.remedymatch.usercontext.UserContextService;
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

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static io.remedymatch.bedarf.domain.service.BedarfAnfrageTestFixtures.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
        BedarfAnfrageSucheService.class, //
        BedarfAnfrageJpaRepository.class,
        UserContextService.class, //
        GeocodingService.class //
})
@Tag("Spring")
@DisplayName("BedarfAnfrageSucheService soll")
class BedarfAnfrageSucheServiceShould {

    @Autowired
    private BedarfAnfrageSucheService anfrageSucheService;

    @MockBean
    private BedarfAnfrageJpaRepository anfrageRepository;

    @MockBean
    GeocodingService geocodingService;

    @Test
    @DisplayName("alle Anfragen der Institution zurueckliefern")
    void alle_Anfragen_der_Institution_zurueckliefern() {

        val anfrage1Entity = beispielBedarfAnfrageEntity();
        val anfrage1 = beispielBedarfAnfrage();

        val anfrage2Id = new BedarfAnfrageId(UUID.randomUUID());
        val anfrage2Entity = beispielBedarfAnfrageEntity();
        anfrage2Entity.setId(anfrage2Id.getValue());
        val anfrage2 = BedarfAnfrageTestFixtures.beispielBedarfAnfrage();
        anfrage2.setId(anfrage2Id);

        val anfrageInstitutionId = anfrage1.getInstitution().getId();

        given(anfrageRepository.findAllByInstitution_Id(anfrageInstitutionId.getValue()))
                .willReturn(Arrays.asList(anfrage1Entity, anfrage2Entity));

        assertThat(//
                anfrageSucheService.findAlleAnfragenDerInstitution(anfrageInstitutionId), //
                containsInAnyOrder(anfrage1, anfrage2));

        then(anfrageRepository).should().findAllByInstitution_Id(anfrageInstitutionId.getValue());
        then(anfrageRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("alle Anfragen der Bedarf Institution zurueckliefern")
    void alle_Anfragen_der_Bedarf_Institution_zurueckliefern() {

        val anfrage1Entity = beispielBedarfAnfrageEntity();
        val anfrage1 = beispielBedarfAnfrage();

        val anfrage2Id = new BedarfAnfrageId(UUID.randomUUID());
        val anfrage2Entity = beispielBedarfAnfrageEntity();
        anfrage2Entity.setId(anfrage2Id.getValue());
        val anfrage2 = BedarfAnfrageTestFixtures.beispielBedarfAnfrage();
        anfrage2.setId(anfrage2Id);

        val bedarfInstitutionId = anfrage1.getBedarf().getInstitution().getId();

        given(anfrageRepository.findAllByBedarf_Institution_Id(bedarfInstitutionId.getValue()))
                .willReturn(Arrays.asList(anfrage1Entity, anfrage2Entity));

        assertThat(//
                anfrageSucheService.findAlleAnfragenDerBedarfInstitution(bedarfInstitutionId), //
                containsInAnyOrder(anfrage1, anfrage2));

        then(anfrageRepository).should().findAllByBedarf_Institution_Id(bedarfInstitutionId.getValue());
        then(anfrageRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("gesuchte Anfrage finden")
    void gesuchte_Anfrage_finden() {

        val anfrageId = beispielBedarfAnfrageId();
        val anfrageEntity = beispielBedarfAnfrageEntity();

        given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

        val expectedAnfrage = beispielBedarfAnfrage();

        assertEquals(Optional.of(expectedAnfrage), anfrageSucheService.findAnfrage(anfrageId));

        then(anfrageRepository).should().findById(anfrageId.getValue());
        then(anfrageRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("eine ObjectNotFoundException werfen wenn gesuchte Anfrage nicht existiert")
    void eine_ObjectNotFoundException_werfen_wenn_gesuchtes_Artikel_nicht_existiert() {
        assertThrows(ObjectNotFoundException.class,
                () -> anfrageSucheService.getAnfrageOrElseThrow(beispielBedarfAnfrageId()));
    }

    @Test
    @DisplayName("gesuchte Anfrage  zurueckliefern")
    void gesuchte_Anfrage_zurueckliefern() {

        val anfrageId = beispielBedarfAnfrageId();
        val anfrageEntity = beispielBedarfAnfrageEntity();

        given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

        val expectedAnfrage = beispielBedarfAnfrage();

        assertEquals(expectedAnfrage, anfrageSucheService.getAnfrageOrElseThrow(anfrageId));

        then(anfrageRepository).should().findById(anfrageId.getValue());
        then(anfrageRepository).shouldHaveNoMoreInteractions();
    }
}
