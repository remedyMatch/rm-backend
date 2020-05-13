package io.remedymatch.match.controller;

import io.remedymatch.institution.controller.InstitutionMapper;
import io.remedymatch.institution.controller.InstitutionStandortMapper;
import io.remedymatch.match.domain.Match;

class MatchMapper {
    static MatchRO mapToDTO(Match match) {
        if (match == null) {
            return null;
        }

        return MatchRO.builder() //
                .anfrageId(match.getAnfrageId()) //
                .standortAn(InstitutionStandortMapper.mapToStandortRO(match.getStandortAn())) //
                .standortVon(InstitutionStandortMapper.mapToStandortRO(match.getStandortVon())) //
                .institutionVon(InstitutionMapper.mapToInstitutionRO(match.getInstitutionVon())) //
                .institutionAn(InstitutionMapper.mapToInstitutionRO(match.getInstitutionAn())) //
                .entfernung(match.getEntfernung()) //
                .anfrageTyp(match.getAnfrageTyp()).artikelId(match.getArtikel().getId().getValue())
                .artikelVarianteId(match.getArtikelVarianteId()).anzahl(match.getAnzahl()).build();
    }
}
