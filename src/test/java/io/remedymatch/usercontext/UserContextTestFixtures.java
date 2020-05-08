package io.remedymatch.usercontext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.domain.model.PersonStandort;
import io.remedymatch.person.domain.model.PersonStandortId;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonStandortEntity;

public final class UserContextTestFixtures {
	private UserContextTestFixtures() {

	}

	private static final PersonId USER_CONTEXT_PERSON_ID = new PersonId(UUID.randomUUID());
	private static final String USER_CONTEXT_USERNAME = "usercontext_username";
	private static final String USER_CONTEXT_VORNAME = "UserContext Vorname";
	private static final String USER_CONTEXT_NACHNAME = "UserContext Nachname";
	private static final String USER_CONTEXT_EMAIL = "email@email.local";
	private static final String USER_CONTEXT_ELEFON = "01234566777";

	public final static PersonId beispielUserContextPersonId() {
		return USER_CONTEXT_PERSON_ID;
	}

	public final static Person beispielUserContextPerson() {
		return Person.builder() //
				.id(beispielUserContextPersonId()) //
				.username(USER_CONTEXT_USERNAME) //
				.vorname(USER_CONTEXT_VORNAME) //
				.nachname(USER_CONTEXT_NACHNAME) //
				.email(USER_CONTEXT_EMAIL) //
				.telefon(USER_CONTEXT_ELEFON) //
				.aktuellesStandort(beispielAktuellesStandort())//
				.standorte(new ArrayList<>(Arrays.asList(beispielAktuellesStandort())))//
				.build();
	}

	public final static PersonEntity beispielUserContextPersonEntity() {
		return PersonEntity.builder() //
				.id(beispielUserContextPersonId().getValue()) //
				.username(USER_CONTEXT_USERNAME) //
				.vorname(USER_CONTEXT_VORNAME) //
				.nachname(USER_CONTEXT_NACHNAME) //
				.email(USER_CONTEXT_EMAIL) //
				.telefon(USER_CONTEXT_ELEFON) //
				.aktuellesStandort(beispielAktuellesStandortEntity())//
				.standorte(new ArrayList<>(Arrays.asList(beispielAktuellesStandortEntity())))//
				.build();
	}

	private static final PersonStandortId AKTUELLE_INSTITUTION_ID = new PersonStandortId(UUID.randomUUID());

	public final static PersonStandortId beispielAktuelleInstitutionId() {
		return AKTUELLE_INSTITUTION_ID;
	}

	public final static PersonStandort beispielAktuellesStandort() {
		return PersonStandort.builder() //
				.id(beispielAktuelleInstitutionId()) //
				.institution(beispielUserContextInstitution())//
				.standort(beispielUserContextHauptstandort())//
				.oeffentlich(true) //
				.build();
	}

	public final static PersonStandortEntity beispielAktuellesStandortEntity() {
		return PersonStandortEntity.builder() //
				.id(beispielAktuelleInstitutionId().getValue()) //
				.institution(beispielUserContextInstitutionEntity())//
				.standort(beispielUserContextHauptstandortEntity())//
				.oeffentlich(true) //
				.build();
	}

	public static final InstitutionId USER_CONTEXT_INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
	public static final String USER_CONTEXT_INSTITUTION_NAME = "UserContext Institution Name";
	public static final String USER_CONTEXT_INSTITUTION_KEY = "userContextInstitutionKey";
	public static final InstitutionTyp USER_CONTEXT_INSTITUTION_TYP = InstitutionTyp.PRIVAT;

	public static InstitutionId beispielUserContextInstitutionId() {
		return USER_CONTEXT_INSTITUTION_ID;
	}

	public static Institution beispielUserContextInstitution() {
		return Institution.builder() //
				.id(beispielUserContextInstitutionId()) //
				.name(USER_CONTEXT_INSTITUTION_NAME) //
				.institutionKey(USER_CONTEXT_INSTITUTION_KEY) //
				.typ(USER_CONTEXT_INSTITUTION_TYP) //
				.hauptstandort(beispielUserContextHauptstandort()) //
				.standorte(new ArrayList<>(
						Arrays.asList(beispielUserContextHauptstandort(), beispielUserContextAnderesStandort()))) //
				.build();
	}

	public static InstitutionEntity beispielUserContextInstitutionEntity() {
		return InstitutionEntity.builder() //
				.id(beispielUserContextInstitutionId().getValue()) //
				.name(USER_CONTEXT_INSTITUTION_NAME) //
				.institutionKey(USER_CONTEXT_INSTITUTION_KEY) //
				.typ(USER_CONTEXT_INSTITUTION_TYP) //
				.hauptstandort(beispielUserContextHauptstandortEntity()) //
				.standorte(new ArrayList<>(Arrays.asList(beispielUserContextHauptstandortEntity(),
						beispielUserContextAnderesStandortEntity()))) //
				.build();
	}

