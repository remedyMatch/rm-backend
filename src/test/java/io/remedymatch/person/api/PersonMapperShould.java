package io.remedymatch.person.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.person.domain.Person;
import io.remedymatch.person.domain.PersonId;

@ExtendWith(SpringExtension.class)
@DisplayName("PersonMapper soll")
public class PersonMapperShould {
	private static final PersonId PERSON_ID = new PersonId(UUID.randomUUID());
	private static final String USERNAME = "username";
	private static final String VORNAME = "Vorname";
	private static final String NACHNAME = "Nachname";
	private static final String TELEFON = "012345";

	private static final InstitutionId INSTITUTION_ID = new InstitutionId(UUID.randomUUID());

	@Test
	@DisplayName("DTO in Domain Objekt konvertieren")
	void dto_in_Domain_Objekt_konvertieren() {
		assertEquals(personOhneUsernameAndInstitution(), PersonMapper.mapToPerson(personDTO()));
	}

	@Test
	@DisplayName("null DTO in null Domain Objekt konvertieren")
	void null_dto_in_null_Domain_Objekt_konvertieren() {
		assertNull(PersonMapper.mapToPerson((PersonDTO) null));
	}

	@Test
	@DisplayName("Domain Objekt in DTO konvertieren")
	void domain_Objekt_in_DTO_konvertieren() {
		assertEquals(personDTO(), PersonMapper.mapToDTO(person()));
	}

	@Test
	@DisplayName("null Domain Objekt in null DTO konvertieren")
	void null_domain_Objekt_in_DTO_konvertieren() {
		assertNull(PersonMapper.mapToDTO((Person) null));
	}

	private Person person() {
		return Person.builder() //
				.id(PERSON_ID)//
				.username(USERNAME) //
				.vorname(VORNAME) //
				.nachname(NACHNAME) //
				.telefon(TELEFON) //
				.institution(institution()) //
				.build();
	}

	private Person personOhneUsernameAndInstitution() {
		return Person.builder() //
				.id(PERSON_ID)//
				.vorname(VORNAME) //
				.nachname(NACHNAME) //
				.telefon(TELEFON) //
				.build();
	}

	private PersonDTO personDTO() {
		return PersonDTO.builder() //
				.id(PERSON_ID.getValue())//
				.vorname(VORNAME) //
				.nachname(NACHNAME) //
				.telefon(TELEFON) //
				.build();
	}

	private Institution institution() {
		return Institution.builder() //
				.id(INSTITUTION_ID) //
				.build();
	}
}
