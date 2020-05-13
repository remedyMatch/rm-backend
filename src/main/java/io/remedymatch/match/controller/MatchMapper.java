package io.remedymatch.match.controller;

import io.remedymatch.artikel.controller.ArtikelControllerMapper;
import io.remedymatch.institution.controller.InstitutionMapper;
import io.remedymatch.institution.controller.InstitutionStandortMapper;
import io.remedymatch.match.domain.Match;

import java.util.List;
import java.util.stream.Collectors;

class MatchMapper {

    static MatchRO map(Match match) {
        if (match == null) {
            return null;
        }
        return MatchRO.builder()
                .anfrageId(match.getAnfrageId())
                .standortAn(InstitutionStandortMapper.mapToStandortRO(match.getStandortAn()))
                .standortVon(InstitutionStandortMapper.mapToStandortRO(match.getStandortVon()))
                .institutionVon(InstitutionMapper.mapToInstitutionRO(match.getInstitutionVon()))
                .institutionAn(InstitutionMapper.mapToInstitutionRO(match.getInstitutionAn()))
                .entfernung(match.getEntfernung())
                .anfrageTyp(match.getAnfrageTyp())
                .artikel(ArtikelControllerMapper.mapArtikelToRO(match.getArtikel()))
                .artikelVarianteId(match.getArtikelVarianteId())
                .anzahl(match.getAnzahl())
                .inseratId(match.getInseratId())
                .build();
    }

    static List<MatchRO> map(List<Match> matches) {
        return matches.stream().map(MatchMapper::map).collect(Collectors.toList());
    }
}
