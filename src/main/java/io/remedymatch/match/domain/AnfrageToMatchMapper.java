package io.remedymatch.match.domain;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;

import java.util.List;
import java.util.stream.Collectors;

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
                .entfernung(anfrage.getEntfernung())
                .inseratId(anfrage.getAngebot().getId().getValue())
                .build();
    }

    static List<Match> mapAngebotAnfragen(List<AngebotAnfrage> anfragen) {
        return anfragen.stream().map(AnfrageToMatchMapper::map).collect(Collectors.toList());
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
                .entfernung(anfrage.getEntfernung())
                .inseratId(anfrage.getBedarf().getId().getValue())
                .build();
    }

    static List<Match> mapBedarfAnfragen(List<BedarfAnfrage> anfragen) {
        return anfragen.stream().map(AnfrageToMatchMapper::map).collect(Collectors.toList());
    }

}
