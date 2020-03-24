package io.remedymatch.match.api;

import io.remedymatch.institution.api.InstitutionMapper;
import io.remedymatch.match.domain.MatchEntity;
import io.remedymatch.person.api.PersonMapper;

public class MatchMapper {

    public static MatchDTO mapToDTO(MatchEntity entity) {
        return MatchDTO.builder()
                .id(entity.getId())
                .adresseVon(entity.getStandortVon())
                .anfrageId(entity.getAnfrageId())
                .lieferant(PersonMapper.mapToDTO(entity.getLieferant()))
                .status(entity.getStatus())
                .institutionVon(InstitutionMapper.mapToDTO(entity.getInstitutionVon()))
                .institutionAn(InstitutionMapper.mapToDTO(entity.getInstitutionAn()))
                .build();

    }

}
