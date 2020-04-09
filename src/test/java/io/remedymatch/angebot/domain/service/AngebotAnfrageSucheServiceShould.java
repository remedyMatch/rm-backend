package io.remedymatch.angebot.domain.service;

import static io.remedymatch.angebot.domain.service.AngebotAnfrageTestFixtures.beispielAngebotAnfrage;
import static io.remedymatch.angebot.domain.service.AngebotAnfrageTestFixtures.beispielAngebotAnfrageEntity;
import static io.remedymatch.angebot.domain.service.AngebotAnfrageTestFixtures.beispielAngebotAnfrageId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;
import io.remedymatch.domain.ObjectNotFoundException;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		AngebotAnfrageSucheService.class, //
		AngebotAnfrageJpaRepository.class //
})
@Tag("Spring")
@DisplayName("AngebotAnfrageSucheService soll")
class AngebotAnfrageSucheServiceShould {

	@Autowired
	private AngebotAnfrageSucheService anfrageSucheService;

	@MockBean
	private AngebotAnfrageJpaRepository anfrageRepository;

	@Test
	@DisplayName("alle Anfragen der Institution zurueckliefern")
	void alle_Anfragen_der_Institution_zurueckliefern() {

		val anfrage1Entity = beispielAngebotAnfrageEntity();
		val anfrage1 = beispielAngebotAnfrage();

		val anfrage2Id = new AngebotAnfrageId(UUID.randomUUID());
		val anfrage2Entity = beispielAngebotAnfrageEntity();
		anfrage2Entity.setId(anfrage2Id.getValue());
		val anfrage2 = AngebotAnfrageTestFixtures.beispielAngebotAnfrage();
		anfrage2.setId(anfrage2Id);

		val anfrageInstitutionId = anfrage1.getInstitutionVon().getId();

		given(anfrageRepository.findAllByInstitutionVon_Id(anfrageInstitutionId.getValue()))
				.willReturn(Arrays.asList(anfrage1Entity, anfrage2Entity));

		assertThat(//
				anfrageSucheService.findAlleAnfragenDerInstitution(anfrageInstitutionId), //
				containsInAnyOrder(anfrage1, anfrage2));

		then(anfrageRepository).should().findAllByInstitutionVon_Id(anfrageInstitutionId.getValue());
		then(anfrageRepository).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("alle Anfragen der Angebot Institution zurueckliefern")
	void alle_Anfragen_der_Angebot_Institution_zurueckliefern() {

		val anfrage1Entity = beispielAngebotAnfrageEntity();
		val anfrage1 = beispielAngebotAnfrage();

		val anfrage2Id = new AngebotAnfrageId(UUID.randomUUID());
		val anfrage2Entity = beispielAngebotAnfrageEntity();
		anfrage2Entity.setId(anfrage2Id.getValue());
		val anfrage2 = AngebotAnfrageTestFixtures.beispielAngebotAnfrage();
		anfrage2.setId(anfrage2Id);

		val angebotInstitutionId = anfrage1.getAngebot().getInstitution().getId();

		given(anfrageRepository.findAllByAngebot_Institution_Id(angebotInstitutionId.getValue()))
				.willReturn(Arrays.asList(anfrage1Entity, anfrage2Entity));

		assertThat(//
				anfrageSucheService.findAlleAnfragenDerAngebotInstitution(angebotInstitutionId), //
				containsInAnyOrder(anfrage1, anfrage2));

		then(anfrageRepository).should().findAllByAngebot_Institution_Id(angebotInstitutionId.getValue());
		then(anfrageRepository).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("gesuchte Anfrage finden")
	void gesuchte_Anfrage_finden() {

		val anfrageId = beispielAngebotAnfrageId();
		val anfrageEntity = beispielAngebotAnfrageEntity();

		given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

		val expectedAnfrage = beispielAngebotAnfrage();

		assertEquals(Optional.of(expectedAnfrage), anfrageSucheService.findAnfrage(anfrageId));

		then(anfrageRepository).should().findById(anfrageId.getValue());
		then(anfrageRepository).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("eine ObjectNotFoundException werfen wenn gesuchte Anfrage nicht existiert")
	void eine_ObjectNotFoundException_werfen_wenn_gesuchtes_Artikel_nicht_existiert() {
		assertThrows(ObjectNotFoundException.class,
				() -> anfrageSucheService.getAnfrageOrElseThrow(beispielAngebotAnfrageId()));
	}

	@Test
	@DisplayName("gesuchte Anfrage  zurueckliefern")
	void gesuchte_Anfrage_zurueckliefern() {

		val anfrageId = beispielAngebotAnfrageId();
		val anfrageEntity = beispielAngebotAnfrageEntity();

		given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

		val expectedAnfrage = beispielAngebotAnfrage();

		assertEquals(expectedAnfrage, anfrageSucheService.getAnfrageOrElseThrow(anfrageId));

		then(anfrageRepository).should().findById(anfrageId.getValue());
		then(anfrageRepository).shouldHaveNoMoreInteractions();
	}

}
