package io.remedymatch.person.domain.service;

import java.util.UUID;

import io.remedymatch.institution.domain.service.InstitutionTestFixtures;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.infrastructure.PersonEntity;

public final class PersonTestFixtures {
	private PersonTestFixtures() {
		
	}
	
	private static final PersonId PERSON_ID = new PersonId(UUID.randomUUID());
	private static final String USERNAME = "username";
	private static final String VORNAME = "Vorname";
	private static final String NACHNAME = "Nachname";
	private static final String EMAIL = "email@email.local";
	private static final String TELEFON = "012345";

	public final static PersonId beispielPersonId() {
		return PERSON_ID;
	}

	public final static Person beispielPerson() {
		return Person.builder() //
				.id(beispielPersonId()) //
				.username(USERNAME) //
				.vorname(VORNAME) //
				.nachname(NACHNAME) //
				.email(EMAIL) //
				.telefon(TELEFON) //
				.institution(InstitutionTestFixtures.beispielInstitution())//
				.standort(InstitutionTestFixtures.beispielHaupstandort())//
				.build();
	}

	final static PersonEntity beispielPersonEntity() {
		return PersonEntity.builder() //
				.id(beispielPersonId().getValue()) //
				.username(USERNAME) //
				.vorname(VORNAME) //
				.nachname(NACHNAME) //
				.email(EMAIL) //
				.telefon(TELEFON) //
				.institution(InstitutionTestFixtures.beispielInstitutionEntity())//
				.standort(InstitutionTestFixtures.beispielHaupstandortEntity())//
				.build();
	}
}
