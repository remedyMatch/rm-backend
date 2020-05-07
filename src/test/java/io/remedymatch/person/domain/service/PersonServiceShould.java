package io.remedymatch.person.domain.service;

import static io.remedymatch.institution.domain.service.InstitutionTestFixtures.beispielInstitution;
import static io.remedymatch.institution.domain.service.InstitutionTestFixtures.beispielInstitutionEntity;
import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPerson;
import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPersonEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
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
import io.remedymatch.person.domain.model.NeuesPersonStandort;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.domain.model.PersonStandort;
import io.remedymatch.person.domain.model.PersonStandortId;
import io.remedymatch.person.domain.model.PersonUpdate;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import io.remedymatch.person.infrastructure.PersonStandortEntity;
import io.remedymatch.usercontext.UserContextService;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		PersonService.class, //
		InstitutionSucheService.class, //
		PersonJpaRepository.class, //
		UserContextService.class //
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

	@MockBean
	private UserContextService userService;

	@Test
	@Disabled
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
				.institutionId(institutionId) //
				.standortId(institutionStandortId) //
				.standortOeffentlich(true) //
				.build();

		val personId = new PersonId(UUID.randomUUID());

		val aktuelleInstitutionId = new PersonStandortId(UUID.randomUUID());

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

		PersonEntity personEntityMitAktuellemStandortOhneId = PersonEntity.builder() //
				.id(personId.getValue()) //
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.build();
		personEntityMitAktuellemStandortOhneId.addNeuesAktuellesStandort(institutionEntity, institutionStandortEntity,
				true);

		PersonStandortEntity aktuellesStandortMitId = PersonStandortEntity.builder() //
				.id(aktuelleInstitutionId.getValue()) //
				.person(personId.getValue()) //
				.institution(institutionEntity) //
				.standort(institutionStandortEntity) //
				.oeffentlich(true) //
				.build();
		PersonEntity personEntity = PersonEntity.builder() //
				.id(personId.getValue()) //
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.aktuellesStandort(aktuellesStandortMitId) //
				.standorte(new ArrayList<>(Arrays.asList(aktuellesStandortMitId))) //
				.build();

		given(institutionSucheService.getByInstitutionId(institutionId)).willReturn(institution);
		given(personRepository.save(personEntityOhneId)).willReturn(personEntityMitId);
		given(personRepository.save(personEntityMitAktuellemStandortOhneId)).willReturn(personEntity);

		val expectedAktuellesStandort = PersonStandort.builder() //
				.id(aktuelleInstitutionId) //
				.institution(institution) //
				.standort(institutionStandort) //
				.oeffentlich(true) //
				.build();
		val expectedPerson = Person.builder() //
				.id(personId) //
				.username(username) //
				.vorname(vorname) //
				.nachname(nachname) //
				.email(email) //
				.telefon(telefon) //
				.aktuellesStandort(expectedAktuellesStandort) //
				.standorte(new ArrayList<>(Arrays.asList(expectedAktuellesStandort))) //
				.build();

		assertEquals(expectedPerson, personService.personAnlegen(neuePerson));

		then(institutionSucheService).should().getByInstitutionId(institutionId);
		then(institutionSucheService).shouldHaveNoMoreInteractions();
		then(personRepository).should().save(personEntityOhneId);
		then(personRepository).should().save(personEntityMitAktuellemStandortOhneId);
		then(personRepository).shouldHaveNoMoreInteractions();
		then(userService).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("ein Fehler werfen, wenn der Standort nicht in Person existiert")
	void eine_Fehler_werfen_wenn_der_Standort_nicht_in_Person_existiert() {

		val unbekanntesStandortId = new PersonStandortId(UUID.randomUUID());

		val person = beispielPerson();
		val personEntity = beispielPersonEntity();

		given(userService.getContextUserId()).willReturn(person.getId());
		given(personRepository.findById(person.getId().getValue())).willReturn(Optional.of(personEntity));

		val personUpdate = PersonUpdate.builder().aktuellesStandortId(unbekanntesStandortId).build();
		assertThrows(ObjectNotFoundException.class, () -> personService.userAktualisieren(personUpdate));

		then(institutionSucheService).shouldHaveNoInteractions();
		then(personRepository).should().findById(person.getId().getValue());
		then(personRepository).shouldHaveNoMoreInteractions();
		then(userService).should().getContextUserId();
		then(userService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("ein anderes Standort, als aktuelles Standort der Person aendern")
	void ein_anderes_Standort_als_aktuelles_Standort_der_Person_aendern() {

		val andereInstitutionStandortId = new PersonStandortId(UUID.randomUUID());

		val andereInstitutionEntity = InstitutionTestFixtures.beispielInstitution1Entity();
		PersonStandortEntity anderesStandort = PersonStandortEntity.builder() //
				.id(andereInstitutionStandortId.getValue())//
				.institution(andereInstitutionEntity)//
				.standort(andereInstitutionEntity.getHauptstandort())//
				.oeffentlich(true)//
				.build();

		val personEntityVorAenderung = beispielPersonEntity();
		personEntityVorAenderung.getStandorte().add(anderesStandort);

		PersonEntity personEntityNachAenderung = PersonEntity.builder()//
				.id(personEntityVorAenderung.getId()) //
				.username(personEntityVorAenderung.getUsername()) //
				.vorname(personEntityVorAenderung.getVorname()) //
				.nachname(personEntityVorAenderung.getNachname()) //
				.email(personEntityVorAenderung.getEmail()) //
				.telefon(personEntityVorAenderung.getTelefon()) //
				.aktuellesStandort(anderesStandort)//
				.standorte(new ArrayList<>(personEntityVorAenderung.getStandorte()))//
				.build();

		val expectedPerson = beispielPerson();
		val andereInstitution = InstitutionTestFixtures.beispielInstitution1();
		val neuesAktuellesStandort = PersonStandort.builder() //
				.id(andereInstitutionStandortId)//
				.institution(andereInstitution)//
				.standort(andereInstitution.getHauptstandort())//
				.oeffentlich(true)//
				.build();
		expectedPerson.setAktuellesStandort(neuesAktuellesStandort);
		expectedPerson.getStandorte().add(neuesAktuellesStandort);

		given(userService.getContextUserId()).willReturn(expectedPerson.getId());
		given(personRepository.findById(expectedPerson.getId().getValue()))
				.willReturn(Optional.of(personEntityVorAenderung));
		given(personRepository.save(personEntityNachAenderung)).willReturn(personEntityNachAenderung);

		val personUpdate = PersonUpdate.builder().aktuellesStandortId(andereInstitutionStandortId).build();
		assertEquals(expectedPerson, personService.userAktualisieren(personUpdate));

		then(institutionSucheService).shouldHaveNoInteractions();
		then(personRepository).should().findById(expectedPerson.getId().getValue());
		then(personRepository).should().save(personEntityNachAenderung);
		then(personRepository).shouldHaveNoMoreInteractions();
		then(userService).should().getContextUserId();
		then(userService).shouldHaveNoMoreInteractions();
	}

	@Test
	@Disabled
	@DisplayName("neues Standort der Person zuweisen")
	void neues_Standord_der_Person_zuweisen() {

		val gleicheInstitutionStandortId = new PersonStandortId(UUID.randomUUID());

		val anderesStandortEntity = InstitutionTestFixtures.beispielStandort2Entity();

		val personEntityVorAenderung = beispielPersonEntity();
		personEntityVorAenderung.getAktuellesStandort().getInstitution().getStandorte().add(anderesStandortEntity);
		personEntityVorAenderung.getStandorte().get(0).getInstitution().getStandorte().add(anderesStandortEntity);

		val personEntityNachAenderung = beispielPersonEntity();
		personEntityNachAenderung.getAktuellesStandort().getInstitution().getStandorte().add(anderesStandortEntity);
		personEntityNachAenderung.getStandorte().get(0).getInstitution().getStandorte().add(anderesStandortEntity);
		personEntityNachAenderung.getStandorte().add(PersonStandortEntity.builder()//
				.id(gleicheInstitutionStandortId.getValue()) //
				.institution(personEntityNachAenderung.getAktuellesStandort().getInstitution()) //
				.standort(anderesStandortEntity) //
				.oeffentlich(false) //
				.build());

		val expectedPerson = beispielPerson();
		val anderesStandort = InstitutionTestFixtures.beispielStandort2();
		expectedPerson.getAktuellesStandort().getInstitution().getStandorte().add(anderesStandort);
		expectedPerson.getStandorte().get(0).getInstitution().getStandorte().add(anderesStandort);
		expectedPerson.getStandorte().add(PersonStandort.builder()//
				.id(gleicheInstitutionStandortId) //
				.institution(expectedPerson.getAktuellesStandort().getInstitution()) //
				.standort(anderesStandort) //
				.oeffentlich(false) //
				.build());

		given(userService.getContextUserId()).willReturn(expectedPerson.getId());
		given(personRepository.findById(expectedPerson.getId().getValue()))
				.willReturn(Optional.of(personEntityVorAenderung));
		given(personRepository.save(personEntityNachAenderung)).willReturn(personEntityNachAenderung);

		val neuesStandort = NeuesPersonStandort.builder()//
				.institutionId(expectedPerson.getAktuellesStandort().getInstitution().getId()) //
				.standortId(anderesStandort.getId()) //
				.oeffentlich(false) //
				.build();
		assertEquals(expectedPerson, personService.userStandortHinzufuegen(neuesStandort));

		then(institutionSucheService).shouldHaveNoInteractions();
		then(personRepository).should().findById(expectedPerson.getId().getValue());
		then(personRepository).should().save(personEntityNachAenderung);
		then(personRepository).shouldHaveNoMoreInteractions();
		then(userService).should().getContextUserId();
		then(userService).shouldHaveNoMoreInteractions();
	}
}
