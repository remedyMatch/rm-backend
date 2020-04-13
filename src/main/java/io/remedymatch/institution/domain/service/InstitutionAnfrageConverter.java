package io.remedymatch.institution.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.institution.domain.model.InstitutionAnfrage;

final class InstitutionAnfrageConverter {

	private InstitutionAnfrageConverter() {
	}

	static List<InstitutionAnfrage> convertAngebotAnfragen(final List<AngebotAnfrage> angebotAnfragen) {
		return angebotAnfragen.stream().map(InstitutionAnfrageConverter::convertAngebotAnfrage).collect(Collectors.toList());
	}

	private static InstitutionAnfrage convertAngebotAnfrage(AngebotAnfrage angebotAnfrage) {
		return InstitutionAnfrage.builder() //
				.id(angebotAnfrage.getId().getValue()) //
				.angebotId(angebotAnfrage.getAngebot().getId().getValue()) //
				.institutionAn(angebotAnfrage.getAngebot().getInstitution()) //
				.standortAn(angebotAnfrage.getAngebot().getStandort()) //
				.institutionVon(angebotAnfrage.getInstitution()) //
				.standortVon(angebotAnfrage.getStandort()) //
				.artikelId(angebotAnfrage.getAngebot().getArtikelVariante().getArtikelId()) //
				.artikelVarianteId(angebotAnfrage.getAngebot().getArtikelVariante().getId()) //
				.anzahl(angebotAnfrage.getAnzahl()) //
				.kommentar(angebotAnfrage.getKommentar()) //
				.prozessInstanzId(angebotAnfrage.getProzessInstanzId()) //
				.status(angebotAnfrage.getStatus().toString()) //
				.build();
	}

	static List<InstitutionAnfrage> convertBedarfAnfragen(final List<BedarfAnfrage> bedarfAnfragen) {
		return bedarfAnfragen.stream().map(InstitutionAnfrageConverter::convertBedarfAnfrage).collect(Collectors.toList());
	}

	private static InstitutionAnfrage convertBedarfAnfrage(final BedarfAnfrage bedarfAnfrage) {
		return InstitutionAnfrage.builder() //
				.id(bedarfAnfrage.getId().getValue()) //
				.bedarfId(bedarfAnfrage.getBedarf().getId().getValue()) //
				.institutionAn(bedarfAnfrage.getBedarf().getInstitution()) //
				.standortAn(bedarfAnfrage.getBedarf().getStandort()) //
				.institutionVon(bedarfAnfrage.getInstitution()) //
				.standortVon(bedarfAnfrage.getStandort()) //
				.anzahl(bedarfAnfrage.getAnzahl()) //
				.artikelId(bedarfAnfrage.getBedarf().getArtikel().getId()) //
				.artikelVarianteId(bedarfAnfrage.getBedarf().getArtikelVariante().getId()) //
				.kommentar(bedarfAnfrage.getKommentar()) //
				.prozessInstanzId(bedarfAnfrage.getProzessInstanzId()) //
				.status(bedarfAnfrage.getStatus().toString()) //
				.build();
	}
}
