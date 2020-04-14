package io.remedymatch.person.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.domain.service.InstitutionTestFixtures;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.infrastructure.PersonEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("PersonEntityConverter soll")
public class PersonEntityConverterShould {
	private static final PersonId PERSON_ID = new PersonId(UUID.randomUUID());
	private static final String USERNAME = "username";
	private static final String VORNAME = "Vorname";
	private static final String NACHNAME = "Nachname";
	private static final String EMAIL = "email@email.local";
	private static final String TELEFON = "012345";

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(person(), PersonEntityConverter.convert(entity()));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(PersonEntityConverter.convert((PersonEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt in Entity konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(entity(), PersonEntityConverter.convert(person()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(PersonEntityConverter.convert((Person) null));
	}

	private Person person() {
		return Person.builder() //
				.id(PERSON_ID).username(USERNAME) //
				.vorname(VORNAME) //
				.nachname(NACHNAME) //
				.email(EMAIL) //
				.telefon(TELEFON) //
				.institution(InstitutionTestFixtures.beispielInstitution())//
				.standort(InstitutionTestFixtures.beispielHaupstandort())//
				.build();
	}

	private PersonEntity entity() {
		return PersonEntity.builder() //
				.id(PERSON_ID.getValue()).username(USERNAME) //
				.vorname(VORNAME) //
				.nachname(NACHNAME) //
				.email(EMAIL) //
				.telefon(TELEFON) //
				.institution(InstitutionTestFixtures.beispielInstitutionEntity())//
				.standort(InstitutionTestFixtures.beispielHaupstandortEntity())//
				.build();
	}
}
