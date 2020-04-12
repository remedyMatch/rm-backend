package io.remedymatch.institution.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.domain.Anfrage;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;

@ExtendWith(SpringExtension.class)
@DisplayName("AnfrageMapper soll")
public class AnfrageMapperShould {

	private static final UUID ANFRAGE_ID = UUID.randomUUID();
	private static final String KOMMENTAR = "Kommentar";
	private static final InstitutionId INSTITUTION_AN_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION_AN = Institution.builder().id(INSTITUTION_AN_ID).build();
	private static final InstitutionRO INSTITUTION_AN_DTO = InstitutionRO.builder().id(INSTITUTION_AN_ID.getValue()).build();
	private static final InstitutionStandortId STANDORT_AN_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandort STANDORT_AN = InstitutionStandort.builder().id(STANDORT_AN_ID).build();
	private static final InstitutionStandortRO STANDORT_AN_DTO = InstitutionStandortRO.builder().id(STANDORT_AN_ID.getValue()).build();
	private static final InstitutionId INSTITUTION_VON_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION_VON = Institution.builder().id(INSTITUTION_VON_ID).build();
	private static final InstitutionRO INSTITUTION_VON_DTO = InstitutionRO.builder().id(INSTITUTION_VON_ID.getValue()).build();
	private static final InstitutionStandortId STANDORT_VON_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandort STANDORT_VON = InstitutionStandort.builder().id(STANDORT_VON_ID).build();
	private static final InstitutionStandortRO STANDORT_VON_DTO = InstitutionStandortRO.builder().id(STANDORT_VON_ID.getValue()).build();
	private static final UUID ANGEBOT_ID = UUID.randomUUID();
	private static final UUID BEDARF_ID = UUID.randomUUID();
	private static final String PROZESSINSTANZ_ID = "ProzessInstanzId";
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final String STATUS = "egal";
	private static final BigDecimal ENTFERNUNG = BigDecimal.valueOf(1523.0);
	

	@Test
	@DisplayName("Anfrage in AnfrageDTO konvertieren")
	void anfrage_in_AnfrageDTO_konvertieren() {
		assertEquals(anfrageDTO(), AnfrageMapper.mapToDTO(anfrage()));
	}

	private AnfrageDTO anfrageDTO() {
		return AnfrageDTO.builder() //
				.id(ANFRAGE_ID) //
				.angebotId(ANGEBOT_ID)
				.bedarfId(BEDARF_ID) //
				.institutionAn(INSTITUTION_AN_DTO) //
				.standortAn(STANDORT_AN_DTO) //
				.institutionVon(INSTITUTION_VON_DTO) //
				.standortVon(STANDORT_VON_DTO) //
				.anzahl(ANZAHL) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(STATUS) //
				.entfernung(ENTFERNUNG) //
				.build();
	}

	private Anfrage anfrage() {
		return Anfrage.builder() //
				.id(ANFRAGE_ID) //
				.angebotId(ANGEBOT_ID)
				.bedarfId(BEDARF_ID) //
				.institutionAn(INSTITUTION_AN) //
				.standortAn(STANDORT_AN) //
				.institutionVon(INSTITUTION_VON) //
				.standortVon(STANDORT_VON) //
				.anzahl(ANZAHL) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(STATUS) //
				.entfernung(ENTFERNUNG) //
				.build();
	}
}
