package io.remedymatch.bedarf.domain;

import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity.BedarfAnfrageEntityBuilder;
import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.institution.domain.InstitutionStandortEntityConverter;

class BedarfAnfrageEntityConverter {

	static BedarfAnfrage convert(final BedarfAnfrageEntity entity) {
		if (entity == null) {
			return null;
		}

		return BedarfAnfrage.builder() //
				.id(new BedarfAnfrageId(entity.getId())) //
				.bedarf(BedarfEntityConverter.convert(entity.getBedarf())) //
				.institutionVon(InstitutionEntityConverter.convert(entity.getInstitutionVon())) //
				.standortVon(InstitutionStandortEntityConverter.convert(entity.getStandortVon())) //
				.kommentar(entity.getKommentar()) //
				.anzahl(entity.getAnzahl()) //
				.prozessInstanzId(entity.getProzessInstanzId()) //
				.status(entity.getStatus())//
				.build();
	}

	static BedarfAnfrageEntity convert(final BedarfAnfrage bedarfAnfrage) {
		if (bedarfAnfrage == null) {
			return null;
		}

		BedarfAnfrageEntityBuilder builder = BedarfAnfrageEntity.builder();
		if (bedarfAnfrage.getId() != null)
		{
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