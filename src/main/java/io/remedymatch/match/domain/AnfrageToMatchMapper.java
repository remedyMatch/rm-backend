package io.remedymatch.match.domain;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;

public class AnfrageToMatchMapper {

    static Match map(AngebotAnfrage anfrage) {
        return Match.builder()
                .anzahl(anfrage.getAnzahl())
                .anfrageTyp("ANGEBOT")
                .anfrageId(anfrage.getId().getValue())
                .artikel(anfrage.getAngebot().getArtikel())
                .artikelVarianteId(anfrage.getAngebot().getArtikelVariante().getId().getValue())
                .institutionAn(anfrage.getInstitution())
                .institutionVon(anfrage.getAngebot().getInstitution())
                .standortAn(anfrage.getStandort())
                .standortVon(anfrage.getAngebot().getStandort())
                .build();
    }

    static Match map(BedarfAnfrage anfrage) {
        return Match.builder()
                .anzahl(anfrage.getAnzahl())
                .anfrageTyp("BEDARF")
                .anfrageId(anfrage.getId().getValue())
                .artikel(anfrage.getBedarf().getArtikel())
                .artikelVarianteId(anfrage.getBedarf().getArtikelVariante() != null ? anfrage.getBedarf().getArtikelVariante().getId().getValue() : null)
                .institutionAn(anfrage.getBedarf().getInstitution())
                .institutionVon(anfrage.getInstitution())
                .standortAn(anfrage.getBedarf().getStandort())
                .standortVon(anfrage.getStandort())
                .build();
    }

}
