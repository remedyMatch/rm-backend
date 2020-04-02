package io.remedymatch.institution.domain;

import io.remedymatch.angebot.domain.AngebotAnfrage;
import io.remedymatch.bedarf.domain.BedarfAnfrage;

class AnfrageConverter {
	static Anfrage convert(AngebotAnfrage angebotAnfrage) {
		return Anfrage.builder() //
				.id(angebotAnfrage.getId().getValue()) //
				.institutionAn(angebotAnfrage.getAngebot().getInstitution()) //
				.standortAn(angebotAnfrage.getAngebot().getStandort()) //
				.institutionVon(angebotAnfrage.getInstitutionVon()) //
				.standortVon(angebotAnfrage.getStandortVon()) //
				.angebotId(angebotAnfrage.getAngebot().getId().getValue()) //
				.anzahl(angebotAnfrage.getAnzahl()) //
				.kommentar(angebotAnfrage.getKommentar()) //
				.prozessInstanzId(angebotAnfrage.getProzessInstanzId()) //
				.status(angebotAnfrage.getStatus().toString()) //
				.build();
	}

	static Anfrage convert(BedarfAnfrage bedarfAnfrage) {
		return Anfrage.builder() //
				.id(bedarfAnfrage.getId().getValue()) //
				.institutionAn(bedarfAnfrage.getBedarf().getInstitution()) //
				.standortAn(bedarfAnfrage.getBedarf().getStandort()) //
				.institutionVon(bedarfAnfrage.getInstitutionVon()) //
				.standortVon(bedarfAnfrage.getStandortVon()) //
				.bedarfId(bedarfAnfrage.getBedarf().getId().getValue()) //
				.anzahl(bedarfAnfrage.getAnzahl()) //
				.kommentar(bedarfAnfrage.getKommentar()) //
				.prozessInstanzId(bedarfAnfrage.getProzessInstanzId()) //
				.status(bedarfAnfrage.getStatus().toString()) //
				.build();
	}
}
