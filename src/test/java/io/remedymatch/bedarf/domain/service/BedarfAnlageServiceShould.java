package io.remedymatch.bedarf.domain.service;

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.NeuerBedarf;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.domain.OperationNotAllowedException;
import io.remedymatch.geodaten.domain.GeocodingService;
import io.remedymatch.usercontext.UserContextService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.*;
import static io.remedymatch.bedarf.domain.service.BedarfTestFixtures.beispielBedarfId;
import static io.remedymatch.usercontext.UserContextTestFixtures.beispielUserContextPerson;
import static io.remedymatch.usercontext.UserContextTestFixtures.beispielUserContextPersonEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
        BedarfAnlageService.class, //
        BedarfJpaRepository.class, //
        UserContextService.class, //
        ArtikelSucheService.class, //
        BedarfProzessService.class, //
        GeocodingService.class, //
})
@Tag("Spring")
@DisplayName("BedarfAnlageService soll")
class BedarfAnlageServiceShould {

    @Autowired
    private BedarfAnlageService bedarfAnlageService;

    @MockBean
    private BedarfJpaRepository bedarfRepository;

    @MockBean
    private UserContextService userService;

    @MockBean
    private ArtikelSucheService artikelSucheService;

    @MockBean
    private GeocodingService geocodingService;

    @MockBean
    private BedarfProzessService bedarfProzessService;

    @Test
    @DisplayName("Fehler werfen wenn beide Artikel und ArtikelVariante leer sind")
    void fehler_werfen_wenn_beide_Artikel_und_Artikel_Variante_leer_sind() {
        assertThrows(OperationNotAllowedException.class, //
                () -> bedarfAnlageService.neuenBedarfEinstellen(NeuerBedarf.builder().build()));
    }

    @Test
    @DisplayName("Fehler werfen wenn Artikel und ArtikelVariante nicht zusammen passen")
    void fehler_werfen_wenn_Artikel_und_ArtikelVariante_nicht_zusammen_passen() {

        val artikelId = new ArtikelId(UUID.randomUUID());

        val artikelVarianteId = beispielArtikelVarianteId();
        val artikelVariante = beispielArtikelVariante();

        val anzahl = BigDecimal.valueOf(100);
        val steril = true;
        val medizinisch = true;
        val kommentar = "Neues Bedarf";

        val userStandort = beispielUserContextPerson().getAktuellesStandort();

        val neuesBedarf = NeuerBedarf.builder() //
                .artikelId(artikelId) //
                .artikelVarianteId(artikelVarianteId) //
                .anzahl(anzahl) //
                .steril(steril) //
                .medizinisch(medizinisch) //
                .kommentar(kommentar) //
                .build();

        given(userService.getContextStandort()).willReturn(userStandort);
        given(artikelSucheService.getArtikelVarianteOrElseThrow(artikelVarianteId)).willReturn(artikelVariante);
        given(userService.getContextUser()).willReturn(beispielUserContextPerson());

        assertThrows(OperationNotAllowedException.class, //
                () -> bedarfAnlageService.neuenBedarfEinstellen(neuesBedarf));
    }

