package io.remedymatch.person.domain.service;

import static io.remedymatch.institution.domain.service.InstitutionTestFixtures.beispielInstitution;
import static io.remedymatch.institution.domain.service.InstitutionTestFixtures.beispielInstitutionEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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
import io.remedymatch.person.infrastructure.PersonEntity;
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

		val personEntityOhneId = PersonEntity.builder() //
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.institution(institutionEntity).standort(institutionStandortEntity) //
				.build();
		val personEntity = PersonEntity.builder() //
				.id(personId.getValue()) //
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.institution(institutionEntity).standort(institutionStandortEntity) //
				.build();

		given(institutionSucheService.findInstitution(institutionId)).willReturn(Optional.of(institution));
		given(personRepository.save(personEntityOhneId)).willReturn(personEntity);

		val expectedPerson = Person.builder() //
				.id(personId) //
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.institution(institution).standort(institutionStandort) //
				.build();

		assertEquals(expectedPerson, personService.personAnlegen(neuePerson));

		then(institutionSucheService).should().findInstitution(institutionId);
		then(institutionSucheService).shouldHaveNoMoreInteractions();
		then(personRepository).should().save(personEntityOhneId);
		then(personRepository).shouldHaveNoMoreInteractions();
	}
}
