package io.remedymatch.institution.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.service.AngebotAnfrageTestFixtures;
import io.remedymatch.angebot.domain.service.AngebotTestFixtures;
import io.remedymatch.artikel.domain.service.ArtikelTestFixtures;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageConverterFixtures;
import io.remedymatch.bedarf.domain.service.BedarfConverterFixtures;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotAnfrageEntityConverter soll")
public class AnfrageConverterShould {

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
		return AngebotAnfrageTestFixtures.beispielAngebotAnfrage();
	}

	private Anfrage anfrageAusAngebot() {
		return Anfrage.builder() //
				.id(AngebotAnfrageTestFixtures.beispielAngebotAnfrageId().getValue()) //
				.angebotId(AngebotTestFixtures.ANGEBOT_ID.getValue()) //
				.artikelId(AngebotTestFixtures.ANGEBOT_ARTIKEL_VARIANTE.getArtikelId().getValue())//
				.artikelVarianteId(AngebotTestFixtures.ANGEBOT_ARTIKEL_VARIANTE.getId().getValue())//
				.institutionAn(AngebotTestFixtures.ANGEBOT_INSTITUTION) //
				.standortAn(AngebotTestFixtures.ANGEBOT_STANDORT) //
				.institutionVon(AngebotAnfrageTestFixtures.ANGEBOT_ANFRAGE_INSTITUTION) //
				.standortVon(AngebotAnfrageTestFixtures.ANGEBOT_ANFRAGE_STANDORT) //
				.anzahl(AngebotAnfrageTestFixtures.ANGEBOT_ANFRAGE_ANZAHL) //
				.kommentar(AngebotAnfrageTestFixtures.ANGEBOT_ANFRAGE_KOMMENTAR) //
				.prozessInstanzId(AngebotAnfrageTestFixtures.ANGEBOT_ANFRAGE_PROZESSINSTANZ_ID) //
				.status(AngebotAnfrageTestFixtures.ANGEBOT_ANFRAGE_STATUS.toString()) //
				.build();
	}

	private BedarfAnfrage bedarfAnfrage() {
		return BedarfAnfrageConverterFixtures.beispielBedarfAnfrage();
	}

	private Anfrage bedarfAusAngebot() {
		return Anfrage.builder() //
				.id(BedarfAnfrageConverterFixtures.BEDARF_ANFRAGE_ID.getValue()) //
				.bedarfId(BedarfConverterFixtures.BEDARF_ID.getValue()) //
				.artikelId(ArtikelTestFixtures.ARTIKEL_ID.getValue())//
				.artikelVarianteId(ArtikelTestFixtures.ARTIKEL_VARIANTE_1_ID.getValue())//
				.institutionAn(BedarfConverterFixtures.INSTITUTION) //
				.standortAn(BedarfConverterFixtures.STANDORT) //
				.institutionVon(BedarfAnfrageConverterFixtures.INSTITUTION_VON) //
				.standortVon(BedarfAnfrageConverterFixtures.STANDORT_VON) //
				.anzahl(BedarfAnfrageConverterFixtures.ANZAHL) //
				.kommentar(BedarfAnfrageConverterFixtures.KOMMENTAR) //
				.prozessInstanzId(BedarfAnfrageConverterFixtures.PROZESSINSTANZ_ID) //
				.status(BedarfAnfrageConverterFixtures.STATUS.toString()) //
				.build();
	}
}
