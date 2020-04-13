package io.remedymatch.institution.controller;

import java.util.UUID;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;

public final class InstitutionMapper {

	private InstitutionMapper() {

	}

	public static InstitutionRO mapToInstitutionRO(Institution institution) {
		return InstitutionRO.builder() //
				.id(institution.getId().getValue()) //
				.name(institution.getName()) //
				.institutionKey(institution.getInstitutionKey()) //
				.typ(institution.getTyp()) //
				.hauptstandort(InstitutionStandortMapper.mapToStandortRO(institution.getHauptstandort())) //
				.standorte(InstitutionStandortMapper.mapToStandorteRO(institution.getStandorte())) //
				.build();
	}

	static InstitutionId maptToInstitutionId(final UUID institutionId) {
		return new InstitutionId(institutionId);
	}
}