package io.remedymatch.institution.api;

import io.remedymatch.angebot.domain.anfrage.AngebotAnfrageEntity;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageEntity;

public class AnfrageMapper {

    public static AnfrageDTO mapToDTO(BedarfAnfrageEntity entity) {
        return AnfrageDTO.builder()
                .id(entity.getId())
                .kommentar(entity.getKommentar())
                .institutionAn(InstitutionMapper.mapToDTO(entity.getInstitutionAn()))
                .institutionVon(InstitutionMapper.mapToDTO(entity.getInstitutionVon()))
                .bedarfId(entity.getBedarf() != null ? entity.getBedarf().getId() : null)
                .standortAn(InstitutionStandortMapper.mapToDTO(entity.getStandortAn()))
                .standortVon(InstitutionStandortMapper.mapToDTO(entity.getStandortVon()))
                .status(entity.getStatus().toString())
                .anzahl(entity.getAnzahl())
                .prozessInstanzId(entity.getProzessInstanzId())
                .build();
    }

    public static AnfrageDTO mapToDTO(AngebotAnfrageEntity entity) {
        return AnfrageDTO.builder()
                .id(entity.getId())
                .kommentar(entity.getKommentar())
                .institutionAn(InstitutionMapper.mapToDTO(entity.getInstitutionAn()))
                .institutionVon(InstitutionMapper.mapToDTO(entity.getInstitutionVon()))
                .bedarfId(entity.getAngebot() != null ? entity.getAngebot().getId() : null)
                .standortAn(InstitutionStandortMapper.mapToDTO(entity.getStandortAn()))
                .standortVon(InstitutionStandortMapper.mapToDTO(entity.getStandortVon()))
                .status(entity.getStatus().toString())
                .anzahl(entity.getAnzahl())
                .prozessInstanzId(entity.getProzessInstanzId())
                .build();
    }

}
