package io.remedymatch.bedarf.api;

import io.remedymatch.bedarf.domain.BedarfEntity;
import io.remedymatch.institution.api.InstitutionMapper;

import static io.remedymatch.artikel.api.ArtikelMapper.getArticleDTO;
import static io.remedymatch.artikel.api.ArtikelMapper.getArticleEntity;

public class BedarfMapper {

    public static BedarfDTO mapToDTO(BedarfEntity entity) {
        var builder = BedarfDTO.builder()
                .id(entity.getId())
                .anzahl(entity.getAnzahl())
                .artikel(getArticleDTO(entity.getArtikel()))
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

    public static BedarfEntity mapToEntity(BedarfDTO dto) {
        var builder = BedarfEntity.builder()
                .id(dto.getId())
                .anzahl(dto.getAnzahl())
                .artikel(getArticleEntity(dto.getArtikel()))
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