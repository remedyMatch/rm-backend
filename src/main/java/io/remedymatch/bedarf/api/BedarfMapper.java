package io.remedymatch.bedarf.api;

import io.remedymatch.bedarf.domain.BedarfEntity;

import static io.remedymatch.artikel.api.ArtikelMapper.getArticleDTO;
import static io.remedymatch.artikel.api.ArtikelMapper.getArticleEntity;

public class BedarfMapper {

    public static BedarfDTO mapToDTO(BedarfEntity entity) {
        return BedarfDTO.builder()
                .id(entity.getId())
                .anzahl(entity.getAnzahl())
                .artikel(getArticleDTO(entity.getArtikel()))
                .build();
    }

    public static BedarfEntity mapToEntity(BedarfDTO dto) {
        return BedarfEntity.builder()
                .id(dto.getId())
                .anzahl(dto.getAnzahl())
                .artikel(getArticleEntity(dto.getArtikel()))
                .build();
    }
}