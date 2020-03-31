package io.remedymatch.angebot.domain;

import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;

class AngebotAnfrageEntityConverter {

	static AngebotAnfrage convert(AngebotAnfrageEntity entity) {
		if (entity == null) {
			return null;
		}

		return AngebotAnfrage.builder() //
				.id(entity.getId()) //
				.kommentar(entity.getKommentar()) //
				.institutionAn(entity.getInstitutionAn()) //
				.institutionVon(entity.getInstitutionVon()) //
				.standortAn(entity.getStandortAn()) //
				.standortVon(entity.getStandortVon()) //
				.angebot(AngebotEntityConverter.convert(entity.getAngebot())) //
				.status(entity.getStatus())//
				.anzahl(entity.getAnzahl()) //
				.prozessInstanzId(entity.getProzessInstanzId()) //
				.build();
	}

	static AngebotAnfrageEntity convert(AngebotAnfrage angebotAnfrage) {
		if (angebotAnfrage == null) {
			return null;
		}

		return AngebotAnfrageEntity.builder() //
				.id(angebotAnfrage.getId()) //
				.kommentar(angebotAnfrage.getKommentar()) //
				.institutionAn(angebotAnfrage.getInstitutionAn()) //
				.institutionVon(angebotAnfrage.getInstitutionVon()) //
				.standortAn(angebotAnfrage.getStandortAn()) //
				.standortVon(angebotAnfrage.getStandortVon()) //
				.angebot(AngebotEntityConverter.convert(angebotAnfrage.getAngebot())) //
				.status(angebotAnfrage.getStatus()) //
				.anzahl(angebotAnfrage.getAnzahl()) //
				.prozessInstanzId(angebotAnfrage.getProzessInstanzId()) //
				.build();
	}
}