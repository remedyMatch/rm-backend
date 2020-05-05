package io.remedymatch.person.domain.service;

import static io.remedymatch.institution.domain.service.InstitutionTestFixtures.beispielInstitution;
import static io.remedymatch.institution.domain.service.InstitutionTestFixtures.beispielInstitutionEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.ArrayList;
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

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.service.InstitutionSucheService;
import io.remedymatch.institution.domain.service.InstitutionTestFixtures;
import io.remedymatch.person.domain.model.NeuePerson;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.domain.model.PersonInstitution;
import io.remedymatch.person.domain.model.PersonInstitutionId;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonInstitutionEntity;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		PersonService.class, //
		InstitutionSucheService.class, //
		PersonJpaRepository.class //
})
@Tag("Spring")
@DisplayName("PersonService soll")
class PersonServiceShould {

	@Autowired
	private PersonService personService;

	@MockBean
	private InstitutionSucheService institutionSucheService;

	@MockBean
	private PersonJpaRepository personRepository;

	@Test
	@DisplayName("Fehler werfen bei Bearbeitung von nicht existierender Institution")
	void fehler_werfen_bei_Bearbeitung_von_nicht_existierender_Institution() {
		val unbekannteInstitutionId = new InstitutionId(UUID.randomUUID());

		assertThrows(ObjectNotFoundException.class, //
				() -> personService.getInstitution(unbekannteInstitutionId));
	}

	@Test
	@DisplayName("Fehler werfen bei Bearbeitung von Standort, der sich nicht in Institution befindet")
	void fehler_werfen_bei_Bearbeitung_von_Standort_der_sich_nicht_in_Institution_befindet() {
		val unbekanntesStandortId = new InstitutionStandortId(UUID.randomUUID());

		assertThrows(ObjectNotFoundException.class, //
				() -> personService.getInstitutionStandort(InstitutionTestFixtures.beispielInstitutionEntity(),
						unbekanntesStandortId));
	}

	@Test
	@DisplayName("Neue Person anlegen")
	void neue_Person_anlegen() {

		val username = "neue_username";
		val vorname = "Neue Vorname";
		val nachname = "Neue Nachname";
		val email = "email@neu.local";
		val telefon = "080808080808";

		val institution = beispielInstitution();
		val institutionEntity = beispielInstitutionEntity();
		val institutionId = institution.getId();

		val institutionStandort = institution.getHauptstandort();
		val institutionStandortEntity = institutionEntity.getHauptstandort();
		val institutionStandortId = institutionStandort.getId();

		val neuePerson = NeuePerson.builder()//
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.institutionId(institutionId).standortId(institutionStandortId) //
				.build();

		val personId = new PersonId(UUID.randomUUID());

		val aktuelleInstitutionId = new PersonInstitutionId(UUID.randomUUID());

		PersonEntity personEntityOhneId = PersonEntity.builder() //
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.build();
		
		PersonEntity personEntityMitId = PersonEntity.builder() //
				.id(personId.getValue()) //
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.build();
		
		PersonEntity personEntityMitAktuellerInstitutionOhneId = PersonEntity.builder() //
				.id(personId.getValue()) //
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.build();
		personEntityMitAktuellerInstitutionOhneId.addNeueAktuelleInstitution(institutionEntity, institutionStandortEntity);
		
		PersonInstitutionEntity aktuelleInstitutionMitId = PersonInstitutionEntity.builder() //
				.id(aktuelleInstitutionId.getValue()) //
				.person(personId.getValue()) //
				.institution(institutionEntity) //
				.standort(institutionStandortEntity) //
				.build();
		PersonEntity personEntity = PersonEntity.builder() //
				.id(personId.getValue()) //
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.aktuelleInstitution(aktuelleInstitutionMitId) //
				.institutionen(new ArrayList<>(Arrays.asList(aktuelleInstitutionMitId))) //
				.build();

		given(institutionSucheService.findInstitution(institutionId)).willReturn(Optional.of(institution));
		given(personRepository.save(personEntityOhneId)).willReturn(personEntityMitId);
		given(personRepository.save(personEntityMitAktuellerInstitutionOhneId)).willReturn(personEntity);

		val expectedAktuelleInstitution = PersonInstitution.builder() //
				.id(aktuelleInstitutionId) //
				.institution(institution) //
				.standort(institutionStandort) //
				.build();
		val expectedPerson = Person.builder() //
				.id(personId) //
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.aktuelleInstitution(expectedAktuelleInstitution) //
				.institutionen(new ArrayList<>(Arrays.asList(expectedAktuelleInstitution))) //
				.build();

		assertEquals(expectedPerson, personService.personAnlegen(neuePerson));

		then(institutionSucheService).should().findInstitution(institutionId);
		then(institutionSucheService).shouldHaveNoMoreInteractions();
		then(personRepository).should().save(personEntityOhneId);
		then(personRepository).should().save(personEntityMitAktuellerInstitutionOhneId);
		then(personRepository).shouldHaveNoMoreInteractions();
	}
}
