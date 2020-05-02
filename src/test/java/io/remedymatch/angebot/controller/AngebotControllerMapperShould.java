package io.remedymatch.angebot.controller;

import io.remedymatch.angebot.domain.model.*;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVariante;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.institution.controller.InstitutionRO;
import io.remedymatch.institution.controller.InstitutionStandortRO;
import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotMapper soll")
class AngebotControllerMapperShould {

    private static final AngebotId ANGEBOT_ID = new AngebotId(UUID.randomUUID());
    private static final BigDecimal ANGEBOT_ANZAHL = BigDecimal.valueOf(120.0);
    private static final BigDecimal ANGEBOT_REST = BigDecimal.valueOf(120.0);
    private static final ArtikelId ARTIKEL_ID = new ArtikelId(UUID.randomUUID());
    private static final ArtikelVarianteId ARTIKEL_VARIANTE_ID = new ArtikelVarianteId(UUID.randomUUID());
    private static final ArtikelVariante ARTIKEL_VARIANTE = ArtikelVariante.builder().id(ARTIKEL_VARIANTE_ID).artikelId(ARTIKEL_ID).build();
    private static final InstitutionId ANGEBOT_INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
    private static final Institution ANGEBOT_INSTITUTION = Institution.builder().id(ANGEBOT_INSTITUTION_ID).build();
    private static final InstitutionStandortId ANGEBOT_STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
    private static final InstitutionStandortRO ANGEBOT_STANDORT_DTO = InstitutionStandortRO.builder()
            .id(ANGEBOT_STANDORT_ID.getValue()).build();
    private static final InstitutionStandort ANGEBOT_STANDORT = InstitutionStandort.builder().id(ANGEBOT_STANDORT_ID)
            .build();
    private static final LocalDateTime HALTBARKEIT = LocalDateTime.now();
    private static final boolean STERIL = true;
    private static final boolean ORIGINALVERPACKT = true;
    private static final boolean MEDIZINISCH = true;
    private static final String ANGEBOT_KOMMENTAR = "Angebot Kommentar";
    private static final boolean BEDIENT = false;

    private static final AngebotAnfrageId ANFRAGE_ID = new AngebotAnfrageId(UUID.randomUUID());
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
    private static final AngebotAnfrageStatus ANFRAGE_STATUS = AngebotAnfrageStatus.OFFEN;

    private static final UUID FILTER_ENTRY_ID = UUID.randomUUID();
    private static final BigInteger FILTER_ENTRY_ANZAHL = BigInteger.valueOf(123);

    @Test
    @DisplayName("eine leere Liste der Domain Objekte in leere Liste der ROs konvertieren")
    void eine_leere_Liste_der_Domain_Objekte_in_leere_Liste_der_ROs_konvertieren() {
        assertEquals(Collections.emptyList(), AngebotControllerMapper.mapToAngeboteRO(Collections.emptyList()));
    }

    @Test
    @DisplayName("eine Liste der Domain Objekte in Liste der ROs konvertieren")
    void eine_Liste_der_Domain_Objekte_in_Liste_der_ROs_konvertieren() {
        assertEquals(Arrays.asList(angebotRO()), AngebotControllerMapper.mapToAngeboteRO(Arrays.asList(angebot())));
    }

    @Test
    @DisplayName("Angebot Domain Objekt in RO konvertieren")
    void angebot_domain_Objekt_in_RO_konvertieren() {
        assertEquals(angebotRO(), AngebotControllerMapper.mapToAngebotRO(angebot()));
    }

    @Test
    @DisplayName("eine leere Liste der FilterEntries in leere Liste der ROs konvertieren")
    void eine_leere_Liste_der_FilterEntries_in_leere_Liste_der_ROs_konvertieren() {
        assertEquals(Collections.emptyList(), AngebotControllerMapper.mapToFilterEntriesRO(Collections.emptyList()));
    }

    @Test
    @DisplayName("eine Liste der FilterEntries in Liste der ROs konvertieren")
    void eine_Liste_der_FilterEntries_in_Liste_der_ROs_konvertieren() {
        assertEquals(Arrays.asList(angebotFilterEntryRO()), AngebotControllerMapper.mapToFilterEntriesRO(Arrays.asList(angebotFilterEntry())));
    }

    @Test
    @DisplayName("FilterEntry in RO konvertieren")
    void filterEntry_in_RO_konvertieren() {
        assertEquals(angebotFilterEntryRO(), AngebotControllerMapper.mapToFilterEntryRO(angebotFilterEntry()));
    }

    @Test
    @DisplayName("NeueAngebot konvertieren koennen")
    void neueAngebot_konvertieren_koennen() {
        assertEquals(neueAngebot(), AngebotControllerMapper.mapToNeueAngebot(neueAngebotRequest()));
    }

