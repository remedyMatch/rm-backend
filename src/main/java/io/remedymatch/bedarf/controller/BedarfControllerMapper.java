package io.remedymatch.bedarf.controller;

import io.remedymatch.artikel.controller.ArtikelControllerMapper;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.bedarf.domain.model.*;
import io.remedymatch.institution.controller.InstitutionMapper;
import io.remedymatch.institution.controller.InstitutionStandortMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

class BedarfControllerMapper {
    private BedarfControllerMapper() {

    }

    static GestellteBedarfAnfrageRO mapToGestellteBedarfAnfrageRO(final BedarfAnfrage bedarfAnfrage) {
        return GestellteBedarfAnfrageRO.builder() //
                .id(bedarfAnfrage.getId().getValue()) //
                .bedarf(mapToBedarfRO(bedarfAnfrage.getBedarf())) //
                .institution(InstitutionMapper.mapToInstitutionRO(bedarfAnfrage.getInstitution())) //
                .standort(InstitutionStandortMapper.mapToStandortRO(bedarfAnfrage.getStandort())) //
                .anzahl(bedarfAnfrage.getAnzahl()) //
                .angebotId(bedarfAnfrage.getAngebotId().getValue()) //
                .kommentar(bedarfAnfrage.getKommentar()) //
                .entfernung(bedarfAnfrage.getEntfernung()) //
                .status(bedarfAnfrage.getStatus()) //
                .build();
    }

    static BedarfAnfrageRO mapToAnfrageRO(final BedarfAnfrage bedarfAnfrage) {
        return BedarfAnfrageRO.builder() //
                .id(bedarfAnfrage.getId().getValue()) //
                .institution(InstitutionMapper.mapToInstitutionRO(bedarfAnfrage.getInstitution())) //
                .standort(InstitutionStandortMapper.mapToStandortRO(bedarfAnfrage.getStandort())) //
                .anzahl(bedarfAnfrage.getAnzahl()) //
                .angebotId(bedarfAnfrage.getAngebotId().getValue()) //
                .kommentar(bedarfAnfrage.getKommentar()) //
                .entfernung(bedarfAnfrage.getEntfernung()) //
                .status(bedarfAnfrage.getStatus()) //
                .build();
    }

    static List<BedarfRO> mapToBedarfeRO(final List<Bedarf> bedarfe) {
        return bedarfe.stream().map(BedarfControllerMapper::mapToBedarfRO).collect(Collectors.toList());
    }

    static BedarfRO mapToBedarfRO(final Bedarf bedarf) {
        return BedarfRO.builder() //
                .id(bedarf.getId().getValue()) //
                .artikel(ArtikelControllerMapper.mapArtikelToRO(bedarf.getArtikel())) //
                .artikelVarianteId(
                        bedarf.getArtikelVariante() != null ? bedarf.getArtikelVariante().getId().getValue() : null) //
                .verfuegbareAnzahl(bedarf.getRest()) //
                .oeffentlich(bedarf.isOeffentlich()) //
                .institutionId(bedarf.getInstitution().getId().getValue()) //
                .standort(InstitutionStandortMapper.mapToStandortRO(bedarf.getStandort())) //
                .steril(bedarf.isSteril()) //
                .medizinisch(bedarf.isMedizinisch()) //
                .kommentar(bedarf.getKommentar()) //
                .entfernung(bedarf.getEntfernung()) //
                .build();
    }

    static List<InstitutionBedarfRO> mapToInstitutionBedarfeRO(final List<Bedarf> bedarfe) {
        return bedarfe.stream().map(BedarfControllerMapper::mapToInstitutionBedarfRO).collect(Collectors.toList());
    }

    static InstitutionBedarfRO mapToInstitutionBedarfRO(final Bedarf bedarf) {
        return InstitutionBedarfRO.builder() //
                .id(bedarf.getId().getValue()) //
                .artikel(ArtikelControllerMapper.mapArtikelToRO(bedarf.getArtikel())) //
                .artikelVarianteId(
                        bedarf.getArtikelVariante() != null ? bedarf.getArtikelVariante().getId().getValue() : null) //
                .verfuegbareAnzahl(bedarf.getRest()) //
                .oeffentlich(bedarf.isOeffentlich()) //
                .institutionId(bedarf.getInstitution().getId().getValue()) //
                .anfragen(bedarf.getAnfragen().stream().map(BedarfControllerMapper::mapToAnfrageRO).collect(Collectors.toList()))
                .standort(InstitutionStandortMapper.mapToStandortRO(bedarf.getStandort())) //
                .steril(bedarf.isSteril()) //
                .medizinisch(bedarf.isMedizinisch()) //
                .kommentar(bedarf.getKommentar()) //
                .entfernung(bedarf.getEntfernung()) //
                .build();
    }

    static List<BedarfFilterEntryRO> mapToFilterEntriesRO(final List<BedarfFilterEntry> filterEntries) {
        return filterEntries.stream().map(BedarfControllerMapper::mapToFilterEntryRO).collect(Collectors.toList());
    }

    static BedarfFilterEntryRO mapToFilterEntryRO(final BedarfFilterEntry filterEntry) {
        return BedarfFilterEntryRO.builder() //
                .id(filterEntry.getId()) //
                .anzahl(filterEntry.getAnzahl()) //
                .build();
    }

    static NeuerBedarf mapToNeuesBedarf(final NeuerBedarfRequest neuesBedarfRequest) {
        return NeuerBedarf.builder() //
                .artikelId(neuesBedarfRequest.getArtikelId() != null ? new ArtikelId(neuesBedarfRequest.getArtikelId())
                        : null) //
                .artikelVarianteId(neuesBedarfRequest.getArtikelVarianteId() != null
                        ? new ArtikelVarianteId(neuesBedarfRequest.getArtikelVarianteId())
                        : null) //
                .anzahl(neuesBedarfRequest.getAnzahl()) //
                .steril(neuesBedarfRequest.isSteril()) //
                .medizinisch(neuesBedarfRequest.isMedizinisch()) //
                .kommentar(neuesBedarfRequest.getKommentar()) //
                .oeffentlich(neuesBedarfRequest.isOeffentlich()) //
                .build();
    }

    static BedarfId mapToBedarfId(final UUID bedarfId) {
        return new BedarfId(bedarfId);
    }
}