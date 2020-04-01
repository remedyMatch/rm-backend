package io.remedymatch.angebot.domain;

import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity.AngebotAnfrageEntityBuilder;
import io.remedymatch.institution.domain.InstitutionStandortEntityConverter;

class AngebotAnfrageEntityConverter {

	static AngebotAnfrage convert(final AngebotAnfrageEntity entity) {
		if (entity == null) {
			return null;
		}

		return AngebotAnfrage.builder() //
				.id(new AngebotAnfrageId(entity.getId())) //
				.angebot(AngebotEntityConverter.convert(entity.getAngebot())) //
				.institutionVon(entity.getInstitutionVon()) //
				.standortVon(InstitutionStandortEntityConverter.convert(entity.getStandortVon())) //
				.kommentar(entity.getKommentar()) //
				.anzahl(entity.getAnzahl()) //
				.prozessInstanzId(entity.getProzessInstanzId()) //
				.status(entity.getStatus())//
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
				.angebot(AngebotEntityConverter.convert(angebotAnfrage.getAngebot())) //
				.institutionVon(angebotAnfrage.getInstitutionVon()) //
				.standortVon(InstitutionStandortEntityConverter.convert(angebotAnfrage.getStandortVon())) //
				.anzahl(angebotAnfrage.getAnzahl()) //
				.kommentar(angebotAnfrage.getKommentar()) //
				.prozessInstanzId(angebotAnfrage.getProzessInstanzId()) //
				.status(angebotAnfrage.getStatus()) //
				.build();
	}
}