package io.remedymatch.angebot.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotAnfrageEntityMapper soll")
public class AngebotAnfrageEntityMapperShould {

	private static final UUID ANGEBOT_ANFRAGE_ID = UUID.randomUUID();
	private static final String KOMMENTAR = "Kommentar";
	private static final InstitutionEntity INSTITUTION_VON = InstitutionEntity.builder().id(UUID.randomUUID()).build();
	private static final InstitutionStandortEntity STANDORT_VON = InstitutionStandortEntity.builder().id(UUID.randomUUID()).build();
	private static final InstitutionEntity INSTITUTION_AN= InstitutionEntity.builder().id(UUID.randomUUID()).build();
	private static final InstitutionStandortEntity STANDORT_AN = InstitutionStandortEntity.builder().id(UUID.randomUUID()).build();
	private static final AngebotEntity ANGEBOT = AngebotEntity.builder().id(UUID.randomUUID()).build();
	private static final String PROZESSINSTANZ_ID = "ProzessInstanzId";
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final AngebotAnfrageStatus STATUS = AngebotAnfrageStatus.Offen;

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(angebotAnfrage(), AngebotAnfrageEntityMapper.convert(entity()));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(AngebotAnfrageEntityMapper.convert((AngebotAnfrageEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt in Entity konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(entity(), AngebotAnfrageEntityMapper.convert(angebotAnfrage()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(AngebotAnfrageEntityMapper.convert((AngebotAnfrage) null));
	}
	
	private AngebotAnfrage angebotAnfrage() {
		return AngebotAnfrage.builder() //
				.id(ANGEBOT_ANFRAGE_ID) //
				.kommentar(KOMMENTAR) //
				.institutionVon(INSTITUTION_VON) //
				.standortVon(STANDORT_VON) //
				.institutionAn(INSTITUTION_AN) //
				.standortAn(STANDORT_AN) //
				.angebot(ANGEBOT) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.anzahl(ANZAHL) //
				.status(STATUS) //
				.build();
	}
	
	private AngebotAnfrageEntity entity() {
		return AngebotAnfrageEntity.builder() //
				.id(ANGEBOT_ANFRAGE_ID) //
				.kommentar(KOMMENTAR) //
				.institutionVon(INSTITUTION_VON) //
				.standortVon(STANDORT_VON) //
				.institutionAn(INSTITUTION_AN) //
				.standortAn(STANDORT_AN) //
				.angebot(ANGEBOT) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.anzahl(ANZAHL.doubleValue()) //
				.status(STATUS) //
				.build();
	}
}