	public static final InstitutionStandortId USER_CONTEXT_HAUPTSTANDORT_STANDORT_ID = new InstitutionStandortId(
			UUID.randomUUID());
	public static final String USER_CONTEXT_HAUPTSTANDORT_STANDORT_NAME = "Hauptstandort Name";
	public static final String USER_CONTEXT_HAUPTSTANDORT_STANDORT_PLZ = "Hauptstandort PLZ";
	public static final String USER_CONTEXT_HAUPTSTANDORT_STANDORT_ORT = "Hauptstandort Ort";
	public static final String USER_CONTEXT_HAUPTSTANDORT_STANDORT_STRASSE = "Hauptstandort Strasse";
	public static final String USER_CONTEXT_HAUPTSTANDORT_STANDORT_LAND = "Hauptstandort Land";
	public static final BigDecimal USER_CONTEXT_HAUPTSTANDORT_STANDORT_LONGITUDE = BigDecimal.valueOf(8888);
	public static final BigDecimal USER_CONTEXT_HAUPTSTANDORT_STANDORT_LATITUDE = BigDecimal.valueOf(8888);

	public static final InstitutionStandortId USER_CONTEXT_ANDERES_STANDORT_ID = new InstitutionStandortId(
			UUID.randomUUID());
	public static final String USER_CONTEXT_ANDERES_STANDORT_NAME = "Standort 1 Name";
	public static final String USER_CONTEXT_ANDERES_STANDORT_PLZ = "Standort 1 PLZ";
	public static final String USER_CONTEXT_ANDERES_STANDORT_ORT = "Standort 1 Ort";
	public static final String USER_CONTEXT_ANDERES_STANDORT_STRASSE = "Standort 1 Strasse";
	public static final String USER_CONTEXT_ANDERES_STANDORT_LAND = "Standort 1 Land";
	public static final BigDecimal USER_CONTEXT_ANDERES_STANDORT_LONGITUDE = BigDecimal.valueOf(4060);
	public static final BigDecimal USER_CONTEXT_ANDERES_STANDORT_LATITUDE = BigDecimal.valueOf(6040);

	public static InstitutionStandortId beispielUserContextHauptstandortId() {
		return USER_CONTEXT_HAUPTSTANDORT_STANDORT_ID;
	}

	public static InstitutionStandort beispielUserContextHauptstandort() {
		return InstitutionStandort.builder() //
				.id(beispielUserContextHauptstandortId()) //
				.name(USER_CONTEXT_HAUPTSTANDORT_STANDORT_NAME) //
				.plz(USER_CONTEXT_HAUPTSTANDORT_STANDORT_PLZ) //
				.ort(USER_CONTEXT_HAUPTSTANDORT_STANDORT_ORT) //
				.strasse(USER_CONTEXT_HAUPTSTANDORT_STANDORT_STRASSE) //
				.land(USER_CONTEXT_HAUPTSTANDORT_STANDORT_LAND) //
				.longitude(USER_CONTEXT_HAUPTSTANDORT_STANDORT_LONGITUDE) //
				.latitude(USER_CONTEXT_HAUPTSTANDORT_STANDORT_LATITUDE) //
				.build();
	}

	public static InstitutionStandortEntity beispielUserContextHauptstandortEntity() {
		return InstitutionStandortEntity.builder() //
				.id(beispielUserContextHauptstandortId().getValue()) //
				.name(USER_CONTEXT_HAUPTSTANDORT_STANDORT_NAME) //
				.plz(USER_CONTEXT_HAUPTSTANDORT_STANDORT_PLZ) //
				.ort(USER_CONTEXT_HAUPTSTANDORT_STANDORT_ORT) //
				.strasse(USER_CONTEXT_HAUPTSTANDORT_STANDORT_STRASSE) //
				.land(USER_CONTEXT_HAUPTSTANDORT_STANDORT_LAND) //
				.longitude(USER_CONTEXT_HAUPTSTANDORT_STANDORT_LONGITUDE) //
				.latitude(USER_CONTEXT_HAUPTSTANDORT_STANDORT_LATITUDE) //
				.build();
	}

	public static InstitutionStandortId beispielUserContextAnderesStandortId() {
		return USER_CONTEXT_ANDERES_STANDORT_ID;
	}

	public static InstitutionStandort beispielUserContextAnderesStandort() {
		return InstitutionStandort.builder() //
				.id(beispielUserContextAnderesStandortId()) //
				.name(USER_CONTEXT_ANDERES_STANDORT_NAME) //
				.plz(USER_CONTEXT_ANDERES_STANDORT_PLZ) //
				.ort(USER_CONTEXT_ANDERES_STANDORT_ORT) //
				.strasse(USER_CONTEXT_ANDERES_STANDORT_STRASSE) //
				.land(USER_CONTEXT_ANDERES_STANDORT_LAND) //
				.longitude(USER_CONTEXT_ANDERES_STANDORT_LONGITUDE) //
				.latitude(USER_CONTEXT_ANDERES_STANDORT_LATITUDE) //
				.build();
	}

	public static InstitutionStandortEntity beispielUserContextAnderesStandortEntity() {
		return InstitutionStandortEntity.builder() //
				.id(beispielUserContextAnderesStandortId().getValue()) //
				.name(USER_CONTEXT_ANDERES_STANDORT_NAME) //
				.plz(USER_CONTEXT_ANDERES_STANDORT_PLZ) //
				.ort(USER_CONTEXT_ANDERES_STANDORT_ORT) //
				.strasse(USER_CONTEXT_ANDERES_STANDORT_STRASSE) //
				.land(USER_CONTEXT_ANDERES_STANDORT_LAND) //
				.longitude(USER_CONTEXT_ANDERES_STANDORT_LONGITUDE) //
				.latitude(USER_CONTEXT_ANDERES_STANDORT_LATITUDE) //
				.build();
	}
}
