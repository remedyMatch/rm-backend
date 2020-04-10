package io.remedymatch.institution.domain;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;

class AnfrageConverter {
    static Anfrage convert(AngebotAnfrage angebotAnfrage) {
        return Anfrage.builder() //
                .id(angebotAnfrage.getId().getValue()) //
                .institutionAn(angebotAnfrage.getAngebot().getInstitution()) //
                .standortAn(angebotAnfrage.getAngebot().getStandort()) //
                .institutionVon(angebotAnfrage.getInstitution()) //
                .standortVon(angebotAnfrage.getStandort()) //
                .angebotId(angebotAnfrage.getAngebot().getId().getValue()) //
                .artikelId(angebotAnfrage.getAngebot().getArtikelVariante().getArtikelId().getValue()) //
                .artikelVarianteId(angebotAnfrage.getAngebot().getArtikelVariante().getId().getValue()) //
                .anzahl(angebotAnfrage.getAnzahl()) //
                .kommentar(angebotAnfrage.getKommentar()) //
                .prozessInstanzId(angebotAnfrage.getProzessInstanzId()) //
                .status(angebotAnfrage.getStatus().toString()) //
                .build();
    }

    static Anfrage convert(BedarfAnfrage bedarfAnfrage) {
        return Anfrage.builder() //
                .id(bedarfAnfrage.getId().getValue()) //
                .institutionAn(bedarfAnfrage.getBedarf().getInstitution()) //
                .standortAn(bedarfAnfrage.getBedarf().getStandort()) //
                .institutionVon(bedarfAnfrage.getInstitution()) //
                .standortVon(bedarfAnfrage.getStandort()) //
                .bedarfId(bedarfAnfrage.getBedarf().getId().getValue()) //
                .anzahl(bedarfAnfrage.getAnzahl()) //
                .artikelId(bedarfAnfrage.getBedarf().getArtikel().getId().getValue()) //
                .artikelVarianteId(bedarfAnfrage.getBedarf().getArtikelVariante().getId().getValue()) //
                .kommentar(bedarfAnfrage.getKommentar()) //
                .prozessInstanzId(bedarfAnfrage.getProzessInstanzId()) //
                .status(bedarfAnfrage.getStatus().toString()) //
                .build();
    }
}
