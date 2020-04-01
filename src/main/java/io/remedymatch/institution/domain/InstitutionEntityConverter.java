package io.remedymatch.institution.domain;

import java.util.stream.Collectors;

import io.remedymatch.institution.domain.infrastructure.InstitutionEntity;

class InstitutionEntityConverter {

	static Institution convert(InstitutionEntity entity) {
		if (entity == null) {
			return null;
		}

		var builder = Institution.builder() //
				.id(new InstitutionId(entity.getId())) //
				.name(entity.getName()) //
				.typ(entity.getTyp()) //
				.institutionKey(entity.getInstitutionKey()) //
				.hauptstandort(InstitutionStandortEntityConverter.convert(entity.getHauptstandort()));

		if (entity.getStandorte() != null) {
			builder = builder.standorte(entity.getStandorte().stream().map(InstitutionStandortEntityConverter::convert)
					.collect(Collectors.toList()));
		}

		return builder.build();
	}

	static InstitutionEntity convert(Institution institution) {
		if (institution == null) {
			return null;
		}

		var builder = InstitutionEntity.builder();
		if (institution.getId() != null) {
			builder.id(institution.getId().getValue());
		}

		builder.name(institution.getName()) //
				.typ(institution.getTyp()) //
				.institutionKey(institution.getInstitutionKey()) //
				.hauptstandort(InstitutionStandortEntityConverter.convert(institution.getHauptstandort()));

		if (institution.getStandorte() != null) {
			builder = builder.standorte(institution.getStandorte().stream()
					.map(InstitutionStandortEntityConverter::convert).collect(Collectors.toList()));
		}

		return builder.build();
	}
}