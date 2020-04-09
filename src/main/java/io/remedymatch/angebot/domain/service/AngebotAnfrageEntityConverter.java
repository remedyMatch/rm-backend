package io.remedymatch.angebot.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.institution.domain.InstitutionStandortEntityConverter;

final class AngebotAnfrageEntityConverter {

	private AngebotAnfrageEntityConverter() {

	}

	static List<AngebotAnfrage> convertAnfragen(final List<AngebotAnfrageEntity> entities) {
		return entities.stream().map(AngebotAnfrageEntityConverter::convertAnfrage).collect(Collectors.toList());
	}

	static AngebotAnfrage convertAnfrage(final AngebotAnfrageEntity entity) {
		return AngebotAnfrage.builder() //
				.id(new AngebotAnfrageId(entity.getId())) //
				.angebot(AngebotEntityConverter.convertAngebot(entity.getAngebot())) //
				.institutionVon(InstitutionEntityConverter.convert(entity.getInstitutionVon())) //
				.standortVon(InstitutionStandortEntityConverter.convert(entity.getStandortVon())) //
				.anzahl(entity.getAnzahl()) //
				.kommentar(entity.getKommentar()) //
				.prozessInstanzId(entity.getProzessInstanzId()) //
				.status(entity.getStatus())//
				.build();
	}
}