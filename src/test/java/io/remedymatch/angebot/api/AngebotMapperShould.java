package io.remedymatch.angebot.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.Angebot;
import io.remedymatch.angebot.domain.AngebotId;
import io.remedymatch.angebot.domain.NeueAngebot;
import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.artikel.domain.ArtikelId;
import io.remedymatch.institution.api.InstitutionStandortDTO;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
import io.remedymatch.institution.domain.InstitutionStandortId;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotMapper soll")
public class AngebotMapperShould {

	private static final AngebotId ANGEBOT_ID = new AngebotId(UUID.randomUUID());
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final BigDecimal REST = BigDecimal.valueOf(120.0);
	private static final ArtikelId ARTIKEL_ID = new ArtikelId(UUID.randomUUID());
	private static final ArtikelEntity ARTIKEL_ENTITY = ArtikelEntity.builder().id(ARTIKEL_ID.getValue()).build();
	private static final ArtikelDTO ARTIKEL_DTO = ArtikelDTO.builder().id(ARTIKEL_ID.getValue()).build();
	private static final InstitutionStandortId STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandortDTO STANDORT_DTO = InstitutionStandortDTO.builder().id(STANDORT_ID.getValue()).build();
	private static final InstitutionStandortEntity STANDORT_ENTITY = InstitutionStandortEntity.builder().id(STANDORT_ID.getValue())
			.build();
	private static final LocalDateTime HALTBARKEIT = LocalDateTime.now();
	private static final boolean STERIL = true;
	private static final boolean ORIGINALVERPACKT = true;
	private static final boolean MEDIZINISCH = true;
	private static final String KOMMENTAR = "Kommentar";
	private static final boolean BEDIENT = false;

	@Test
	@DisplayName("NeueAngebot konvertieren koennen")
	void neueAngebot_konvertieren_koennen() {
		assertEquals(neueAngebot(), AngebotMapper.mapToNeueAngebot(neueAngebotRequest()));
	}
	
	@Test
	@DisplayName("eine IllegalArgumentException wenn NeueAngebotRequest null ist")
	void eine_IllegalArgumentException_werfen_wenn_NeueAngebotRequest_null_ist() {
		assertThrows(IllegalArgumentException.class, //
				() -> AngebotMapper.mapToNeueAngebot(null));
	}

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

	/* help methods */ 
	
	private NeueAngebot neueAngebot() {
		return NeueAngebot.builder() //
				.anzahl(ANZAHL) //
				.artikelId(ARTIKEL_ID) //
				.standortId(STANDORT_ID) //
				.haltbarkeit(HALTBARKEIT) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.build();
	}
	
	private NeueAngebotRequest neueAngebotRequest() {
		return NeueAngebotRequest.builder() //
				.anzahl(ANZAHL) //
				.artikelId(ARTIKEL_ID.getValue()) //
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
				.id(ANGEBOT_ID.getValue()) //
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
