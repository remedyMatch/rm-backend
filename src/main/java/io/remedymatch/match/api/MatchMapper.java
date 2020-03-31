package io.remedymatch.match.api;

import io.remedymatch.institution.api.InstitutionMapper;
import io.remedymatch.match.domain.MatchEntity;

public class MatchMapper {

    public static MatchDTO mapToDTO(MatchEntity entity) {
        return MatchDTO.builder()
                .id(entity.getId())
                .anfrageId(entity.getAnfrageId())
                .status(entity.getStatus())
                .standortAn(MatchStandortMapper.mapToDTO(entity.getStandortAn()))
                .standortVon(MatchStandortMapper.mapToDTO(entity.getStandortVon()))
                .institutionVon(InstitutionMapper.mapToDTO(entity.getInstitutionVon()))
                .institutionAn(InstitutionMapper.mapToDTO(entity.getInstitutionAn()))
                .build();
    }

}
