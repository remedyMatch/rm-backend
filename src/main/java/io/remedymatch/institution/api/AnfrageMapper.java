package io.remedymatch.institution.api;

import java.math.BigDecimal;

import io.remedymatch.angebot.domain.AngebotAnfrage;
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
                .anzahl(BigDecimal.valueOf(entity.getAnzahl()))
                .prozessInstanzId(entity.getProzessInstanzId())
                .build();
    }

    public static AnfrageDTO mapToDTO(AngebotAnfrage angebotAnfrage) {
        return AnfrageDTO.builder()
                .id(angebotAnfrage.getId().getValue())
                .kommentar(angebotAnfrage.getKommentar())
                .institutionAn(InstitutionMapper.mapToDTO(angebotAnfrage.getInstitutionAn()))
                .institutionVon(InstitutionMapper.mapToDTO(angebotAnfrage.getInstitutionVon()))
                .bedarfId(angebotAnfrage.getAngebot() != null ? angebotAnfrage.getAngebot().getId().getValue() : null)
                .standortAn(InstitutionStandortMapper.mapToDTO(angebotAnfrage.getStandortAn()))
                .standortVon(InstitutionStandortMapper.mapToDTO(angebotAnfrage.getStandortVon()))
                .status(angebotAnfrage.getStatus().toString())
                .anzahl(angebotAnfrage.getAnzahl())
                .prozessInstanzId(angebotAnfrage.getProzessInstanzId())
                .build();
    }

}
