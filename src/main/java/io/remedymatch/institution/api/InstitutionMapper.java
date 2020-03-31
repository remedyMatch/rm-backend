package io.remedymatch.institution.api;

import io.remedymatch.institution.domain.InstitutionEntity;

import java.util.stream.Collectors;

public class InstitutionMapper {

    public static InstitutionDTO mapToDTO(InstitutionEntity entity) {
        var builder = InstitutionDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .typ(entity.getTyp())
                .institutionKey(entity.getInstitutionKey());

        if (entity.getHauptstandort() != null) {
            builder = builder.hauptstandort(InstitutionStandortMapper.mapToDTO(entity.getHauptstandort()));
        }

        if (entity.getStandorte() != null) {
            builder = builder.standorte(entity.getStandorte().stream()
                    .map(InstitutionStandortMapper::mapToDTO)
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }

    public static InstitutionEntity mapToEntity(InstitutionDTO dto) {
        var builder = InstitutionEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .typ(dto.getTyp())
                .institutionKey(dto.getInstitutionKey());

        if (dto.getHauptstandort() != null) {
            builder = builder.hauptstandort(InstitutionStandortMapper.mapToEntity(dto.getHauptstandort()));
        }

        if (dto.getStandorte() != null) {
            builder = builder.standorte(dto.getStandorte().stream()
                    .map(InstitutionStandortMapper::mapToEntity)
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }
}