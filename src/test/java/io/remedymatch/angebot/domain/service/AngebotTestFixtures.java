package io.remedymatch.angebot.domain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.artikel.domain.service.ArtikelTestFixtures;
import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.service.InstitutionTestFixtures;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

public final class AngebotTestFixtures {
	private AngebotTestFixtures() {

	}

	public static final AngebotId ANGEBOT_ID = new AngebotId(UUID.randomUUID());
	public static final BigDecimal ANGEBOT_ANZAHL = BigDecimal.valueOf(120.0);
	public static final BigDecimal ANGEBOT_REST = BigDecimal.valueOf(120.0);
	public static final Institution ANGEBOT_INSTITUTION = InstitutionTestFixtures.beispielInstitution();
	public static final InstitutionEntity ANGEBOT_INSTITUTION_ENTITY = InstitutionTestFixtures
			.beispielInstitutionEntity();
	public static final InstitutionStandort ANGEBOT_STANDORT = InstitutionTestFixtures.beispielHaupstandort();
	public static final InstitutionStandortEntity ANGEBOT_STANDORT_ENTITY = InstitutionTestFixtures
			.beispielHaupstandortEntity();
	public static final LocalDateTime ANGEBOT_HALTBARKEIT = LocalDateTime.now();
	public static final boolean ANGEBOT_STERIL = true;
	public static final boolean ANGEBOT_ORIGINALVERPACKT = true;
	public static final boolean ANGEBOT_MEDIZINISCH = true;
	public static final String ANGEBOT_KOMMENTAR = "Kommentar";
	public static final boolean ANGEBOT_BEDIENT = false;

	private static final AngebotId ANGEBOT_OHNE_ANFRAGEN_ID = new AngebotId(UUID.randomUUID());

	public static AngebotId beispielAngebotId() {
		return ANGEBOT_ID;
	}

	public static Angebot beispielAngebot() {
		return Angebot.builder() //
				.id(beispielAngebotId()) //
				.artikelVariante(ArtikelTestFixtures.beispielArtikelVariante()) //
				.anzahl(ANGEBOT_ANZAHL) //
				.rest(ANGEBOT_REST) //
				.institution(ANGEBOT_INSTITUTION) //
				.standort(ANGEBOT_STANDORT) //
				.haltbarkeit(ANGEBOT_HALTBARKEIT) //
				.steril(ANGEBOT_STERIL) //
				.originalverpackt(ANGEBOT_ORIGINALVERPACKT) //
				.medizinisch(ANGEBOT_MEDIZINISCH) //
				.kommentar(ANGEBOT_KOMMENTAR) //
				.bedient(ANGEBOT_BEDIENT) //
				.build();
	}

	public static AngebotEntity beispielAngebotEntity() {
		return AngebotEntity.builder() //
				.id(beispielAngebotId().getValue()) //
				.artikelVariante(ArtikelTestFixtures.beispielArtikelVarianteEntity()) //
				.anzahl(ANGEBOT_ANZAHL) //
				.rest(ANGEBOT_REST) //
				.institution(ANGEBOT_INSTITUTION_ENTITY) //
				.standort(ANGEBOT_STANDORT_ENTITY) //
				.haltbarkeit(ANGEBOT_HALTBARKEIT) //
				.steril(ANGEBOT_STERIL) //
				.originalverpackt(ANGEBOT_ORIGINALVERPACKT) //
				.medizinisch(ANGEBOT_MEDIZINISCH) //
				.kommentar(ANGEBOT_KOMMENTAR) //
				.bedient(ANGEBOT_BEDIENT) //
				.build();
	}

	static Angebot beispielAngebotOhneAnfragen() {
		return Angebot.builder() //
				.id(ANGEBOT_OHNE_ANFRAGEN_ID) //
				.artikelVariante(ArtikelTestFixtures.beispielArtikelVariante()) //
				.anzahl(ANGEBOT_ANZAHL) //
				.rest(ANGEBOT_REST) //
				.institution(ANGEBOT_INSTITUTION) //
				.standort(ANGEBOT_STANDORT) //
				.haltbarkeit(ANGEBOT_HALTBARKEIT) //
				.steril(ANGEBOT_STERIL) //
				.originalverpackt(ANGEBOT_ORIGINALVERPACKT) //
				.medizinisch(ANGEBOT_MEDIZINISCH) //
				.kommentar(ANGEBOT_KOMMENTAR) //
				.bedient(ANGEBOT_BEDIENT) //
				.build();
	}

	static AngebotEntity beispielAngebotOhneAnfragenEntity() {
		return AngebotEntity.builder() //
				.id(ANGEBOT_OHNE_ANFRAGEN_ID.getValue()) //
				.artikelVariante(ArtikelTestFixtures.beispielArtikelVarianteEntity()) //
				.anzahl(ANGEBOT_ANZAHL) //
				.rest(ANGEBOT_REST) //
				.institution(ANGEBOT_INSTITUTION_ENTITY) //
				.standort(ANGEBOT_STANDORT_ENTITY) //
				.haltbarkeit(ANGEBOT_HALTBARKEIT) //
				.steril(ANGEBOT_STERIL) //
				.originalverpackt(ANGEBOT_ORIGINALVERPACKT) //
				.medizinisch(ANGEBOT_MEDIZINISCH) //
				.kommentar(ANGEBOT_KOMMENTAR) //
				.bedient(ANGEBOT_BEDIENT) //
				.build();
	}
}
