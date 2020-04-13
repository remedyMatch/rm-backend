package io.remedymatch.institution.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.model.NeuesInstitutionStandort;

@ExtendWith(SpringExtension.class)
@DisplayName("InstitutionStandortMapper soll")
class InstitutionStandortMapperShould {

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
	@DisplayName("eine leere Liste der Domain Objekte in leere Liste der ROs konvertieren")
	void eine_leere_Liste_der_Domain_Objekte_in_leere_Liste_der_ROs_konvertieren() {
		assertEquals(Collections.emptyList(), InstitutionStandortMapper.mapToStandorteRO(Collections.emptyList()));
	}

	@Test
	@DisplayName("eine Liste der Domain Objekte in Liste der ROs konvertieren")
	void eine_Liste_der_Domain_Objekte_in_Liste_der_ROs_konvertieren() {
		assertEquals(Arrays.asList(standortRO()),
				InstitutionStandortMapper.mapToStandorteRO(Arrays.asList(standort())));
	}

	@Test
	@DisplayName("Standort Domain Objekt in RO konvertieren")
	void standort_domain_Objekt_in_RO_konvertieren() {
		assertEquals(standortRO(), InstitutionStandortMapper.mapToStandortRO(standort()));
	}

	@Test
	@DisplayName("NeuesStandort konvertieren koennen")
	void neuesStandort_konvertieren_koennen() {
		assertEquals(neuesStandort(), InstitutionStandortMapper.mapToNeuesStandort(neuesStandortRequest()));
	}

	@Test
	@DisplayName("UUID in StandortId konvertieren")
	void uuid_in_StandortId_konvertieren() {
		assertEquals(STANDORT_ID, InstitutionStandortMapper.mapToStandortId(STANDORT_ID.getValue()));
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

	private NeuesInstitutionStandort neuesStandort() {
		return NeuesInstitutionStandort.builder() //
				.name(STANDORT_NAME) //
				.plz(STANDORT_PLZ) //
				.ort(STANDORT_ORT) //
				.strasse(STANDORT_STRASSE) //
				.strasse(STANDORT_HAUSNUMMER) //
				.land(STANDORT_LAND) //
				.build();
	}

	private NeuesInstitutionStandortRequest neuesStandortRequest() {
		return NeuesInstitutionStandortRequest.builder() //
				.name(STANDORT_NAME) //
				.plz(STANDORT_PLZ) //
				.ort(STANDORT_ORT) //
				.strasse(STANDORT_STRASSE) //
				.strasse(STANDORT_HAUSNUMMER) //
				.land(STANDORT_LAND) //
				.build();
	}
}
