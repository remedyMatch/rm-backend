package io.remedymatch.angebot.controller;

import io.remedymatch.angebot.domain.model.*;
import io.remedymatch.artikel.controller.ArtikelControllerMapper;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.institution.controller.InstitutionMapper;
import io.remedymatch.institution.controller.InstitutionStandortMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

class AngebotControllerMapper {
    private AngebotControllerMapper() {

    }

    static GestellteAngebotAnfrageRO mapToGestellteAngebotAnfrageRO(final AngebotAnfrage angebotAnfrage) {
        return GestellteAngebotAnfrageRO.builder() //
                .id(angebotAnfrage.getId().getValue()) //
                .angebot(mapToAngebotRO(angebotAnfrage.getAngebot()))
                .institution(InstitutionMapper.mapToInstitutionRO(angebotAnfrage.getInstitution())) //
                .standort(InstitutionStandortMapper.mapToStandortRO(angebotAnfrage.getStandort())) //
                .anzahl(angebotAnfrage.getAnzahl()) //
                .kommentar(angebotAnfrage.getKommentar()) //
                .entfernung(angebotAnfrage.getEntfernung())
                .status(angebotAnfrage.getStatus()) //
                .bedarfId(angebotAnfrage.getBedarfId().getValue()) //
                .build();
    }

    static AngebotAnfrageRO mapToAnfrageRO(final AngebotAnfrage angebotAnfrage) {
        return AngebotAnfrageRO.builder() //
                .id(angebotAnfrage.getId().getValue()) //
                .institution(InstitutionMapper.mapToInstitutionRO(angebotAnfrage.getInstitution())) //
                .standort(InstitutionStandortMapper.mapToStandortRO(angebotAnfrage.getStandort())) //
                .anzahl(angebotAnfrage.getAnzahl()) //
                .kommentar(angebotAnfrage.getKommentar()) //
                .entfernung(angebotAnfrage.getEntfernung())
                .status(angebotAnfrage.getStatus()) //
                .bedarfId(angebotAnfrage.getBedarfId().getValue()) //
                .build();
    }

    static List<AngebotRO> mapToAngeboteRO(final List<Angebot> angebote) {
        return angebote.stream().map(AngebotControllerMapper::mapToAngebotRO).collect(Collectors.toList());
    }

    static AngebotRO mapToAngebotRO(final Angebot angebot) {
        return AngebotRO.builder() //
                .id(angebot.getId().getValue()) //
                .artikel(ArtikelControllerMapper.mapArtikelToRO(angebot.getArtikel())) //
                .artikelVarianteId(angebot.getArtikelVariante().getId().getValue()) //
                .verfuegbareAnzahl(angebot.getRest()) //
                .ort(angebot.getStandort().getOrt())
                .haltbarkeit(angebot.getHaltbarkeit()).medizinisch(angebot.isMedizinisch()).steril(angebot.isSteril()) //
                .originalverpackt(angebot.isOriginalverpackt()) //
                .medizinisch(angebot.isMedizinisch()) //
                .kommentar(angebot.getKommentar()) //
                .entfernung(angebot.getEntfernung()) //
                .oeffentlich(angebot.isOeffentlich()) //
                .build();
    }

    static List<InstitutionAngebotRO> mapToInstitutionAngebotRO(final List<Angebot> angebote) {
        return angebote.stream().map(AngebotControllerMapper::mapToInstitutionAngebotRO).collect(Collectors.toList());
    }

    static InstitutionAngebotRO mapToInstitutionAngebotRO(final Angebot angebot) {
        return InstitutionAngebotRO.builder() //
                .id(angebot.getId().getValue()) //
                .artikel(ArtikelControllerMapper.mapArtikelToRO(angebot.getArtikel())) //
                .artikelVarianteId(angebot.getArtikelVariante().getId().getValue()) //
                .verfuegbareAnzahl(angebot.getAnzahl()) //
                .anfragen(angebot.getAnfragen().stream().map(AngebotControllerMapper::mapToAnfrageRO).collect(Collectors.toList()))
                .ort(angebot.getStandort().getOrt())
                .haltbarkeit(angebot.getHaltbarkeit()).medizinisch(angebot.isMedizinisch()).steril(angebot.isSteril()) //
                .originalverpackt(angebot.isOriginalverpackt()) //
                .medizinisch(angebot.isMedizinisch()) //
                .kommentar(angebot.getKommentar()) //
                .entfernung(angebot.getEntfernung()) //
                .oeffentlich(angebot.isOeffentlich()) //
                .build();
    }

    static List<AngebotFilterEntryRO> mapToFilterEntriesRO(final List<AngebotFilterEntry> filterEntries) {
        return filterEntries.stream().map(AngebotControllerMapper::mapToFilterEntryRO).collect(Collectors.toList());
    }

    static AngebotFilterEntryRO mapToFilterEntryRO(final AngebotFilterEntry filterEntry) {
        return AngebotFilterEntryRO.builder() //
                .id(filterEntry.getId()) //
                .anzahl(filterEntry.getAnzahl()) //
                .build();
    }

    static NeuesAngebot mapToNeueAngebot(final NeuesAngebotRequest neueAngebotRequest) {
        return NeuesAngebot.builder()//
                .artikelVarianteId(new ArtikelVarianteId(neueAngebotRequest.getArtikelVarianteId())) //
                .anzahl(neueAngebotRequest.getAnzahl()) //
                .haltbarkeit(neueAngebotRequest.getHaltbarkeit()) //
                .steril(neueAngebotRequest.isSteril()) //
                .originalverpackt(neueAngebotRequest.isOriginalverpackt()) //
                .medizinisch(neueAngebotRequest.isMedizinisch()) //
                .kommentar(neueAngebotRequest.getKommentar()) //
                .oeffentlich(neueAngebotRequest.isOeffentlich()) //
                .build();
    }

    static AngebotId mapToAngebotId(final UUID angebotId) {
        return new AngebotId(angebotId);
    }
}