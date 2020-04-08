package io.remedymatch.angebot.domain.service;

import java.math.BigDecimal;
import java.util.UUID;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

public final class AngebotAnfrageConverterFixtures {

	public static final AngebotAnfrageId ANGEBOT_ANFRAGE_ID = new AngebotAnfrageId(UUID.randomUUID());
	public static final String KOMMENTAR = "Kommentar";
	public static final InstitutionId INSTITUTION_VON_ID = new InstitutionId(UUID.randomUUID());
	public static final Institution INSTITUTION_VON = Institution.builder().id(INSTITUTION_VON_ID).build();
	public static final InstitutionEntity INSTITUTION_VON_ENTITY = InstitutionEntity.builder()
			.id(INSTITUTION_VON_ID.getValue()).build();
	public static final InstitutionStandortId STANDORT_VON_ID = new InstitutionStandortId(UUID.randomUUID());
	public static final InstitutionStandort STANDORT_VON = InstitutionStandort.builder().id(STANDORT_VON_ID).build();
	public static final InstitutionStandortEntity STANDORT_VON_ENTITY = InstitutionStandortEntity.builder()
			.id(STANDORT_VON_ID.getValue()).build();
	public static final String PROZESSINSTANZ_ID = "ProzessInstanzId";
	public static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	public static final AngebotAnfrageStatus STATUS = AngebotAnfrageStatus.Offen;

	public static AngebotAnfrage beispielAngebotAnfrage() {
		return AngebotAnfrage.builder() //
				.id(ANGEBOT_ANFRAGE_ID) //
				.angebot(AngebotConverterFixtures.beispielAngebot()) //
				.institutionVon(INSTITUTION_VON) //
				.standortVon(STANDORT_VON) //
				.anzahl(ANZAHL) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(STATUS) //
				.build();
	}

	public static AngebotAnfrageEntity beispielAngebotAnfrageEntity() {
		return AngebotAnfrageEntity.builder() //
				.id(ANGEBOT_ANFRAGE_ID.getValue()) //
				.angebot(AngebotConverterFixtures.beispielAngebotEntity()) //
				.institutionVon(INSTITUTION_VON_ENTITY) //
				.standortVon(STANDORT_VON_ENTITY) //
				.anzahl(ANZAHL) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(STATUS) //
				.build();
	}
}
