package io.remedymatch.angebot.api;

import io.remedymatch.angebot.domain.AngebotEntity;

import static io.remedymatch.artikel.api.ArtikelMapper.getArticleDTO;
import static io.remedymatch.artikel.api.ArtikelMapper.getArticleEntity;

public class AngebotMapper {

    public static AngebotDTO mapToDTO(AngebotEntity entity) {
        return AngebotDTO.builder()
                .id(entity.getId())
                .anzahl(entity.getAnzahl())
                .artikel(getArticleDTO(entity.getArtikel()))
                .gueltigkeit(entity.getGueltigkeit())
                .medizinisch(entity.isMedizinisch())
                .originalverpackt(entity.isOriginalverpackt())
                .standort(entity.getStandort())
                .steril(entity.isSteril())
                .build();
    }

    public static AngebotEntity mapToEntity(AngebotDTO dto) {
        return AngebotEntity.builder()
                .id(dto.getId())
                .anzahl(dto.getAnzahl())
                .artikel(getArticleEntity(dto.getArtikel()))
                .gueltigkeit(dto.getGueltigkeit())
                .medizinisch(dto.isMedizinisch())
                .originalverpackt(dto.isOriginalverpackt())
                .standort(dto.getStandort())
                .steril(dto.isSteril())
                .build();
    }
}