package io.remedymatch.angebot.api;

import io.remedymatch.angebot.domain.AngebotEntity;
import io.remedymatch.institution.api.InstitutionMapper;

import static io.remedymatch.artikel.api.ArtikelMapper.getArticleDTO;
import static io.remedymatch.artikel.api.ArtikelMapper.getArticleEntity;

public class AngebotMapper {

    public static AngebotDTO mapToDTO(AngebotEntity entity) {
        var builder = AngebotDTO.builder()
                .id(entity.getId())
                .anzahl(entity.getAnzahl())
                .artikel(getArticleDTO(entity.getArtikel()))
                .haltbarkeit(entity.getHaltbarkeit())
                .medizinisch(entity.isMedizinisch())
                .originalverpackt(entity.isOriginalverpackt())
                .standort(entity.getStandort())
                .steril(entity.isSteril())
                .kommentar(entity.getKommentar());

        if (entity.getInstitution() != null) {
            builder = builder.institution(InstitutionMapper.mapToDTO(entity.getInstitution()));
        }

        return builder.build();

    }

    public static AngebotEntity mapToEntity(AngebotDTO dto) {
        var builder = AngebotEntity.builder()
                .id(dto.getId())
                .anzahl(dto.getAnzahl())
                .artikel(getArticleEntity(dto.getArtikel()))
                .haltbarkeit(dto.getHaltbarkeit())
                .medizinisch(dto.isMedizinisch())
                .originalverpackt(dto.isOriginalverpackt())
                .standort(dto.getStandort())
                .steril(dto.isSteril())
                .kommentar(dto.getKommentar());

        if (dto.getInstitution() != null) {
            builder = builder.institution(InstitutionMapper.mapToEntity(dto.getInstitution()));
        }

        return builder.build();
    }
}