package io.remedymatch.angebot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.domain.service.NeuesAngebot;
import io.remedymatch.artikel.domain.model.ArtikelVariante;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.institution.api.InstitutionStandortDTO;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotMapper soll")
public class AngebotControllerMapperShould {

	private static final AngebotId ANGEBOT_ID = new AngebotId(UUID.randomUUID());
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final BigDecimal REST = BigDecimal.valueOf(120.0);
	private static final ArtikelVarianteId ARTIKEL_VARIANTE_ID = new ArtikelVarianteId(UUID.randomUUID());
	private static final ArtikelVariante ARTIKEL_VARIANTE = ArtikelVariante.builder().id(ARTIKEL_VARIANTE_ID).build();
	private static final InstitutionId INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION = Institution.builder().id(INSTITUTION_ID).build();
	private static final InstitutionStandortId STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandortDTO STANDORT_DTO = InstitutionStandortDTO.builder()
			.id(STANDORT_ID.getValue()).build();
	private static final InstitutionStandort STANDORT = InstitutionStandort.builder().id(STANDORT_ID).build();
	private static final LocalDateTime HALTBARKEIT = LocalDateTime.now();
	private static final boolean STERIL = true;
	private static final boolean ORIGINALVERPACKT = true;
	private static final boolean MEDIZINISCH = true;
	private static final String KOMMENTAR = "Kommentar";
	private static final boolean BEDIENT = false;

	@Test
	@DisplayName("NeueAngebot konvertieren koennen")
	void neueAngebot_konvertieren_koennen() {
		assertEquals(neueAngebot(), AngebotControllerMapper.mapToNeueAngebot(neueAngebotRequest()));
	}

	@Test
	@DisplayName("Domain Objekt in DTO konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(ro(), AngebotControllerMapper.mapToAngebotRO(angebot()));
	}

	/* help methods */

	private NeuesAngebot neueAngebot() {
		return NeuesAngebot.builder() //
				.anzahl(ANZAHL) //
				.artikelVarianteId(ARTIKEL_VARIANTE_ID) //
				.standortId(STANDORT_ID) //
				.haltbarkeit(HALTBARKEIT) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.build();
	}

	private NeuesAngebotRequest neueAngebotRequest() {
		return NeuesAngebotRequest.builder() //
				.anzahl(ANZAHL) //
				.artikelVarianteId(ARTIKEL_VARIANTE_ID.getValue()) //
				.standortId(STANDORT_ID.getValue()) //
				.haltbarkeit(HALTBARKEIT) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.build();
	}

	private Angebot angebot() {
		return Angebot.builder() //
				.id(ANGEBOT_ID) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikelVariante(ARTIKEL_VARIANTE) //
				.institution(INSTITUTION) //
				.standort(STANDORT) //
				.haltbarkeit(HALTBARKEIT) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.build();
	}

	private AngebotRO ro() {
		return AngebotRO.builder() //
				.id(ANGEBOT_ID.getValue()) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikelVarianteId(ARTIKEL_VARIANTE_ID.getValue()) //
				.institutionId(INSTITUTION_ID.getValue()) //
				.standort(STANDORT_DTO) //
				.haltbarkeit(HALTBARKEIT) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.build();
	}
}
