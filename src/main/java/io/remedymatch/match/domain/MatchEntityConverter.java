package io.remedymatch.match.domain;

import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.match.infrastructure.MatchEntity;
import io.remedymatch.match.infrastructure.MatchEntity.MatchEntityBuilder;

class MatchEntityConverter {

    static Match convert(final MatchEntity entity) {
        if (entity == null) {
            return null;
        }

        return Match.builder() //
                .id(new MatchId(entity.getId())) //
                .anfrageId(entity.getAnfrageId()) //
                .institutionVon(InstitutionEntityConverter.convert(entity.getInstitutionVon())) //
                .standortVon(MatchStandortEntityConverter.convert(entity.getStandortVon())) //
                .institutionAn(InstitutionEntityConverter.convert(entity.getInstitutionAn())) //
                .standortAn(MatchStandortEntityConverter.convert(entity.getStandortAn())) //
                .kommentar(entity.getKommentar()) //
                .prozessInstanzId(entity.getProzessInstanzId()) //
                .status(entity.getStatus())//
                .anfrageTyp(entity.getAnfrageTyp())
                .anzahl(entity.getAnzahl())
                .artikelId(entity.getArtikelId())
                .artikelVarianteId(entity.getArtikelVarianteId())
                .build();
    }

    static MatchEntity convert(final Match match) {
        if (match == null) {
            return null;
        }

        MatchEntityBuilder builder = MatchEntity.builder();
        if (match.getId() != null) {
            builder.id(match.getId().getValue());
        }

        return builder //
                .anfrageId(match.getAnfrageId()) //
                .institutionVon(InstitutionEntityConverter.convert(match.getInstitutionVon())) //
                .standortVon(MatchStandortEntityConverter.convert(match.getStandortVon())) //
                .institutionAn(InstitutionEntityConverter.convert(match.getInstitutionAn())) //
                .standortAn(MatchStandortEntityConverter.convert(match.getStandortAn())) //
                .kommentar(match.getKommentar()) //
                .prozessInstanzId(match.getProzessInstanzId()) //
                .status(match.getStatus())//
                .anfrageTyp(match.getAnfrageTyp())
                .anzahl(match.getAnzahl())
                .artikelId(match.getArtikelId())
                .artikelVarianteId(match.getArtikelVarianteId())
                .build();
    }
}