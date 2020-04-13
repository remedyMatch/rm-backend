package io.remedymatch.angebot.domain.service;

import java.math.BigDecimal;
import java.util.UUID;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.institution.domain.service.InstitutionTestFixtures;

public final class AngebotAnfrageTestFixtures {
	private AngebotAnfrageTestFixtures() {

	}

	public static final AngebotAnfrageId ANGEBOT_ANFRAGE_ID = new AngebotAnfrageId(UUID.randomUUID());
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
				.angebot(AngebotTestFixtures.beispielAngebotOhneAnfragen()) //
				.institution(InstitutionTestFixtures.beispielInstitution()) //
				.standort(InstitutionTestFixtures.beispielHaupstandort()) //
				.anzahl(ANGEBOT_ANFRAGE_ANZAHL) //
				.kommentar(ANGEBOT_ANFRAGE_KOMMENTAR) //
				.prozessInstanzId(ANGEBOT_ANFRAGE_PROZESSINSTANZ_ID) //
				.status(ANGEBOT_ANFRAGE_STATUS) //
				.build();
	}

	static AngebotAnfrageEntity beispielAngebotAnfrageEntity() {
		return AngebotAnfrageEntity.builder() //
				.id(beispielAngebotAnfrageId().getValue()) //
				.angebot(AngebotTestFixtures.beispielAngebotOhneAnfragenEntity()) //
				.institution(InstitutionTestFixtures.beispielInstitutionEntity()) //
				.standort(InstitutionTestFixtures.beispielHaupstandortEntity()) //
				.anzahl(ANGEBOT_ANFRAGE_ANZAHL) //
				.kommentar(ANGEBOT_ANFRAGE_KOMMENTAR) //
				.prozessInstanzId(ANGEBOT_ANFRAGE_PROZESSINSTANZ_ID) //
				.status(ANGEBOT_ANFRAGE_STATUS) //
				.build();
	}
}
