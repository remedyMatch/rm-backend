package io.remedymatch.match.api;

import io.remedymatch.match.domain.MatchStandortEntity;

public class MatchStandortMapper {

    public static MatchStandortDTO mapToDTO(MatchStandortEntity entity) {
        return MatchStandortDTO.builder()
                .id(entity.getId())
                .land(entity.getLand())
                .ort(entity.getOrt())
                .plz(entity.getPlz())
                .strasse(entity.getStrasse())
                .build();
    }

    public static MatchStandortEntity mapToEntity(MatchStandortDTO dto) {
        return MatchStandortEntity.builder()
                .id(dto.getId())
                .land(dto.getLand())
                .ort(dto.getOrt())
                .plz(dto.getPlz())
                .strasse(dto.getStrasse())
                .build();
    }

}
