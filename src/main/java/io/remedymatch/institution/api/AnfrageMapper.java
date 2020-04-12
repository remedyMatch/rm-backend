package io.remedymatch.institution.api;

import io.remedymatch.institution.domain.Anfrage;

class AnfrageMapper {
    static AnfrageDTO mapToDTO(Anfrage anfrage) {
        return AnfrageDTO.builder() //
                .id(anfrage.getId()) //
                .institutionAn(InstitutionMapper.mapToInstitutionRO(anfrage.getInstitutionAn())) //
                .standortAn(InstitutionStandortMapper.mapToStandortRO(anfrage.getStandortAn())) //
                .institutionVon(InstitutionMapper.mapToInstitutionRO(anfrage.getInstitutionVon())) //
                .standortVon(InstitutionStandortMapper.mapToStandortRO(anfrage.getStandortVon())) //
                .angebotId(anfrage.getAngebotId()) //
                .bedarfId(anfrage.getBedarfId()) //
                .anzahl(anfrage.getAnzahl()) //
                .artikelId(anfrage.getArtikelId()) //
                .kommentar(anfrage.getKommentar()) //
                .prozessInstanzId(anfrage.getProzessInstanzId()) //
                .status(anfrage.getStatus()) //
                .entfernung(anfrage.getEntfernung()) //
                .build();
    }
}