    @Test
    @DisplayName("UUID in AngebotId konvertieren")
    void uuid_in_AngebotId_konvertieren() {
        assertEquals(ANGEBOT_ID, AngebotControllerMapper.mapToAngebotId(ANGEBOT_ID.getValue()));
    }

    @Test
    @DisplayName("AngebotAnfrage Domain Objekt in DTO konvertieren")
    void angebot_Anfrage_domain_Objekt_in_RO_konvertieren() {
        assertEquals(anfrageRO(), AngebotControllerMapper.mapToAnfrageRO(anfrage()));
    }

    /* help methods */

    private NeuesAngebot neueAngebot() {
        return NeuesAngebot.builder() //
                .anzahl(ANGEBOT_ANZAHL) //
                .artikelVarianteId(ARTIKEL_VARIANTE_ID) //
                .standortId(ANGEBOT_STANDORT_ID) //
                .haltbarkeit(HALTBARKEIT) //
                .steril(STERIL) //
                .originalverpackt(ORIGINALVERPACKT) //
                .medizinisch(MEDIZINISCH) //
                .kommentar(ANGEBOT_KOMMENTAR) //
                .build();
    }

    private NeuesAngebotRequest neueAngebotRequest() {
        return NeuesAngebotRequest.builder() //
                .anzahl(ANGEBOT_ANZAHL) //
                .artikelVarianteId(ARTIKEL_VARIANTE_ID.getValue()) //
                .standortId(ANGEBOT_STANDORT_ID.getValue()) //
                .haltbarkeit(HALTBARKEIT) //
                .steril(STERIL) //
                .originalverpackt(ORIGINALVERPACKT) //
                .medizinisch(MEDIZINISCH) //
                .kommentar(ANGEBOT_KOMMENTAR) //
                .build();
    }

    private Angebot angebot() {
        return Angebot.builder() //
                .id(ANGEBOT_ID) //
                .anzahl(ANGEBOT_ANZAHL) //
                .rest(ANGEBOT_REST) //
                .artikelVariante(ARTIKEL_VARIANTE) //
                .institution(ANGEBOT_INSTITUTION) //
                .standort(ANGEBOT_STANDORT) //
                .haltbarkeit(HALTBARKEIT) //
                .steril(STERIL) //
                .originalverpackt(ORIGINALVERPACKT) //
                .medizinisch(MEDIZINISCH) //
                .kommentar(ANGEBOT_KOMMENTAR) //
                .bedient(BEDIENT) //
                .build();
    }

    private AngebotRO angebotRO() {
        return AngebotRO.builder() //
                .id(ANGEBOT_ID.getValue()) //
                .verfuegbareAnzahl(ANGEBOT_ANZAHL) //
                .artikelId(ARTIKEL_ID.getValue()) //
                .artikelVarianteId(ARTIKEL_VARIANTE_ID.getValue()) //
                .ort(ANGEBOT_STANDORT_DTO.getOrt()) //
                .haltbarkeit(HALTBARKEIT) //
                .steril(STERIL) //
                .originalverpackt(ORIGINALVERPACKT) //
                .medizinisch(MEDIZINISCH) //
                .kommentar(ANGEBOT_KOMMENTAR) //
                .build();
    }

    private AngebotAnfrage anfrage() {
        return AngebotAnfrage.builder() //
                .id(ANFRAGE_ID) //
                .angebot(angebot()) //
                .institution(ANFRAGE_INSTITUTION) //
                .standort(ANFRAGE_STANDORT) //
                .anzahl(ANFRAGE_ANZAHL) //
                .kommentar(ANFRAGE_KOMMENTAR) //
                .prozessInstanzId(ANFRAGE_PROZESS_INSTANZ_ID) //
                .status(ANFRAGE_STATUS) //
                .build();
    }

    private AngebotAnfrageRO anfrageRO() {
        return AngebotAnfrageRO.builder() //
                .id(ANFRAGE_ID.getValue()) //
                .institution(ANFRAGE_INSTITUTION_DTO) //
                .standort(ANFRAGE_STANDORT_DTO) //
                .anzahl(ANFRAGE_ANZAHL) //
                .kommentar(ANFRAGE_KOMMENTAR) //
                .status(ANFRAGE_STATUS) //
                .build();
    }

    private AngebotFilterEntryRO angebotFilterEntryRO() {
        return AngebotFilterEntryRO.builder() //
                .id(FILTER_ENTRY_ID) //
                .anzahl(FILTER_ENTRY_ANZAHL) //
                .build();
    }

    private AngebotFilterEntry angebotFilterEntry() {
        return AngebotFilterEntry.builder() //
                .id(FILTER_ENTRY_ID) //
                .anzahl(FILTER_ENTRY_ANZAHL) //
                .build();
    }
}
