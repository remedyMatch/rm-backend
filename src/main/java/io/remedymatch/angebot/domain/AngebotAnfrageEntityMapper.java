package io.remedymatch.angebot.domain;

import java.math.BigDecimal;

import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;

class AngebotAnfrageEntityMapper {

	static AngebotAnfrage convert(AngebotAnfrageEntity entity) {
		if (entity == null)
		{
			return null;
		}
		
		return AngebotAnfrage.builder() //
				.id(entity.getId()) //
				.kommentar(entity.getKommentar()) //
				.institutionAn(entity.getInstitutionAn()) //
				.institutionVon(entity.getInstitutionVon()) //
				.standortAn(entity.getStandortAn()) //
				.standortVon(entity.getStandortVon()) //
				.angebot(entity.getAngebot()) //
				.status(entity.getStatus())//
				.anzahl(BigDecimal.valueOf(entity.getAnzahl())) //
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
				.angebot(angebotAnfrage.getAngebot()) //
				.status(angebotAnfrage.getStatus()) //
				.anzahl(angebotAnfrage.getAnzahl().doubleValue()) //
				.prozessInstanzId(angebotAnfrage.getProzessInstanzId()) //
				.build();
	}
}