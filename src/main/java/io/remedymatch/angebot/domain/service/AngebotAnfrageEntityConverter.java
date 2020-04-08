package io.remedymatch.angebot.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity.AngebotAnfrageEntityBuilder;
import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.institution.domain.InstitutionStandortEntityConverter;

class AngebotAnfrageEntityConverter {

	static List<AngebotAnfrage> convertAnfragen(final List<AngebotAnfrageEntity> entities) {
		return entities.stream().map(AngebotAnfrageEntityConverter::convertAnfrage).collect(Collectors.toList());
	}

	static AngebotAnfrage convertAnfrage(final AngebotAnfrageEntity entity) {
		return AngebotAnfrage.builder() //
				.id(new AngebotAnfrageId(entity.getId())) //
				.angebot(AngebotEntityConverter.convert(entity.getAngebot())) //
				.institutionVon(InstitutionEntityConverter.convert(entity.getInstitutionVon())) //
				.standortVon(InstitutionStandortEntityConverter.convert(entity.getStandortVon())) //
				.anzahl(entity.getAnzahl()) //
				.kommentar(entity.getKommentar()) //
				.prozessInstanzId(entity.getProzessInstanzId()) //
				.status(entity.getStatus())//
				.build();
	}

	static AngebotAnfrageEntity convert(final AngebotAnfrage angebotAnfrage) {
		if (angebotAnfrage == null) {
			return null;
		}

		AngebotAnfrageEntityBuilder builder = AngebotAnfrageEntity.builder();
		if (angebotAnfrage.getId() != null) {
			builder.id(angebotAnfrage.getId().getValue());
		}

		return builder //
				.angebot(AngebotEntityConverter.convert(angebotAnfrage.getAngebot())) //
				.institutionVon(InstitutionEntityConverter.convert(angebotAnfrage.getInstitutionVon())) //
				.standortVon(InstitutionStandortEntityConverter.convert(angebotAnfrage.getStandortVon())) //
				.anzahl(angebotAnfrage.getAnzahl()) //
				.kommentar(angebotAnfrage.getKommentar()) //
				.prozessInstanzId(angebotAnfrage.getProzessInstanzId()) //
				.status(angebotAnfrage.getStatus()) //
				.build();
	}
}