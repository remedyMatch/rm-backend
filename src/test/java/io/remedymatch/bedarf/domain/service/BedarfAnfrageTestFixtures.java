package io.remedymatch.bedarf.domain.service;

import java.math.BigDecimal;
import java.util.UUID;

import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.institution.domain.InstitutionTestFixtures;

public final class BedarfAnfrageTestFixtures {

	public static final BedarfAnfrageId BEDARF_ANFRAGE_ID = new BedarfAnfrageId(UUID.randomUUID());
	public static final String BEDARF_ANFRAGE_KOMMENTAR = "Kommentar";
	public static final String BEDARF_ANFRAGE_PROZESSINSTANZ_ID = "ProzessInstanzId";
	public static final BigDecimal BEDARF_ANFRAGE_ANZAHL = BigDecimal.valueOf(120.0);
	public static final BedarfAnfrageStatus BEDARF_ANFRAGE_STATUS = BedarfAnfrageStatus.Offen;

	public static BedarfAnfrageId beispielBedarfAnfrageId() {
		return BEDARF_ANFRAGE_ID;
	}

	public static BedarfAnfrage beispielBedarfAnfrage() {
		return BedarfAnfrage.builder() //
				.id(beispielBedarfAnfrageId()) //
				.bedarf(BedarfTestFixtures.beispielBedarfOhneAnfragen()) //
				.institution(InstitutionTestFixtures.beispielInstitution()) //
				.standort(InstitutionTestFixtures.beispielHaupstandort()) //
				.anzahl(BEDARF_ANFRAGE_ANZAHL) //
				.kommentar(BEDARF_ANFRAGE_KOMMENTAR) //
				.prozessInstanzId(BEDARF_ANFRAGE_PROZESSINSTANZ_ID) //
				.status(BEDARF_ANFRAGE_STATUS) //
				.build();
	}

	public static BedarfAnfrageEntity beispielBedarfAnfrageEntity() {
		return BedarfAnfrageEntity.builder() //
				.id(beispielBedarfAnfrageId().getValue()) //
				.bedarf(BedarfTestFixtures.beispielBedarfOhneAnfragenEntity()) //
				.institution(InstitutionTestFixtures.beispielInstitutionEntity()) //
				.standort(InstitutionTestFixtures.beispielHaupstandortEntity()) //
				.anzahl(BEDARF_ANFRAGE_ANZAHL) //
				.kommentar(BEDARF_ANFRAGE_KOMMENTAR) //
				.prozessInstanzId(BEDARF_ANFRAGE_PROZESSINSTANZ_ID) //
				.status(BEDARF_ANFRAGE_STATUS) //
				.build();
	}
}