    @Test
    @DisplayName("neues Bedarf fuer Artikel anlegen koennen")
    void neues_Bedarf_fuer_Artikel_anlegen_koennen() {

        val artikelId = beispielArtikelId();
        val artikel = beispielArtikel();
        val artikelEntity = beispielArtikelEntity();

        val anzahl = BigDecimal.valueOf(100);
        val steril = true;
        val medizinisch = true;
        val kommentar = "Neues Bedarf";

        val userStandort = beispielUserContextPerson().getAktuellesStandort();
        val userStandortEntity = beispielUserContextPersonEntity().getAktuellesStandort();

        val institution = userStandort.getInstitution();
        val institutionEntity = userStandortEntity.getInstitution();
        val standort = userStandort.getStandort();
        val standortEntity = userStandortEntity.getStandort();

        BedarfEntity bedarfEntityOhneId = BedarfEntity.builder() //
                .artikel(artikelEntity) //
                .anzahl(anzahl) //
                .rest(anzahl) //
                .institution(institutionEntity) //
                .standort(standortEntity) //
                .steril(steril) //
                .medizinisch(medizinisch) //
                .kommentar(kommentar) //
                .build();

        val bedarfId = beispielBedarfId();

        BedarfEntity bedarfEntityMitId = BedarfEntity.builder() //
                .id(bedarfId.getValue()) //
                .artikel(artikelEntity) //
                .anzahl(anzahl) //
                .rest(anzahl) //
                .institution(institutionEntity) //
                .standort(standortEntity) //
                .steril(steril) //
                .medizinisch(medizinisch) //
                .kommentar(kommentar) //
                .build();

        val neuesBedarf = NeuerBedarf.builder() //
                .artikelId(artikelId) //
                .anzahl(anzahl) //
                .steril(steril) //
                .medizinisch(medizinisch) //
                .kommentar(kommentar) //
                .build();

        val entfernung = BigDecimal.valueOf(123664);

        given(userService.getContextStandort()).willReturn(userStandort);
        given(geocodingService.berechneUserDistanzInKilometer(standort)).willReturn(entfernung);
        given(artikelSucheService.getArtikelOrElseThrow(artikelId)).willReturn(artikel);
        given(bedarfRepository.save(bedarfEntityOhneId)).willReturn(bedarfEntityMitId);
        given(userService.getContextUser()).willReturn(beispielUserContextPerson());

        val expectedBedarf = Bedarf.builder() //
                .id(bedarfId) //
                .artikel(artikel) //
                .anzahl(anzahl) //
                .rest(anzahl) //
                .institution(institution) //
                .standort(standort) //
                .steril(steril) //
                .medizinisch(medizinisch) //
                .kommentar(kommentar) //
                .entfernung(entfernung) //
                .build();

        assertEquals(expectedBedarf, bedarfAnlageService.neuenBedarfEinstellen(neuesBedarf));

        then(bedarfRepository).should().save(bedarfEntityOhneId);
        then(bedarfRepository).shouldHaveNoMoreInteractions();
        then(userService).should(times(2)).getContextStandort();
        then(userService).should().getContextUserId();
        then(userService).shouldHaveNoMoreInteractions();
        then(artikelSucheService).should().getArtikelOrElseThrow(artikelId);
        then(artikelSucheService).shouldHaveNoMoreInteractions();
        then(geocodingService).should().berechneUserDistanzInKilometer(standort);
        then(geocodingService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("neues Bedarf fuer ArtikelVariante anlegen koennen")
    void neues_Bedarf_fuer_Artikel_Variante_anlegen_koennen() {

        val artikelVarianteId = beispielArtikelVarianteId();
        val artikelVariante = beispielArtikelVariante();
        val artikelVarianteEntity = beispielArtikelVarianteEntity();

        val artikelId = artikelVariante.getArtikelId();
        val artikel = beispielArtikel();
        artikel.setId(artikelId);
        val artikelEntity = beispielArtikelEntity();
        artikelEntity.setId(artikelId.getValue());

        val anzahl = BigDecimal.valueOf(100);
        val steril = true;
        val medizinisch = true;
        val kommentar = "Neues Bedarf";

        val userStandort = beispielUserContextPerson().getAktuellesStandort();
        val userStandortEntity = beispielUserContextPersonEntity().getAktuellesStandort();

        val institution = userStandort.getInstitution();
        val institutionEntity = userStandortEntity.getInstitution();
        val standort = userStandort.getStandort();
        val standortEntity = userStandortEntity.getStandort();

        BedarfEntity bedarfEntityOhneId = BedarfEntity.builder() //
                .artikel(artikelEntity) //
                .artikelVariante(artikelVarianteEntity) //
                .anzahl(anzahl) //
                .rest(anzahl) //
                .institution(institutionEntity) //
                .standort(standortEntity) //
                .steril(steril) //
                .medizinisch(medizinisch) //
                .kommentar(kommentar) //
                .build();

        val bedarfId = beispielBedarfId();

        BedarfEntity bedarfEntityMitId = BedarfEntity.builder() //
                .id(bedarfId.getValue()) //
                .artikel(artikelEntity) //
                .artikelVariante(artikelVarianteEntity) //
                .anzahl(anzahl) //
                .rest(anzahl) //
                .institution(institutionEntity) //
                .standort(standortEntity) //
                .steril(steril) //
                .medizinisch(medizinisch) //
                .kommentar(kommentar) //
                .build();

        val neueBedarf = NeuerBedarf.builder() //
                .artikelVarianteId(artikelVarianteId) //
                .anzahl(anzahl) //
                .steril(steril) //
                .medizinisch(medizinisch) //
                .kommentar(kommentar) //
                .build();

        val entfernung = BigDecimal.valueOf(123664);

        given(userService.getContextStandort()).willReturn(userStandort);
        given(geocodingService.berechneUserDistanzInKilometer(standort)).willReturn(entfernung);
        given(artikelSucheService.getArtikelVarianteOrElseThrow(artikelVarianteId)).willReturn(artikelVariante);
        given(artikelSucheService.getArtikelOrElseThrow(artikelId)).willReturn(artikel);
        given(bedarfRepository.save(bedarfEntityOhneId)).willReturn(bedarfEntityMitId);
        given(userService.getContextUser()).willReturn(beispielUserContextPerson());

        val expectedBedarf = Bedarf.builder() //
                .id(bedarfId) //
                .artikel(artikel) //
                .artikelVariante(artikelVariante) //
                .anzahl(anzahl) //
                .rest(anzahl) //
                .institution(institution) //
                .standort(standort) //
                .steril(steril) //
                .medizinisch(medizinisch) //
                .kommentar(kommentar) //
                .entfernung(entfernung) //
                .build();

        assertEquals(expectedBedarf, bedarfAnlageService.neuenBedarfEinstellen(neueBedarf));

        then(bedarfRepository).should().save(bedarfEntityOhneId);
        then(bedarfRepository).shouldHaveNoMoreInteractions();
        then(userService).should(times(2)).getContextStandort();
        then(userService).should().getContextUserId();
        then(userService).shouldHaveNoMoreInteractions();
        then(artikelSucheService).should().getArtikelVarianteOrElseThrow(artikelVarianteId);
        then(artikelSucheService).should().getArtikelOrElseThrow(artikelId);
        then(artikelSucheService).shouldHaveNoMoreInteractions();
        then(geocodingService).should().berechneUserDistanzInKilometer(standort);
        then(geocodingService).shouldHaveNoMoreInteractions();
    }
}
