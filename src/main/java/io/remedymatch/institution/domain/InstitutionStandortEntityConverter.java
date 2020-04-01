package io.remedymatch.institution.domain;

import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity.InstitutionStandortEntityBuilder;

public class InstitutionStandortEntityConverter {

	public static InstitutionStandort convert(final InstitutionStandortEntity entity) {
		if (entity == null) {
			return null;
		}

		return InstitutionStandort.builder()//
				.id(new InstitutionStandortId(entity.getId())) //
				.name(entity.getName()) //
				.plz(entity.getPlz()) //
				.ort(entity.getOrt()) //
				.strasse(entity.getStrasse())//
				.land(entity.getLand()) //
				.longitude(entity.getLongitude()) //
				.latitude(entity.getLatitude()) //
				.build();
	}

	public static InstitutionStandortEntity convert(final InstitutionStandort institutionStandort) {
		if (institutionStandort == null) {
			return null;
		}

		InstitutionStandortEntityBuilder builder = InstitutionStandortEntity.builder();
		if (institutionStandort.getId() != null) {
			builder.id(institutionStandort.getId().getValue());
		}

		return builder
				.name(institutionStandort.getName()) //
				.plz(institutionStandort.getPlz()) //
				.ort(institutionStandort.getOrt()) //
				.strasse(institutionStandort.getStrasse())//
				.land(institutionStandort.getLand()) //
				.longitude(institutionStandort.getLongitude()) //
				.latitude(institutionStandort.getLatitude()) // ;
				.build();
	}
}