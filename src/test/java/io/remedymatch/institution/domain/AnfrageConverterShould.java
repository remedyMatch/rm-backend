package io.remedymatch.institution.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.service.AngebotAnfrageConverterFixtures;
import io.remedymatch.angebot.domain.service.AngebotConverterFixtures;
import io.remedymatch.artikel.domain.service.ArtikelConverterFixtures;
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
		return AngebotAnfrageConverterFixtures.beispielAngebotAnfrage();
	}

	private Anfrage anfrageAusAngebot() {
		return Anfrage.builder() //
				.id(AngebotAnfrageConverterFixtures.ANGEBOT_ANFRAGE_ID.getValue()) //
				.angebotId(AngebotConverterFixtures.ANGEBOT_ID.getValue()) //
				.artikelId(ArtikelConverterFixtures.ARTIKEL_ID.getValue())//
				.artikelVarianteId(ArtikelConverterFixtures.ARTIKEL_VARIANTE_1_ID.getValue())//
				.institutionAn(AngebotConverterFixtures.INSTITUTION) //
				.standortAn(AngebotConverterFixtures.STANDORT) //
				.institutionVon(AngebotAnfrageConverterFixtures.INSTITUTION_VON) //
				.standortVon(AngebotAnfrageConverterFixtures.STANDORT_VON) //
				.anzahl(AngebotAnfrageConverterFixtures.ANZAHL) //
				.kommentar(AngebotAnfrageConverterFixtures.KOMMENTAR) //
				.prozessInstanzId(AngebotAnfrageConverterFixtures.PROZESSINSTANZ_ID) //
				.status(AngebotAnfrageConverterFixtures.STATUS.toString()) //
				.build();
	}

	private BedarfAnfrage bedarfAnfrage() {
		return BedarfAnfrageConverterFixtures.beispielBedarfAnfrage();
	}

	private Anfrage bedarfAusAngebot() {
		return Anfrage.builder() //
				.id(BedarfAnfrageConverterFixtures.BEDARF_ANFRAGE_ID.getValue()) //
				.bedarfId(BedarfConverterFixtures.BEDARF_ID.getValue()) //
				.artikelId(ArtikelConverterFixtures.ARTIKEL_ID.getValue())//
				.artikelVarianteId(ArtikelConverterFixtures.ARTIKEL_VARIANTE_1_ID.getValue())//
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
