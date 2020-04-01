package io.remedymatch.angebot.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotEntityConverter soll")
public class AngebotEntityConverterShould {

	private static final AngebotId ANGEBOT_ID = new AngebotId(UUID.randomUUID());
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final BigDecimal REST = BigDecimal.valueOf(120.0);
	private static final ArtikelEntity ARTIKEL = ArtikelEntity.builder().id(UUID.randomUUID()).build();
	private static final InstitutionEntity INSTITUTION = InstitutionEntity.builder().id(UUID.randomUUID()).build();
	private static final InstitutionStandortId STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandort STANDORT = InstitutionStandort.builder().id(STANDORT_ID).build();
	private static final InstitutionStandortEntity STANDORT_ENTITY = InstitutionStandortEntity.builder().id(STANDORT_ID.getValue()).build();
	private static final LocalDateTime HALTBARKEIT = LocalDateTime.now();
	private static final boolean STERIL = true;
	private static final boolean ORIGINALVERPACKT = true;
	private static final boolean MEDIZINISCH = true;
	private static final String KOMMENTAR = "Kommentar";
	private static final boolean BEDIENT = false;
	private static final AngebotAnfrageId ANFRAGE_ID = new AngebotAnfrageId(UUID.randomUUID());
	private static final AngebotAnfrage ANFRAGE = AngebotAnfrage.builder().id(ANFRAGE_ID).build();
	private static final AngebotAnfrageEntity ANFRAGE_ENTITY = AngebotAnfrageEntity.builder().id(ANFRAGE_ID.getValue())
			.build();

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(angebot(true), AngebotEntityConverter.convert(entity(true)));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(AngebotEntityConverter.convert((AngebotEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt ohne Id in Entity konvertieren")
	void domain_Objekt_ohne_Id_in_Entity_konvertieren() {
		assertEquals(entity(false), AngebotEntityConverter.convert(angebot(false)));
	}

	@Test
	@DisplayName("Domain Objekt mit Id in Entity konvertieren")
	void domain_Objekt_mit_Id_in_Entity_konvertieren() {
		assertEquals(entity(true), AngebotEntityConverter.convert(angebot(true)));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(AngebotEntityConverter.convert((Angebot) null));
	}

	private Angebot angebot(boolean mitId) {
		return Angebot.builder() //
				.id(mitId ? ANGEBOT_ID : null) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikel(ARTIKEL) //
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

	private AngebotEntity entity(boolean mitId) {
		return AngebotEntity.builder() //
				.id(mitId ? ANGEBOT_ID.getValue() : null) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikel(ARTIKEL) //
				.institution(INSTITUTION) //
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
