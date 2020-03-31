package io.remedymatch.institution.api;

import io.remedymatch.institution.domain.InstitutionStandortEntity;

public class InstitutionStandortMapper {

    public static InstitutionStandortDTO mapToDTO(InstitutionStandortEntity entity) {
        return InstitutionStandortDTO.builder()
                .id(entity.getId())
                .land(entity.getLand())
                .ort(entity.getOrt())
                .plz(entity.getPlz())
                .strasse(entity.getStrasse())
                .longitude(entity.getLongitude())
                .latitude(entity.getLatitude())
                .build();
    }

    public static InstitutionStandortEntity mapToEntity(InstitutionStandortDTO dto) {
        return InstitutionStandortEntity.builder()
                .id(dto.getId())
                .land(dto.getLand())
                .ort(dto.getOrt())
                .plz(dto.getPlz())
                .strasse(dto.getStrasse())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .build();
    }

}
