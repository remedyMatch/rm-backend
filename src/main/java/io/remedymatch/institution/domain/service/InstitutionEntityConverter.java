package io.remedymatch.institution.domain.service;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;

public final class InstitutionEntityConverter {
	private InstitutionEntityConverter() {

	}

	public static Institution convertInstitution(final InstitutionEntity entity) {
		if (entity == null) return null;
		
		return Institution.builder() //
				.id(new InstitutionId(entity.getId())) //
				.name(entity.getName()) //
				.institutionKey(entity.getInstitutionKey()) //
				.typ(entity.getTyp()) //
				.hauptstandort(InstitutionStandortEntityConverter.convertStandort(entity.getHauptstandort())) //
				.standorte(InstitutionStandortEntityConverter.convertStandortEntities(entity.getStandorte())) //
				.build();
	}

	public static InstitutionEntity convertInstitution(Institution institution) {
		return InstitutionEntity.builder() //
				.id(institution.getId().getValue()) //
				.name(institution.getName()) //
				.institutionKey(institution.getInstitutionKey()) //
				.typ(institution.getTyp()) //
				.hauptstandort(InstitutionStandortEntityConverter.convertStandort(institution.getHauptstandort())) //
				.standorte(InstitutionStandortEntityConverter.convertStandorte(institution.getStandorte())) //
				.build();
	}
}