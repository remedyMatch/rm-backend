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
import io.remedymatch.institution.domain.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotEntityConverter soll")
public class AngebotEntityConverterShould {

	private static final UUID ANGEBOT_ID = UUID.randomUUID();
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final BigDecimal REST = BigDecimal.valueOf(120.0);
	private static final ArtikelEntity ARTIKEL = ArtikelEntity.builder().id(UUID.randomUUID()).build();
	private static final InstitutionEntity INSTITUTION = InstitutionEntity.builder().id(UUID.randomUUID()).build();
	private static final InstitutionStandortEntity STANDORT = InstitutionStandortEntity.builder().id(UUID.randomUUID())
			.build();
	private static final LocalDateTime HALTBARKEIT = LocalDateTime.now();
	private static final boolean STERIL = true;
	private static final boolean ORIGINALVERPACKT = true;
	private static final boolean MEDIZINISCH = true;
	private static final String KOMMENTAR = "Kommentar";
	private static final boolean BEDIENT = false;
	private static final UUID ANFRAGE_ID = UUID.randomUUID();
	private static final AngebotAnfrage ANFRAGE = AngebotAnfrage.builder().id(ANFRAGE_ID).build();
	private static final AngebotAnfrageEntity ANFRAGE_ENTITY = AngebotAnfrageEntity.builder().id(ANFRAGE_ID).build();

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(angebot(), AngebotEntityConverter.convert(entity()));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(AngebotEntityConverter.convert((AngebotEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt in Entity konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(entity(), AngebotEntityConverter.convert(angebot()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(AngebotEntityConverter.convert((Angebot) null));
	}

	private Angebot angebot() {
		return Angebot.builder() //
				.id(ANGEBOT_ID) //
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

	private AngebotEntity entity() {
		return AngebotEntity.builder() //
				.id(ANGEBOT_ID) //
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
				.anfragen(Arrays.asList(ANFRAGE_ENTITY)) //
				.build();
	}
}
