package io.remedymatch.bedarf.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionStandortEntityConverter;

final class BedarfAnfrageEntityConverter {

	private BedarfAnfrageEntityConverter() {

	}

	static List<BedarfAnfrage> convertAnfragen(final List<BedarfAnfrageEntity> entities) {
		return entities.stream().map(BedarfAnfrageEntityConverter::convertAnfrage).collect(Collectors.toList());
	}

	static BedarfAnfrage convertAnfrage(final BedarfAnfrageEntity entity) {
		return BedarfAnfrage.builder() //
				.id(new BedarfAnfrageId(entity.getId())) //
				.bedarf(BedarfEntityConverter.convertBedarf(entity.getBedarf())) //
				.institution(InstitutionEntityConverter.convertInstitution(entity.getInstitution())) //
				.standort(InstitutionStandortEntityConverter.convertStandort(entity.getStandort())) //
				.anzahl(entity.getAnzahl()) //
				.kommentar(entity.getKommentar()) //
				.prozessInstanzId(entity.getProzessInstanzId()) //
				.status(entity.getStatus())//
				.build();
	}
}