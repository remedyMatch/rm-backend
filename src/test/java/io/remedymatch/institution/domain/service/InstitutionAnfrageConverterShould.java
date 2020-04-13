package io.remedymatch.institution.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.service.AngebotAnfrageTestFixtures;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageTestFixtures;
import io.remedymatch.institution.domain.model.InstitutionAnfrage;
import lombok.val;

@ExtendWith(SpringExtension.class)
@DisplayName("InstitutionAnfrageConverter soll")
class InstitutionAnfrageConverterShould {

	@Test
	@DisplayName("AngebotAnfrage in Anfrage konvertieren")
	void angebotAnfragen_in_Anfragen_konvertieren() {
		assertEquals(Arrays.asList(anfrageAusAngebot()),
				InstitutionAnfrageConverter.convertAngebotAnfragen(Arrays.asList(angebotAnfrage())));
	}

	@Test
	@DisplayName("BedarfAnfrage in Anfrage konvertieren")
	void bedarfAnfragen_in_Anfragen_konvertieren() {
		assertEquals(Arrays.asList(bedarfAusAngebot()),
				InstitutionAnfrageConverter.convertBedarfAnfragen(Arrays.asList(bedarfAnfrage())));
	}

	private AngebotAnfrage angebotAnfrage() {
		return AngebotAnfrageTestFixtures.beispielAngebotAnfrage();
	}

	private InstitutionAnfrage anfrageAusAngebot() {
		val angebotAnfrage = AngebotAnfrageTestFixtures.beispielAngebotAnfrage();
		val angebot = angebotAnfrage.getAngebot();

		return InstitutionAnfrage.builder() //
				.id(angebotAnfrage.getId().getValue()) //
				.angebotId(angebot.getId().getValue()) //
				.artikelId(angebot.getArtikelVariante().getArtikelId())//
				.artikelVarianteId(angebot.getArtikelVariante().getId())//
				.institutionAn(angebot.getInstitution()) //
				.standortAn(angebot.getStandort()) //
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

	private InstitutionAnfrage bedarfAusAngebot() {
		val bedarfAnfrage = BedarfAnfrageTestFixtures.beispielBedarfAnfrage();
		val bedarf = bedarfAnfrage.getBedarf();

		return InstitutionAnfrage.builder() //
				.id(bedarfAnfrage.getId().getValue()) //
				.bedarfId(bedarf.getId().getValue()) //
				.artikelId(bedarf.getArtikel().getId())//
				.artikelVarianteId(bedarf.getArtikelVariante().getId())//
				.institutionAn(bedarf.getInstitution()) //
				.standortAn(bedarf.getStandort()) //
				.institutionVon(bedarfAnfrage.getInstitution()) //
				.standortVon(bedarfAnfrage.getStandort()) //
				.anzahl(bedarfAnfrage.getAnzahl()) //
				.kommentar(bedarfAnfrage.getKommentar()) //
				.prozessInstanzId(bedarfAnfrage.getProzessInstanzId()) //
				.status(bedarfAnfrage.getStatus().toString()) //
				.build();
	}
}
