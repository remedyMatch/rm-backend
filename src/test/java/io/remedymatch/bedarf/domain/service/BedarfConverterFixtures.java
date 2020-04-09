package io.remedymatch.bedarf.domain.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import io.remedymatch.artikel.domain.service.ArtikelTestFixtures;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

public final class BedarfConverterFixtures {

	public static final BedarfId BEDARF_ID = new BedarfId(UUID.randomUUID());
	public static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	public static final BigDecimal REST = BigDecimal.valueOf(120.0);
	public static final InstitutionId INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
	public static final Institution INSTITUTION = Institution.builder().id(INSTITUTION_ID).build();
	public static final InstitutionEntity INSTITUTION_ENTITY = InstitutionEntity.builder().id(INSTITUTION_ID.getValue())
			.build();
	public static final InstitutionStandortId STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
	public static final InstitutionStandort STANDORT = InstitutionStandort.builder().id(STANDORT_ID).build();
	public static final InstitutionStandortEntity STANDORT_ENTITY = InstitutionStandortEntity.builder()
			.id(STANDORT_ID.getValue()).build();
	public static final boolean STERIL = true;
	public static final boolean MEDIZINISCH = true;
	public static final String KOMMENTAR = "Kommentar";
	public static final boolean BEDIENT = true;
	public static final BedarfAnfrageId ANFRAGE_ID = new BedarfAnfrageId(UUID.randomUUID());
	public static final BedarfAnfrage ANFRAGE = BedarfAnfrage.builder().id(ANFRAGE_ID).build();
	public static final BedarfAnfrageEntity ANFRAGE_ENTITY = BedarfAnfrageEntity.builder().id(ANFRAGE_ID.getValue())
			.build();

	public static Bedarf beispielBedarf() {
		return Bedarf.builder() //
				.id(BEDARF_ID) //
				.artikel(ArtikelTestFixtures.beispielArtikel()) //
				.artikelVariante(ArtikelTestFixtures.beispielArtikelVariante1()) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.institution(INSTITUTION) //
				.standort(STANDORT) //
				.steril(STERIL) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.anfragen(Arrays.asList(ANFRAGE)) //
				.build();
	}

	public static BedarfEntity beispielBedarfEntity() {
		return BedarfEntity.builder() //
				.id(BEDARF_ID.getValue()) //
				.artikel(ArtikelTestFixtures.beispielArtikelEntity()) //
				.artikelVariante(ArtikelTestFixtures.beispielArtikelVariante1Entity()) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.institution(INSTITUTION_ENTITY) //
				.standort(STANDORT_ENTITY) //
				.steril(STERIL) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.anfragen(Arrays.asList(ANFRAGE_ENTITY)) //
				.build();
	}
}
