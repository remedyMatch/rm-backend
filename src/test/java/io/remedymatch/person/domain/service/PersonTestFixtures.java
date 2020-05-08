package io.remedymatch.person.domain.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import io.remedymatch.institution.domain.service.InstitutionTestFixtures;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.domain.model.PersonStandort;
import io.remedymatch.person.domain.model.PersonStandortId;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonStandortEntity;

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
				.aktuellesStandort(beispielAktuellesStandort())//
				.standorte(new ArrayList<>(Arrays.asList(beispielAktuellesStandort())))//
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
				.aktuellesStandort(beispielAktuellesStandortEntity())//
				.standorte(new ArrayList<>(Arrays.asList(beispielAktuellesStandortEntity())))//
				.build();
	}

	private static final PersonStandortId AKTUELLE_INSTITUTION_ID = new PersonStandortId(UUID.randomUUID());

	final static PersonStandortId beispielAktuelleInstitutionId() {
		return AKTUELLE_INSTITUTION_ID;
	}

	final static PersonStandort beispielAktuellesStandort() {
		return PersonStandort.builder() //
				.id(beispielAktuelleInstitutionId()) //
				.institution(InstitutionTestFixtures.beispielInstitution())//
				.standort(InstitutionTestFixtures.beispielHaupstandort())//
				.oeffentlich(true) //
				.build();
	}

	final static PersonStandortEntity beispielAktuellesStandortEntity() {
		return PersonStandortEntity.builder() //
				.id(beispielAktuelleInstitutionId().getValue()) //
				.institution(InstitutionTestFixtures.beispielInstitutionEntity())//
				.standort(InstitutionTestFixtures.beispielHaupstandortEntity())//
				.oeffentlich(true) //
				.build();
	}
}
