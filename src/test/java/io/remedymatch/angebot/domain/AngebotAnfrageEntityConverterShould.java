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
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotAnfrageEntityConverter soll")
public class AngebotAnfrageEntityConverterShould {

	private static final AngebotAnfrageId ANGEBOT_ANFRAGE_ID = new AngebotAnfrageId(UUID.randomUUID());
	private static final String KOMMENTAR = "Kommentar";
	private static final InstitutionEntity INSTITUTION_VON = InstitutionEntity.builder().id(UUID.randomUUID()).build();
	private static final InstitutionStandortEntity STANDORT_VON = InstitutionStandortEntity.builder().id(UUID.randomUUID()).build();
	private static final InstitutionEntity INSTITUTION_AN= InstitutionEntity.builder().id(UUID.randomUUID()).build();
	private static final InstitutionStandortEntity STANDORT_AN = InstitutionStandortEntity.builder().id(UUID.randomUUID()).build();
	private static final AngebotId ANGEBOT_ID = new AngebotId(UUID.randomUUID());
	private static final Angebot ANGEBOT = Angebot.builder().id(ANGEBOT_ID).build();
	private static final AngebotEntity ANGEBOT_ENTITY = AngebotEntity.builder().id(ANGEBOT_ID.getValue()).build();
	private static final String PROZESSINSTANZ_ID = "ProzessInstanzId";
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final AngebotAnfrageStatus STATUS = AngebotAnfrageStatus.Offen;

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(angebotAnfrage(true), AngebotAnfrageEntityConverter.convert(entity(true)));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(AngebotAnfrageEntityConverter.convert((AngebotAnfrageEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt mit Id in Entity konvertieren")
	void domain_Objekt_mit_Id_in_Entity_konvertieren() {
		assertEquals(entity(true), AngebotAnfrageEntityConverter.convert(angebotAnfrage(true)));
	}

	@Test
	@DisplayName("Domain Objekt ohne Id in Entity konvertieren")
	void domain_Objekt_ohne_Id_in_Entity_konvertieren() {
		assertEquals(entity(false), AngebotAnfrageEntityConverter.convert(angebotAnfrage(false)));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(AngebotAnfrageEntityConverter.convert((AngebotAnfrage) null));
	}
	
	private AngebotAnfrage angebotAnfrage(boolean mitId) {
		return AngebotAnfrage.builder() //
				.id(mitId ? ANGEBOT_ANFRAGE_ID : null) //
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
	
	private AngebotAnfrageEntity entity(boolean mitId) {
		return AngebotAnfrageEntity.builder() //
				.id(mitId ? ANGEBOT_ANFRAGE_ID.getValue() : null) //
				.kommentar(KOMMENTAR) //
				.institutionVon(INSTITUTION_VON) //
				.standortVon(STANDORT_VON) //
				.institutionAn(INSTITUTION_AN) //
				.standortAn(STANDORT_AN) //
				.angebot(ANGEBOT_ENTITY) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.anzahl(ANZAHL) //
				.status(STATUS) //
				.build();
	}
}
