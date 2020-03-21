package io.remedymatch.institution.api;

import io.remedymatch.institution.domain.InstitutionEntity;

public class InstitutionMapper {

    public static InstitutionDTO mapToDTO(InstitutionEntity entity) {
        return InstitutionDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .typ(entity.getTyp())
                .build();
    }

    public static InstitutionEntity mapToEntity(InstitutionDTO dto) {
        return InstitutionEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .typ(dto.getTyp())
                .build();
    }
}