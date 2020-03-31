package io.remedymatch.angebot.domain;

import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity.AngebotAnfrageEntityBuilder;

class AngebotAnfrageEntityConverter {

	static AngebotAnfrage convert(final AngebotAnfrageEntity entity) {
		if (entity == null) {
			return null;
		}

		return AngebotAnfrage.builder() //
				.id(new AngebotAnfrageId(entity.getId())) //
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

	static AngebotAnfrageEntity convert(final AngebotAnfrage angebotAnfrage) {
		if (angebotAnfrage == null) {
			return null;
		}

		AngebotAnfrageEntityBuilder builder = AngebotAnfrageEntity.builder();
		if (angebotAnfrage.getId() != null)
		{
			builder.id(angebotAnfrage.getId().getValue());
		}
		
		return builder //
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