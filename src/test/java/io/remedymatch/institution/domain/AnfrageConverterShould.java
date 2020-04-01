package io.remedymatch.institution.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.Angebot;
import io.remedymatch.angebot.domain.AngebotAnfrage;
import io.remedymatch.angebot.domain.AngebotAnfrageId;
import io.remedymatch.angebot.domain.AngebotAnfrageStatus;
import io.remedymatch.angebot.domain.AngebotId;
import io.remedymatch.bedarf.domain.BedarfEntity;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageEntity;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageStatus;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotAnfrageEntityConverter soll")
public class AnfrageConverterShould {

	private static final AngebotAnfrageId ANGEBOT_ANFRAGE_ID = new AngebotAnfrageId(UUID.randomUUID());
	private static final UUID BEDARF_ANFRAGE_ID = UUID.randomUUID();
	private static final String KOMMENTAR = "Kommentar";
	private static final InstitutionId INSTITUTION_AN_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION_AN = Institution.builder().id(INSTITUTION_AN_ID).build();
	private static final InstitutionEntity INSTITUTION_AN_ENTITY = InstitutionEntity.builder()
			.id(INSTITUTION_AN_ID.getValue()).build();
	private static final InstitutionStandortId STANDORT_AN_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandort STANDORT_AN = InstitutionStandort.builder().id(STANDORT_AN_ID).build();
	private static final InstitutionStandortEntity STANDORT_AN_ENTITY = InstitutionStandortEntity.builder()
			.id(STANDORT_AN_ID.getValue()).build();
	private static final InstitutionId INSTITUTION_VON_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION_VON = Institution.builder().id(INSTITUTION_VON_ID).build();
	private static final InstitutionEntity INSTITUTION_VON_ENTITY = InstitutionEntity.builder()
			.id(INSTITUTION_VON_ID.getValue()).build();
	private static final InstitutionStandortId STANDORT_VON_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandort STANDORT_VON = InstitutionStandort.builder().id(STANDORT_VON_ID).build();
	private static final InstitutionStandortEntity STANDORT_VON_ENTITY = InstitutionStandortEntity.builder()
			.id(STANDORT_VON_ID.getValue()).build();
	private static final AngebotId ANGEBOT_ID = new AngebotId(UUID.randomUUID());
	private static final Angebot ANGEBOT = Angebot.builder().id(ANGEBOT_ID).institution(INSTITUTION_AN).standort(STANDORT_AN).build();
	private static final UUID BEDARF_ID = UUID.randomUUID();
	private static final BedarfEntity BEDARF = BedarfEntity.builder().id(BEDARF_ID).institution(INSTITUTION_AN_ENTITY).standort(STANDORT_AN_ENTITY).build();
	private static final String PROZESSINSTANZ_ID = "ProzessInstanzId";
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final AngebotAnfrageStatus ANGEBOT_ANFRAGE_STATUS = AngebotAnfrageStatus.Offen;
	private static final BedarfAnfrageStatus BEDARF_ANFRAGE_STATUS = BedarfAnfrageStatus.Offen;

	@Test
	@DisplayName("AngebotAnfrage in Anfrage konvertieren")
	void angebotAnfrage_in_Anfrage_konvertieren() {
		assertEquals(anfrageAusAngebot(), AnfrageConverter.convert(angebotAnfrage()));
	}

	@Test
	@DisplayName("BedarfAnfrage in Anfrage konvertieren")
	void bedarfAnfrage_in_Anfrage_konvertieren() {
		assertEquals(bedarfAusAngebot(), AnfrageConverter.convert(bedarfAnfrage()));
	}

	private AngebotAnfrage angebotAnfrage() {
		return AngebotAnfrage.builder() //
				.id(ANGEBOT_ANFRAGE_ID) //
				.angebot(ANGEBOT) //
				.institutionVon(INSTITUTION_VON) //
				.standortVon(STANDORT_VON) //
				.anzahl(ANZAHL) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(ANGEBOT_ANFRAGE_STATUS) //
				.build();
	}

	private Anfrage anfrageAusAngebot() {
		return Anfrage.builder() //
				.id(ANGEBOT_ANFRAGE_ID.getValue()) //
				.angebotId(ANGEBOT.getId().getValue()) //
				.institutionAn(INSTITUTION_AN) //
				.standortAn(STANDORT_AN) //
				.institutionVon(INSTITUTION_VON) //
				.standortVon(STANDORT_VON) //
				.anzahl(ANZAHL) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(ANGEBOT_ANFRAGE_STATUS.toString()) //
				.build();
	}

	private BedarfAnfrageEntity bedarfAnfrage() {
		return BedarfAnfrageEntity.builder() //
				.id(BEDARF_ANFRAGE_ID) //
				.bedarf(BEDARF) //
				.institutionVon(INSTITUTION_VON_ENTITY) //
				.standortVon(STANDORT_VON_ENTITY) //
				.anzahl(ANZAHL.doubleValue()) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(BEDARF_ANFRAGE_STATUS) //
				.build();
	}

	private Anfrage bedarfAusAngebot() {
		return Anfrage.builder() //
				.id(BEDARF_ANFRAGE_ID) //
				.bedarfId(BEDARF_ID) //
				.institutionAn(INSTITUTION_AN) //
				.standortAn(STANDORT_AN) //
				.institutionVon(INSTITUTION_VON) //
				.standortVon(STANDORT_VON) //
				.anzahl(ANZAHL) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(BEDARF_ANFRAGE_STATUS.toString()) //
				.build();
	}
}
