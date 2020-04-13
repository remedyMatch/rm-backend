package io.remedymatch.institution.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

public final class InstitutionStandortEntityConverter {

	private InstitutionStandortEntityConverter() {

	}

	static List<InstitutionStandort> convertStandortEntities(final List<InstitutionStandortEntity> entities) {
		return entities.stream().map(InstitutionStandortEntityConverter::convertStandort).collect(Collectors.toList());
	}

	public static InstitutionStandort convertStandort(final InstitutionStandortEntity entity) {
		if (entity == null) {
			return null;
		}
		
		return InstitutionStandort.builder() //
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

	static List<InstitutionStandortEntity> convertStandorte(final List<InstitutionStandort> standorte) {
		return standorte.stream().map(InstitutionStandortEntityConverter::convertStandort).collect(Collectors.toList());
	}

	public static InstitutionStandortEntity convertStandort(final InstitutionStandort institutionStandort) {
		if (institutionStandort == null) {
			return null;
		}
		
		return InstitutionStandortEntity.builder() //
				.id(institutionStandort.getId().getValue()) //
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