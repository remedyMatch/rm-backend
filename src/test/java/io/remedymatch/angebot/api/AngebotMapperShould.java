package io.remedymatch.angebot.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.Angebot;
import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.institution.api.InstitutionStandortDTO;
import io.remedymatch.institution.domain.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotMapper soll")
public class AngebotMapperShould {

	private static final UUID ANGEBOT_ID = UUID.randomUUID();
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final BigDecimal REST = BigDecimal.valueOf(120.0);
	private static final UUID ARTIKEL_ID = UUID.randomUUID();
	private static final ArtikelEntity ARTIKEL_ENTITY = ArtikelEntity.builder().id(ARTIKEL_ID).build();
	private static final ArtikelDTO ARTIKEL_DTO = ArtikelDTO.builder().id(ARTIKEL_ID).build();
	private static final UUID STANDORT_ID = UUID.randomUUID();
	private static final InstitutionStandortDTO STANDORT_DTO = InstitutionStandortDTO.builder().id(STANDORT_ID)
			.build();
	private static final InstitutionStandortEntity STANDORT_ENTITY = InstitutionStandortEntity.builder().id(STANDORT_ID)
			.build();
	private static final LocalDateTime HALTBARKEIT = LocalDateTime.now();
	private static final boolean STERIL = true;
	private static final boolean ORIGINALVERPACKT = true;
	private static final boolean MEDIZINISCH = true;
	private static final String KOMMENTAR = "Kommentar";
	private static final boolean BEDIENT = false;

	@Test
	@DisplayName("Dto in Domain Objekt mappen")
	void dto_in_Domain_Objekt_konvertieren() {
		assertEquals(angebot(), AngebotMapper.mapToAngebot(dto()));
	}

	@Test
	@DisplayName("null DTO in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(AngebotMapper.mapToAngebot((AngebotDTO) null));
	}

	@Test
	@DisplayName("Domain Objekt in DTO konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(dto(), AngebotMapper.mapToDto(angebot()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(AngebotMapper.mapToDto((Angebot) null));
	}

	private Angebot angebot() {
		return Angebot.builder() //
				.id(ANGEBOT_ID) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikel(ARTIKEL_ENTITY) //
				.standort(STANDORT_ENTITY) //
				.haltbarkeit(HALTBARKEIT) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.build();
	}

	private AngebotDTO dto() {
		return AngebotDTO.builder() //
				.id(ANGEBOT_ID) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikel(ARTIKEL_DTO) //
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
