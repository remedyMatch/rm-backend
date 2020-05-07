package io.remedymatch.bedarf.controller;

import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.artikel.controller.ArtikelRO;
import io.remedymatch.artikel.domain.model.*;
import io.remedymatch.bedarf.domain.model.*;
import io.remedymatch.institution.controller.InstitutionRO;
import io.remedymatch.institution.controller.InstitutionStandortRO;
import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DisplayName("BedarfMapper soll")
class BedarfControllerMapperShould {

    private static final BedarfId BEDARF_ID = new BedarfId(UUID.randomUUID());
    private static final AngebotId ANGEBOT_ID = new AngebotId(UUID.randomUUID());
    private static final BigDecimal BEDARF_ANZAHL = BigDecimal.valueOf(120.0);
    private static final BigDecimal BEDARF_REST = BigDecimal.valueOf(120.0);
    private static final ArtikelId BEDARF_ARTIKEL_ID = new ArtikelId(UUID.randomUUID());
    private static final ArtikelKategorieId BEDARF_ARTIKEL_KATEGORIE_ID = new ArtikelKategorieId(UUID.randomUUID());
    private static final Artikel BEDARF_ARTIKEL = Artikel.builder().id(BEDARF_ARTIKEL_ID).artikelKategorieId(BEDARF_ARTIKEL_KATEGORIE_ID).build();
    private static final ArtikelRO BEDARF_ARTIKEL_RO = ArtikelRO.builder().id(BEDARF_ARTIKEL_ID.getValue()).artikelKategorieId(BEDARF_ARTIKEL_KATEGORIE_ID.getValue()).build();
    private static final ArtikelVarianteId ARTIKEL_VARIANTE_ID = new ArtikelVarianteId(UUID.randomUUID());
    private static final ArtikelVariante ARTIKEL_VARIANTE = ArtikelVariante.builder().id(ARTIKEL_VARIANTE_ID).build();
    private static final InstitutionId BEDARF_INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
    private static final Institution BEDARF_INSTITUTION = Institution.builder().id(BEDARF_INSTITUTION_ID).build();
    private static final InstitutionStandortId BEDARF_STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
    private static final InstitutionStandortRO BEDARF_STANDORT_DTO = InstitutionStandortRO.builder()
            .id(BEDARF_STANDORT_ID.getValue()).build();
    private static final InstitutionStandort BEDARF_STANDORT = InstitutionStandort.builder().id(BEDARF_STANDORT_ID)
            .build();
    private static final boolean STERIL = true;
    private static final boolean MEDIZINISCH = true;
    private static final String BEDARF_KOMMENTAR = "Bedarf Kommentar";
    private static final boolean BEDIENT = false;

    private static final BedarfAnfrageId ANFRAGE_ID = new BedarfAnfrageId(UUID.randomUUID());
    private static final InstitutionId ANFRAGE_INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
    private static final Institution ANFRAGE_INSTITUTION = Institution.builder().id(ANFRAGE_INSTITUTION_ID).build();
    private static final InstitutionRO ANFRAGE_INSTITUTION_DTO = InstitutionRO.builder()
            .id(ANFRAGE_INSTITUTION_ID.getValue()).build();
    private static final InstitutionStandortId ANFRAGE_STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
    private static final InstitutionStandortRO ANFRAGE_STANDORT_DTO = InstitutionStandortRO.builder()
            .id(ANFRAGE_STANDORT_ID.getValue()).build();
    private static final InstitutionStandort ANFRAGE_STANDORT = InstitutionStandort.builder().id(ANFRAGE_STANDORT_ID)
            .build();
    private static final BigDecimal ANFRAGE_ANZAHL = BigDecimal.valueOf(98741.0);
    private static final String ANFRAGE_KOMMENTAR = "Anfrage Kommentar";
    private static final String ANFRAGE_PROZESS_INSTANZ_ID = "Anfrage ProzessInstanzId";
    private static final BedarfAnfrageStatus ANFRAGE_STATUS = BedarfAnfrageStatus.OFFEN;

