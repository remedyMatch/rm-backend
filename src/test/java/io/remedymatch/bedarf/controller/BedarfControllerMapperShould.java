package io.remedymatch.bedarf.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.artikel.domain.model.Artikel;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVariante;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.domain.service.NeuesBedarf;
import io.remedymatch.institution.api.InstitutionStandortDTO;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;

@ExtendWith(SpringExtension.class)
@DisplayName("BedarfMapper soll")
public class BedarfControllerMapperShould {

	private static final BedarfId BEDARF_ID = new BedarfId(UUID.randomUUID());
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final BigDecimal REST = BigDecimal.valueOf(120.0);
	private static final ArtikelId ARTIKEL_ID = new ArtikelId(UUID.randomUUID());
	private static final Artikel ARTIKEL = Artikel.builder().id(ARTIKEL_ID).build();
	private static final ArtikelVarianteId ARTIKEL_VARIANTE_ID = new ArtikelVarianteId(UUID.randomUUID());
	private static final ArtikelVariante ARTIKEL_VARIANTE = ArtikelVariante.builder().id(ARTIKEL_VARIANTE_ID).build();
	private static final InstitutionId INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION = Institution.builder().id(INSTITUTION_ID).build();
	private static final InstitutionStandortId STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandortDTO STANDORT_DTO = InstitutionStandortDTO.builder()
			.id(STANDORT_ID.getValue()).build();
	private static final InstitutionStandort STANDORT = InstitutionStandort.builder().id(STANDORT_ID).build();
	private static final boolean STERIL = true;
	private static final boolean MEDIZINISCH = true;
	private static final String KOMMENTAR = "Kommentar";
	private static final boolean BEDIENT = false;

	@Test
	@DisplayName("NeueBedarf konvertieren koennen")
	void neuesBedarf_konvertieren_koennen() {
		assertEquals(neueBedarf(), BedarfControllerMapper.mapToNeuesBedarf(neuesBedarfRequest()));
	}

	@Test
	@DisplayName("Domain Objekt in DTO konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(ro(), BedarfControllerMapper.mapToBedarfRO(bedarf()));
	}

	/* help methods */

	private NeuesBedarf neueBedarf() {
		return NeuesBedarf.builder() //
				.anzahl(ANZAHL) //
				.artikelId(ARTIKEL_ID) //
				.artikelVarianteId(ARTIKEL_VARIANTE_ID) //
				.standortId(STANDORT_ID) //
				.steril(STERIL) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.build();
	}

	private NeuesBedarfRequest neuesBedarfRequest() {
		return NeuesBedarfRequest.builder() //
				.anzahl(ANZAHL) //
				.artikelId(ARTIKEL_ID.getValue()) //
				.artikelVarianteId(ARTIKEL_VARIANTE_ID.getValue()) //
				.standortId(STANDORT_ID.getValue()) //
				.steril(STERIL) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.build();
	}

	private Bedarf bedarf() {
		return Bedarf.builder() //
				.id(BEDARF_ID) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikel(ARTIKEL) //
				.artikelVariante(ARTIKEL_VARIANTE) //
				.institution(INSTITUTION) //
				.standort(STANDORT) //
				.steril(STERIL) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.build();
	}

	private BedarfRO ro() {
		return BedarfRO.builder() //
				.id(BEDARF_ID.getValue()) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikelId(ARTIKEL_ID.getValue()) //
				.artikelVarianteId(ARTIKEL_VARIANTE_ID.getValue()) //
				.institutionId(INSTITUTION_ID.getValue()) //
				.standort(STANDORT_DTO) //
				.steril(STERIL) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.build();
	}
}
