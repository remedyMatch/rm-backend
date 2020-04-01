package io.remedymatch.institution.api;

import java.util.UUID;
import java.util.stream.Collectors;

import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;

public class InstitutionMapper {

	public static InstitutionDTO mapToDTO(InstitutionEntity institution) {
        var builder = InstitutionDTO.builder()
                .id(institution.getId())
                .name(institution.getName())
                .typ(institution.getTyp())
                .institutionKey(institution.getInstitutionKey());

        if (institution.getHauptstandort() != null) {
            builder = builder.hauptstandort(InstitutionStandortMapper.mapToDTO(institution.getHauptstandort()));
        }

        if (institution.getStandorte() != null) {
            builder = builder.standorte(institution.getStandorte().stream()
                    .map(InstitutionStandortMapper::mapToDTO)
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }
	
    public static InstitutionDTO mapToDTO(Institution institution) {
        var builder = InstitutionDTO.builder()
                .id(institution.getId().getValue())
                .name(institution.getName())
                .typ(institution.getTyp())
                .institutionKey(institution.getInstitutionKey());

        if (institution.getHauptstandort() != null) {
            builder = builder.hauptstandort(InstitutionStandortMapper.mapToDTO(institution.getHauptstandort()));
        }

        if (institution.getStandorte() != null) {
            builder = builder.standorte(institution.getStandorte().stream()
                    .map(InstitutionStandortMapper::mapToDTO)
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }

    public static Institution mapToEntity(InstitutionDTO dto) {
        var builder = Institution.builder()
                .id(maptToInstitutionId(dto.getId()))
                .name(dto.getName())
                .typ(dto.getTyp())
                .institutionKey(dto.getInstitutionKey());

        if (dto.getHauptstandort() != null) {
            builder = builder.hauptstandort(InstitutionStandortMapper.mapToStandort(dto.getHauptstandort()));
        }

        if (dto.getStandorte() != null) {
            builder = builder.standorte(dto.getStandorte().stream()
                    .map(InstitutionStandortMapper::mapToStandort)
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }
    
    static InstitutionId maptToInstitutionId(final UUID institutionId)
    {
    	return new InstitutionId(institutionId);
    }
}