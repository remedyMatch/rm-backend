package io.remedymatch.angebot.domain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.artikel.domain.service.ArtikelConverterFixtures;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

public final class AngebotConverterFixtures {

	public static final AngebotId ANGEBOT_ID = new AngebotId(UUID.randomUUID());
	public static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	public static final BigDecimal REST = BigDecimal.valueOf(120.0);
	public static final InstitutionId INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
	public static final Institution INSTITUTION = Institution.builder().id(INSTITUTION_ID).build();
	public static final InstitutionEntity INSTITUTION_ENTITY = InstitutionEntity.builder()
			.id(INSTITUTION_ID.getValue()).build();
	public static final InstitutionStandortId STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
	public static final InstitutionStandort STANDORT = InstitutionStandort.builder().id(STANDORT_ID).build();
	public static final InstitutionStandortEntity STANDORT_ENTITY = InstitutionStandortEntity.builder()
			.id(STANDORT_ID.getValue()).build();
	public static final LocalDateTime HALTBARKEIT = LocalDateTime.now();
	public static final boolean STERIL = true;
	public static final boolean ORIGINALVERPACKT = true;
	public static final boolean MEDIZINISCH = true;
	public static final String KOMMENTAR = "Kommentar";
	public static final boolean BEDIENT = true;
	public static final AngebotAnfrageId ANFRAGE_ID = new AngebotAnfrageId(UUID.randomUUID());
	public static final AngebotAnfrage ANFRAGE = AngebotAnfrage.builder().id(ANFRAGE_ID).build();
	public static final AngebotAnfrageEntity ANFRAGE_ENTITY = AngebotAnfrageEntity.builder().id(ANFRAGE_ID.getValue())
			.build();

	public static Angebot beispielAngebot() {
		return Angebot.builder() //
				.id(ANGEBOT_ID) //
				.artikelVariante(ArtikelConverterFixtures.beispielArtikelVariante1()) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.institution(INSTITUTION) //
				.standort(STANDORT) //
				.haltbarkeit(HALTBARKEIT) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.anfragen(Arrays.asList(ANFRAGE)) //
				.build();
	}

	public static AngebotEntity beispielAngebotEntity() {
		return AngebotEntity.builder() //
				.id(ANGEBOT_ID.getValue()) //
				.artikelVariante(ArtikelConverterFixtures.beispielArtikelVariante1Entity()) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.institution(INSTITUTION_ENTITY) //
				.standort(STANDORT_ENTITY) //
				.haltbarkeit(HALTBARKEIT) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.anfragen(Arrays.asList(ANFRAGE_ENTITY)) //
				.build();
	}
}
