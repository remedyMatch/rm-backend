package io.remedymatch.anfrage.api;

import io.remedymatch.anfrage.domain.AnfrageEntity;

public class AnfrageMapper {

    public static AnfrageDTO mapToDTO(AnfrageEntity entity) {
        return AnfrageDTO.builder()
                .id(entity.getId())
                .kommentar(entity.getKommentar())
                .anfrager(entity.getAnfrager().getId())
                .bedarf(entity.getBedarf().getId())
                .storniert(entity.isStorniert())
                .angenommen(entity.isAngenommen())
                .build();
    }

}
