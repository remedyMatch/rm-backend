package io.remedymatch.angebot.domain.service;

import java.math.BigDecimal;
import java.util.UUID;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionTestFixtures;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

public final class AngebotAnfrageTestFixtures {
	private AngebotAnfrageTestFixtures() {

	}

	public static final AngebotAnfrageId ANGEBOT_ANFRAGE_ID = new AngebotAnfrageId(UUID.randomUUID());
	public static final Angebot ANGEBOT_ANFRAGE_ANGEBOT = AngebotTestFixtures.beispielAngebot();
	public static final AngebotEntity ANGEBOT_ANFRAGE_ANGEBOT_ENTITY = AngebotTestFixtures.beispielAngebotEntity();
	public static final Institution ANGEBOT_ANFRAGE_INSTITUTION = InstitutionTestFixtures.beispielInstitution1();
	public static final InstitutionEntity ANGEBOT_ANFRAGE_INSTITUTION_ENTITY = InstitutionTestFixtures
			.beispielInstitution1Entity();
	public static final InstitutionStandort ANGEBOT_ANFRAGE_STANDORT = ANGEBOT_ANFRAGE_INSTITUTION.getHauptstandort();
	public static final InstitutionStandortEntity ANGEBOT_ANFRAGE_STANDORT_ENTITY = ANGEBOT_ANFRAGE_INSTITUTION_ENTITY
			.getHauptstandort();
	public static final BigDecimal ANGEBOT_ANFRAGE_ANZAHL = BigDecimal.valueOf(120.0);
	public static final String ANGEBOT_ANFRAGE_KOMMENTAR = "Angebot Anfrage Kommentar";
	public static final String ANGEBOT_ANFRAGE_PROZESSINSTANZ_ID = "Angebot_Anfrage_ProzessInstanzId";
	public static final AngebotAnfrageStatus ANGEBOT_ANFRAGE_STATUS = AngebotAnfrageStatus.Offen;

	public static AngebotAnfrageId beispielAngebotAnfrageId() {
		return ANGEBOT_ANFRAGE_ID;
	}

	public static AngebotAnfrage beispielAngebotAnfrage() {
		return AngebotAnfrage.builder() //
				.id(beispielAngebotAnfrageId()) //
				.angebot(ANGEBOT_ANFRAGE_ANGEBOT) // 
				.institution(ANGEBOT_ANFRAGE_INSTITUTION) //
				.standort(ANGEBOT_ANFRAGE_STANDORT) //
				.anzahl(ANGEBOT_ANFRAGE_ANZAHL) //
				.kommentar(ANGEBOT_ANFRAGE_KOMMENTAR) //
				.prozessInstanzId(ANGEBOT_ANFRAGE_PROZESSINSTANZ_ID) //
				.status(ANGEBOT_ANFRAGE_STATUS) //
				.build();
	}

	static AngebotAnfrageEntity beispielAngebotAnfrageEntity() {
		return AngebotAnfrageEntity.builder() //
				.id(beispielAngebotAnfrageId().getValue()) //
				.angebot(ANGEBOT_ANFRAGE_ANGEBOT_ENTITY) //
				.institution(ANGEBOT_ANFRAGE_INSTITUTION_ENTITY) //
				.standort(ANGEBOT_ANFRAGE_STANDORT_ENTITY) //
				.anzahl(ANGEBOT_ANFRAGE_ANZAHL) //
				.kommentar(ANGEBOT_ANFRAGE_KOMMENTAR) //
				.prozessInstanzId(ANGEBOT_ANFRAGE_PROZESSINSTANZ_ID) //
				.status(ANGEBOT_ANFRAGE_STATUS) //
				.build();
	}
}