    private static final UUID FILTER_ENTRY_ID = UUID.randomUUID();
    private static final BigInteger FILTER_ENTRY_ANZAHL = BigInteger.valueOf(123);

    @Test
    @DisplayName("eine leere Liste der Domain Objekte in leere Liste der ROs konvertieren")
    void eine_leere_Liste_der_Domain_Objekte_in_leere_Liste_der_ROs_konvertieren() {
        assertEquals(Collections.emptyList(), BedarfControllerMapper.mapToBedarfeRO(Collections.emptyList()));
    }

    @Test
    @DisplayName("eine Liste der Domain Objekte in Liste der ROs konvertieren")
    void eine_Liste_der_Domain_Objekte_in_Liste_der_ROs_konvertieren() {
        assertEquals(Arrays.asList(bedarfRO()), BedarfControllerMapper.mapToBedarfeRO(Arrays.asList(bedarf())));
    }

    @Test
    @DisplayName("Bedarf Domain Objekt mit ArtikelVariante in RO konvertieren")
    void bedarf_domain_Objekt_mit_ArtikelVariante_in_RO_konvertieren() {
        assertEquals(bedarfRO(), BedarfControllerMapper.mapToBedarfRO(bedarf()));
    }

    @Test
    @DisplayName("Bedarf Domain Objekt ohne ArtikelVariante in RO konvertieren")
    void bedarf_domain_Objekt_ohne_ArtikelVariante_in_RO_konvertieren() {
        val bedarfOhneArtikelVariante = bedarf();
        bedarfOhneArtikelVariante.setArtikelVariante(null);

        BedarfRO bedarfROOhneArtikelVariante = bedarfRO();
        bedarfROOhneArtikelVariante.setArtikelVarianteId(null);

        assertEquals(bedarfROOhneArtikelVariante, BedarfControllerMapper.mapToBedarfRO(bedarfOhneArtikelVariante));
    }

    @Test
    @DisplayName("eine leere Liste der FilterEntries in leere Liste der ROs konvertieren")
    void eine_leere_Liste_der_FilterEntries_in_leere_Liste_der_ROs_konvertieren() {
        assertEquals(Collections.emptyList(), BedarfControllerMapper.mapToFilterEntriesRO(Collections.emptyList()));
    }

    @Test
    @DisplayName("eine Liste der FilterEntries in Liste der ROs konvertieren")
    void eine_Liste_der_FilterEntries_in_Liste_der_ROs_konvertieren() {
        assertEquals(Arrays.asList(bedarfFilterEntryRO()), BedarfControllerMapper.mapToFilterEntriesRO(Arrays.asList(bedarfFilterEntry())));
    }

    @Test
    @DisplayName("FilterEntry in RO konvertieren")
    void filterEntry_in_RO_konvertieren() {
        assertEquals(bedarfFilterEntryRO(), BedarfControllerMapper.mapToFilterEntryRO(bedarfFilterEntry()));
    }

    @Test
    @DisplayName("NeuesBedarf konvertieren koennen")
    void neuesBedarf_konvertieren_koennen() {
        assertEquals(neuesBedarf(), BedarfControllerMapper.mapToNeuesBedarf(neuesBedarfRequest()));
    }

    @Test
    @DisplayName("UUID in BedarfId konvertieren")
    void uuid_in_BedarfId_konvertieren() {
        assertEquals(BEDARF_ID, BedarfControllerMapper.mapToBedarfId(BEDARF_ID.getValue()));
    }

    @Test
    @DisplayName("BedarfAnfrage Domain Objekt in RO konvertieren")
    void bedarf_Anfrage_domain_Objekt_in_RO_konvertieren() {
        assertEquals(anfrageRO(), BedarfControllerMapper.mapToAnfrageRO(anfrage()));
    }

    /* help methods */

