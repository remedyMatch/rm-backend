package io.remedymatch.bedarf.api;

import io.remedymatch.bedarf.domain.BedarfEntity;
import io.remedymatch.institution.api.InstitutionStandortMapper;

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
                .standort(InstitutionStandortMapper.mapToDTO(entity.getStandort()))
                .steril(entity.isSteril())
                .rest(entity.getRest())
                .kommentar(entity.getKommentar())
                .bedient(entity.isBedient());

        if (entity.getInstitution() != null) {
            builder = builder.institutionId(entity.getInstitution().getId());
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
                .standort(InstitutionStandortMapper.mapToEntity(dto.getStandort()))
                .steril(dto.isSteril())
                .kommentar(dto.getKommentar())
                .bedient(dto.isBedient());
        return builder.build();
    }
}