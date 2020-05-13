package io.remedymatch.match.api;

import io.remedymatch.institution.controller.InstitutionMapper;
import io.remedymatch.match.domain.Match;

class MatchMapper {
    static MatchRO mapToDTO(Match match) {
        if (match == null) {
            return null;
        }

        return MatchRO.builder() //
                .id(match.getId().getValue()) //
                .anfrageId(match.getAnfrageId()) //
                .status(match.getStatus()) //
                .standortAn(MatchStandortMapper.mapToDTO(match.getStandortAn())) //
                .standortVon(MatchStandortMapper.mapToDTO(match.getStandortVon())) //
                .institutionVon(InstitutionMapper.mapToInstitutionRO(match.getInstitutionVon())) //
                .institutionAn(InstitutionMapper.mapToInstitutionRO(match.getInstitutionAn())) //
                .entfernung(match.getEntfernung()) //
                .anfrageTyp(match.getAnfrageTyp()).artikelId(match.getArtikelId())
                .artikelVarianteId(match.getArtikelVarianteId()).anzahl(match.getAnzahl()).build();
    }
}
