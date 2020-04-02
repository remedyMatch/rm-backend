package io.remedymatch.match.api;

import io.remedymatch.institution.api.InstitutionMapper;
import io.remedymatch.match.domain.Match;

class MatchMapper {
	static MatchDTO mapToDTO(Match match) {
		return MatchDTO.builder() //
				.id(match.getId().getValue()) //
				.anfrageId(match.getAnfrageId()) //
				.status(match.getStatus()) //
				.standortAn(MatchStandortMapper.mapToDTO(match.getStandortAn())) //
				.standortVon(MatchStandortMapper.mapToDTO(match.getStandortVon())) //
				.institutionVon(InstitutionMapper.mapToDTO(match.getInstitutionVon())) //
				.institutionAn(InstitutionMapper.mapToDTO(match.getInstitutionAn())) //
				.entfernung(match.getEntfernung()) //
				.build();
	}
}
