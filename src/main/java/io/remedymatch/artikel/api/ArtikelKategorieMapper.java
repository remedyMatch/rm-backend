package io.remedymatch.artikel.api;

import io.remedymatch.artikel.domain.ArtikelKategorieEntity;

public class ArtikelKategorieMapper {
    public static ArtikelKategorieEntity getArtikelKategorieEntity(ArtikelKategorieDTO dto ) {
        return ArtikelKategorieEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public static ArtikelKategorieDTO getArtikelKategorieDTO(ArtikelKategorieEntity entity ) {
        return ArtikelKategorieDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
