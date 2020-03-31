package io.remedymatch.angebot.domain;

import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;

public class AngebotAnfrageEntityMapper {

	public static AngebotAnfrage convert(AngebotAnfrageEntity entity) {
		return AngebotAnfrage.builder() //
				.id(entity.getId()) //
				.kommentar(entity.getKommentar()) //
				.institutionAn(entity.getInstitutionAn()) //
				.institutionVon(entity.getInstitutionVon()) //
				.standortAn(entity.getStandortAn()) //
				.standortVon(entity.getStandortVon()) //
				.status(entity.getStatus())//
				.anzahl(entity.getAnzahl()) //
				.prozessInstanzId(entity.getProzessInstanzId()) //
				.build();
	}

	public static AngebotAnfrageEntity convert(AngebotAnfrage angebotAnfrage) {
		return AngebotAnfrageEntity.builder() //
				.id(angebotAnfrage.getId()) //
				.kommentar(angebotAnfrage.getKommentar()) //
				.institutionAn(angebotAnfrage.getInstitutionAn()) //
				.institutionVon(angebotAnfrage.getInstitutionVon()) //
				.standortAn(angebotAnfrage.getStandortAn()) //
				.standortVon(angebotAnfrage.getStandortVon()) //
				.status(angebotAnfrage.getStatus()) //
				.anzahl(angebotAnfrage.getAnzahl()) //
				.prozessInstanzId(angebotAnfrage.getProzessInstanzId()) //
				.build();
	}
}