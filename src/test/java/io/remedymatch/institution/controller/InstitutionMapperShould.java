package io.remedymatch.institution.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.domain.model.InstitutionUpdate;

@ExtendWith(SpringExtension.class)
@DisplayName("InstitutionMapper soll")
class InstitutionMapperShould {

	public static final InstitutionId INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
	public static final String INSTITUTION_NAME = "Institution Name";
	public static final String INSTITUTION_KEY = "institutionKey";
	public static final InstitutionTyp INSTITUTION_TYP = InstitutionTyp.KRANKENHAUS;

	public static final InstitutionStandortId STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
	public static final String STANDORT_NAME = "Standort Name";
	public static final String STANDORT_PLZ = "Standort PLZ";
	public static final String STANDORT_ORT = "Standort Ort";
	public static final String STANDORT_STRASSE = "Standort Strasse";
	public static final String STANDORT_HAUSNUMMER = "Standort 10a";
	public static final String STANDORT_LAND = "Standort Land";
	public static final BigDecimal STANDORT_LONGITUDE = BigDecimal.valueOf(1000);
	public static final BigDecimal STANDORT_LATITUDE = BigDecimal.valueOf(2000);

	@Test
	@DisplayName("Standort Domain Objekt in RO konvertieren")
	void standort_domain_Objekt_in_RO_konvertieren() {
		assertEquals(institutionRO(), InstitutionMapper.mapToInstitutionRO(institution()));
	}

	@Test
	@DisplayName("InstitutionUpdate konvertieren koennen")
	void institutionUpdate_konvertieren_koennen() {
		assertEquals(institutionUpdate(), InstitutionMapper.mapToUpdate(institutionUpdateRequest()));
	}

	@Test
	@DisplayName("UUID in InstitutionId konvertieren")
	void uuid_in_InstitutionId_konvertieren() {
		assertEquals(INSTITUTION_ID, InstitutionMapper.mapToInstitutionId(INSTITUTION_ID.getValue()));
	}

	private Institution institution() {
		return Institution.builder() //
				.id(INSTITUTION_ID) //
				.name(INSTITUTION_NAME) //
				.institutionKey(INSTITUTION_KEY) //
				.typ(INSTITUTION_TYP) //
				.hauptstandort(standort()) //
				.standorte(new ArrayList<>(Arrays.asList(standort()))) //
				.build();
	}

	private InstitutionRO institutionRO() {
		return InstitutionRO.builder() //
				.id(INSTITUTION_ID.getValue()) //
				.name(INSTITUTION_NAME) //
				.institutionKey(INSTITUTION_KEY) //
				.typ(INSTITUTION_TYP) //
				.hauptstandort(standortRO()) //
				.standorte(new ArrayList<>(Arrays.asList(standortRO()))) //
				.build();
	}

	private InstitutionUpdate institutionUpdate() {
		return InstitutionUpdate.builder() //
				.neueName(INSTITUTION_NAME) //
				.neuesTyp(INSTITUTION_TYP) //
				.neuesHauptstandortId(STANDORT_ID) //
				.build();
	}

	private InstitutionUpdateRequest institutionUpdateRequest() {
		return InstitutionUpdateRequest.builder() //
				.name(INSTITUTION_NAME) //
				.typ(INSTITUTION_TYP) //
				.hauptstandortId(STANDORT_ID.getValue()) //
				.build();
	}

	private InstitutionStandort standort() {
		return InstitutionStandort.builder() //
				.id(STANDORT_ID) //
				.name(STANDORT_NAME) //
				.plz(STANDORT_PLZ) //
				.ort(STANDORT_ORT) //
				.strasse(STANDORT_STRASSE) //
				.strasse(STANDORT_HAUSNUMMER) //
				.land(STANDORT_LAND) //
				.longitude(STANDORT_LONGITUDE) //
				.latitude(STANDORT_LATITUDE) //
				.build();
	}

	private InstitutionStandortRO standortRO() {
		return InstitutionStandortRO.builder() //
				.id(STANDORT_ID.getValue()) //
				.name(STANDORT_NAME) //
				.plz(STANDORT_PLZ) //
				.ort(STANDORT_ORT) //
				.strasse(STANDORT_STRASSE) //
				.strasse(STANDORT_HAUSNUMMER) //
				.land(STANDORT_LAND) //
				.longitude(STANDORT_LONGITUDE) //
				.latitude(STANDORT_LATITUDE) //
				.build();
	}
}