    private NeuerBedarf neuesBedarf() {
        return NeuerBedarf.builder() //
                .anzahl(BEDARF_ANZAHL) //
                .artikelId(BEDARF_ARTIKEL_ID) //
                .artikelVarianteId(ARTIKEL_VARIANTE_ID) //
                .steril(STERIL) //
                .oeffentlich(true) //
                .medizinisch(MEDIZINISCH) //
                .kommentar(BEDARF_KOMMENTAR) //
                .build();
    }

    private NeuerBedarfRequest neuesBedarfRequest() {
        return NeuerBedarfRequest.builder() //
                .anzahl(BEDARF_ANZAHL) //
                .artikelId(BEDARF_ARTIKEL_ID.getValue()) //
                .artikelVarianteId(ARTIKEL_VARIANTE_ID.getValue()) //
                .steril(STERIL) //
                .medizinisch(MEDIZINISCH) //
                .kommentar(BEDARF_KOMMENTAR) //
                .oeffentlich(true) //
                .build();
    }

    private Bedarf bedarf() {
        return Bedarf.builder() //
                .id(BEDARF_ID) //
                .anzahl(BEDARF_ANZAHL) //
                .oeffentlich(true) //
                .rest(BEDARF_REST) //
                .artikel(BEDARF_ARTIKEL) //
                .artikelVariante(ARTIKEL_VARIANTE) //
                .institution(BEDARF_INSTITUTION) //
                .standort(BEDARF_STANDORT) //
                .steril(STERIL) //
                .medizinisch(MEDIZINISCH) //
                .kommentar(BEDARF_KOMMENTAR) //
                .bedient(BEDIENT) //
                .build();
    }

    private BedarfRO bedarfRO() {
        return BedarfRO.builder() //
                .id(BEDARF_ID.getValue()) //
                .verfuegbareAnzahl(BEDARF_ANZAHL) //
                .oeffentlich(true) //
                .artikel(BEDARF_ARTIKEL_RO) //
                .artikelVarianteId(ARTIKEL_VARIANTE_ID.getValue()) //
                .institutionId(BEDARF_INSTITUTION_ID.getValue()) //
                .ort(BEDARF_STANDORT_DTO.getOrt()) //
                .steril(STERIL) //
                .medizinisch(MEDIZINISCH) //
                .kommentar(BEDARF_KOMMENTAR) //
                .build();
    }

    private BedarfAnfrage anfrage() {
        return BedarfAnfrage.builder() //
                .id(ANFRAGE_ID) //
                .bedarf(bedarf()) //
                .angebotId(ANGEBOT_ID)
                .institution(ANFRAGE_INSTITUTION) //
                .standort(ANFRAGE_STANDORT) //
                .anzahl(ANFRAGE_ANZAHL) //
                .kommentar(ANFRAGE_KOMMENTAR) //
                .status(ANFRAGE_STATUS) //
                .build();
    }

    private BedarfAnfrageRO anfrageRO() {
        return BedarfAnfrageRO.builder() //
                .id(ANFRAGE_ID.getValue()) //
                .institution(ANFRAGE_INSTITUTION_DTO) //
                .angebotId(ANGEBOT_ID.getValue())
                .standort(ANFRAGE_STANDORT_DTO) //
                .anzahl(ANFRAGE_ANZAHL) //
                .kommentar(ANFRAGE_KOMMENTAR) //
                .status(ANFRAGE_STATUS) //
                .build();
    }

    private BedarfFilterEntryRO bedarfFilterEntryRO() {
        return BedarfFilterEntryRO.builder() //
                .id(FILTER_ENTRY_ID) //
                .anzahl(FILTER_ENTRY_ANZAHL) //
                .build();
    }

    private BedarfFilterEntry bedarfFilterEntry() {
        return BedarfFilterEntry.builder() //
                .id(FILTER_ENTRY_ID) //
                .anzahl(FILTER_ENTRY_ANZAHL) //
                .build();
    }
}
