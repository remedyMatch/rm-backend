package io.remedymatch.institution.domain;

import java.math.BigDecimal;

import io.remedymatch.angebot.domain.AngebotAnfrage;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageEntity;

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

	static Anfrage convert(BedarfAnfrageEntity angebotBedarf) {
		return Anfrage.builder() //
				.id(angebotBedarf.getId()) //
				.institutionAn(InstitutionEntityConverter.convert(angebotBedarf.getBedarf().getInstitution())) //
				.standortAn(InstitutionStandortEntityConverter.convert(angebotBedarf.getBedarf().getStandort())) //
				.institutionVon(InstitutionEntityConverter.convert(angebotBedarf.getInstitutionVon())) //
				.standortVon(InstitutionStandortEntityConverter.convert(angebotBedarf.getStandortVon())) //
				.bedarfId(angebotBedarf.getBedarf().getId()) //
				.anzahl(BigDecimal.valueOf(angebotBedarf.getAnzahl())) //
				.kommentar(angebotBedarf.getKommentar()) //
				.prozessInstanzId(angebotBedarf.getProzessInstanzId()) //
				.status(angebotBedarf.getStatus().toString()) //
				.build();
	}
}
