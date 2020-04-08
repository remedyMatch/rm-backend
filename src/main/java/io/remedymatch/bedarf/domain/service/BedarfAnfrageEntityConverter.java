package io.remedymatch.bedarf.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity.BedarfAnfrageEntityBuilder;
import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.institution.domain.InstitutionStandortEntityConverter;

class BedarfAnfrageEntityConverter {

	static List<BedarfAnfrage> convertAnfragen(final List<BedarfAnfrageEntity> entities) {
		return entities.stream().map(BedarfAnfrageEntityConverter::convertAnfrage).collect(Collectors.toList());
	}

	static BedarfAnfrage convertAnfrage(final BedarfAnfrageEntity entity) {
		return BedarfAnfrage.builder() //
				.id(new BedarfAnfrageId(entity.getId())) //
				.bedarf(BedarfEntityConverter.convert(entity.getBedarf())) //
				.institutionVon(InstitutionEntityConverter.convert(entity.getInstitutionVon())) //
				.standortVon(InstitutionStandortEntityConverter.convert(entity.getStandortVon())) //
				.anzahl(entity.getAnzahl()) //
				.kommentar(entity.getKommentar()) //
				.prozessInstanzId(entity.getProzessInstanzId()) //
				.status(entity.getStatus())//
				.build();
	}

	static BedarfAnfrageEntity convert(final BedarfAnfrage bedarfAnfrage) {
		if (bedarfAnfrage == null) {
			return null;
		}

		BedarfAnfrageEntityBuilder builder = BedarfAnfrageEntity.builder();
		if (bedarfAnfrage.getId() != null) {
			builder.id(bedarfAnfrage.getId().getValue());
		}

		return builder //
				.bedarf(BedarfEntityConverter.convert(bedarfAnfrage.getBedarf())) //
				.institutionVon(InstitutionEntityConverter.convert(bedarfAnfrage.getInstitutionVon())) //
				.standortVon(InstitutionStandortEntityConverter.convert(bedarfAnfrage.getStandortVon())) //
				.anzahl(bedarfAnfrage.getAnzahl()) //
				.kommentar(bedarfAnfrage.getKommentar()) //
				.prozessInstanzId(bedarfAnfrage.getProzessInstanzId()) //
				.status(bedarfAnfrage.getStatus()) //
				.build();
	}
}