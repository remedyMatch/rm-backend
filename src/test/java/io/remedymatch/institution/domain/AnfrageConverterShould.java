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
import io.remedymatch.bedarf.domain.service.BedarfAnfrageTestFixtures;
import io.remedymatch.bedarf.domain.service.BedarfTestFixtures;
import lombok.val;

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
		val angebotAnfrage = AngebotAnfrageTestFixtures.beispielAngebotAnfrage();
		return Anfrage.builder() //
				.id(angebotAnfrage.getId().getValue()) //
				.angebotId(AngebotTestFixtures.ANGEBOT_ID.getValue()) //
				.artikelId(AngebotTestFixtures.ANGEBOT_ARTIKEL_VARIANTE.getArtikelId().getValue())//
				.artikelVarianteId(AngebotTestFixtures.ANGEBOT_ARTIKEL_VARIANTE.getId().getValue())//
				.institutionAn(AngebotTestFixtures.ANGEBOT_INSTITUTION) //
				.standortAn(AngebotTestFixtures.ANGEBOT_STANDORT) //
				.institutionVon(angebotAnfrage.getInstitution()) //
				.standortVon(angebotAnfrage.getStandort()) //
				.anzahl(angebotAnfrage.getAnzahl()) //
				.kommentar(angebotAnfrage.getKommentar()) //
				.prozessInstanzId(angebotAnfrage.getProzessInstanzId()) //
				.status(angebotAnfrage.getStatus().toString()) //
				.build();
	}

	private BedarfAnfrage bedarfAnfrage() {
		return BedarfAnfrageTestFixtures.beispielBedarfAnfrage();
	}

	private Anfrage bedarfAusAngebot() {
		val bedarfAnfrage = BedarfAnfrageTestFixtures.beispielBedarfAnfrage();
		return Anfrage.builder() //
				.id(bedarfAnfrage.getId().getValue()) //
				.bedarfId(BedarfTestFixtures.BEDARF_ID.getValue()) //
				.artikelId(ArtikelTestFixtures.ARTIKEL_ID.getValue())//
				.artikelVarianteId(ArtikelTestFixtures.ARTIKEL_VARIANTE_1_ID.getValue())//
				.institutionAn(BedarfTestFixtures.BEDARF_INSTITUTION) //
				.standortAn(BedarfTestFixtures.BEDARF_STANDORT) //
				.institutionVon(bedarfAnfrage.getInstitution()) //
				.standortVon(bedarfAnfrage.getStandort()) //
				.anzahl(bedarfAnfrage.getAnzahl()) //
				.kommentar(bedarfAnfrage.getKommentar()) //
				.prozessInstanzId(bedarfAnfrage.getProzessInstanzId()) //
				.status(bedarfAnfrage.getStatus().toString()) //
				.build();
	}
}
