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

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionAnfrage;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.model.InstitutionStandortId;

@ExtendWith(SpringExtension.class)
@DisplayName("InstitutionAnfrageControllerMapper soll")
class InstitutionAnfrageControllerMapperShould {

	private static final UUID ANFRAGE_ID = UUID.randomUUID();
	private static final String KOMMENTAR = "Kommentar";
	private static final InstitutionId INSTITUTION_AN_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION_AN = Institution.builder().id(INSTITUTION_AN_ID).build();
	private static final InstitutionRO INSTITUTION_AN_DTO = InstitutionRO.builder().id(INSTITUTION_AN_ID.getValue())
			.build();
	private static final InstitutionStandortId STANDORT_AN_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandort STANDORT_AN = InstitutionStandort.builder().id(STANDORT_AN_ID).build();
	private static final InstitutionStandortRO STANDORT_AN_DTO = InstitutionStandortRO.builder()
			.id(STANDORT_AN_ID.getValue()).build();
	private static final InstitutionId INSTITUTION_VON_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION_VON = Institution.builder().id(INSTITUTION_VON_ID).build();
	private static final InstitutionRO INSTITUTION_VON_DTO = InstitutionRO.builder().id(INSTITUTION_VON_ID.getValue())
			.build();
	private static final InstitutionStandortId STANDORT_VON_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandort STANDORT_VON = InstitutionStandort.builder().id(STANDORT_VON_ID).build();
	private static final InstitutionStandortRO STANDORT_VON_DTO = InstitutionStandortRO.builder()
			.id(STANDORT_VON_ID.getValue()).build();
	private static final UUID ANGEBOT_ID = UUID.randomUUID();
	private static final UUID BEDARF_ID = UUID.randomUUID();
	private static final String PROZESSINSTANZ_ID = "ProzessInstanzId";
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final ArtikelId ARTIKEL_ID = new ArtikelId(UUID.randomUUID());
	private static final ArtikelVarianteId ARTIKEL_VARIANTE_ID = new ArtikelVarianteId(UUID.randomUUID());
	private static final String STATUS = "egal";
	private static final BigDecimal ENTFERNUNG = BigDecimal.valueOf(1523.0);

	@Test
	@DisplayName("eine leere Liste der Domain Objekte in leere Liste der ROs konvertieren")
	void eine_leere_Liste_der_Domain_Objekte_in_leere_Liste_der_ROs_konvertieren() {
		assertEquals(Collections.emptyList(),
				InstitutionAnfrageControllerMapper.mapToAnfragenRO(Collections.emptyList()));
	}

	@Test
	@DisplayName("eine Liste der Domain Objekte in Liste der ROs konvertieren")
	void eine_Liste_der_Domain_Objekte_in_Liste_der_ROs_konvertieren() {
		assertEquals(Arrays.asList(anfrageRO()),
				InstitutionAnfrageControllerMapper.mapToAnfragenRO(Arrays.asList(anfrage())));
	}

	@Test
	@DisplayName("Domain Objekt in DTO konvertieren")
	void domain_Objekt_in_RO_konvertieren() {
		assertEquals(anfrageRO(), InstitutionAnfrageControllerMapper.mapToAnfrageRO(anfrage()));
	}

	private InstitutionAnfrageRO anfrageRO() {
		return InstitutionAnfrageRO.builder() //
				.id(ANFRAGE_ID) //
				.angebotId(ANGEBOT_ID)//
				.bedarfId(BEDARF_ID) //
				.institutionAn(INSTITUTION_AN_DTO) //
				.standortAn(STANDORT_AN_DTO) //
				.institutionVon(INSTITUTION_VON_DTO) //
				.standortVon(STANDORT_VON_DTO) //
				.anzahl(ANZAHL) //
				.artikelId(ARTIKEL_ID.getValue()) //
				.artikelVarianteId(ARTIKEL_VARIANTE_ID.getValue()) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(STATUS) //
				.entfernung(ENTFERNUNG) //
				.build();
	}

	private InstitutionAnfrage anfrage() {
		return InstitutionAnfrage.builder() //
				.id(ANFRAGE_ID) //
				.angebotId(ANGEBOT_ID)//
				.bedarfId(BEDARF_ID) //
				.institutionAn(INSTITUTION_AN) //
				.standortAn(STANDORT_AN) //
				.institutionVon(INSTITUTION_VON) //
				.standortVon(STANDORT_VON) //
				.anzahl(ANZAHL) //
				.artikelId(ARTIKEL_ID) //
				.artikelVarianteId(ARTIKEL_VARIANTE_ID) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(STATUS) //
				.entfernung(ENTFERNUNG) //
				.build();
	}
}
