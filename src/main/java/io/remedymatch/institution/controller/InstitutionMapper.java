package io.remedymatch.institution.controller;

import java.util.UUID;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionUpdate;

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

	static InstitutionUpdate mapToUpdate(final InstitutionUpdateRequest institutionUpdateRequest) {
		return InstitutionUpdate.builder()//
				.neueName(institutionUpdateRequest.getName()) //
				.neuesTyp(institutionUpdateRequest.getTyp()) //
				.neuesHauptstandortId(institutionUpdateRequest.getHauptstandortId() != null
						? InstitutionStandortMapper.mapToStandortId(institutionUpdateRequest.getHauptstandortId())
						: null) //
				.build();
	}

	static InstitutionId mapToInstitutionId(final UUID institutionId) {
		return new InstitutionId(institutionId);
	}
}