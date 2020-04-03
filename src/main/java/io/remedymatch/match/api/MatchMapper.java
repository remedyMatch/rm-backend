package io.remedymatch.match.api;

import io.remedymatch.institution.api.InstitutionMapper;
import io.remedymatch.match.domain.Match;

class MatchMapper {
    static MatchDTO mapToDTO(Match match) {
        if (match == null) {
            return null;
        }

        return MatchDTO.builder() //
                .id(match.getId().getValue()) //
                .anfrageId(match.getAnfrageId()) //
                .status(match.getStatus()) //
                .standortAn(MatchStandortMapper.mapToDTO(match.getStandortAn())) //
                .standortVon(MatchStandortMapper.mapToDTO(match.getStandortVon())) //
                .institutionVon(InstitutionMapper.mapToDTO(match.getInstitutionVon())) //
                .institutionAn(InstitutionMapper.mapToDTO(match.getInstitutionAn())) //
                .kommentar(match.getKommentar()) //
                .prozessInstanzId(match.getProzessInstanzId()) //
                .entfernung(match.getEntfernung()) //
                .anfrageTyp(match.getAnfrageTyp())
                .artikelId(match.getAritkelId())
                .anzahl(match.getAnzahl())
                .build();
    }
}
