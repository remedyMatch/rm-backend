package io.remedymatch.artikel.domain.service;

import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikel;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikel1;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikel1Entity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikel2;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikel2Entity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelEntity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelId;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelKategorie1;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelKategorie1Entity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelKategorie2;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelKategorie2Entity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVariante;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVarianteEntity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVarianteId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.artikel.infrastructure.ArtikelJpaRepository;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieJpaRepository;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteJpaRepository;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		ArtikelSucheService.class, //
		ArtikelKategorieJpaRepository.class, //
		ArtikelJpaRepository.class, //
		ArtikelVarianteJpaRepository.class //
})
@Tag("Spring")
@DisplayName("ArtikelSucheService soll")
class ArtikelSucheServiceShould {
	@Autowired
	private ArtikelSucheService sucheService;

	@MockBean
	private ArtikelKategorieJpaRepository artikelKategorieRepository;

	@MockBean
	private ArtikelJpaRepository artikelRepository;

	@MockBean
	private ArtikelVarianteJpaRepository artikelVarianteRepository;

	@Test
	@DisplayName("alle vorhandene Artikel Kategorien zurueckliefern")
	void alle_vorhandene_Artikel_Kategorien_zurueckliefern() {

		val artikelKategorie1Entity = beispielArtikelKategorie1Entity();
		val artikelKategorie2Entity = beispielArtikelKategorie2Entity();

		given(artikelKategorieRepository.findAll()).willReturn(Arrays.asList(artikelKategorie1Entity, artikelKategorie2Entity));

		val expectedArtikel1Kategorie = beispielArtikelKategorie1();
		val expectedArtikel2Kategorie = beispielArtikelKategorie2();

		assertThat(//
				sucheService.findAlleKategorien(), //
				containsInAnyOrder(expectedArtikel1Kategorie, expectedArtikel2Kategorie));

		then(artikelKategorieRepository).should().findAll();
		then(artikelKategorieRepository).shouldHaveNoMoreInteractions();
		then(artikelRepository).shouldHaveNoInteractions();
		then(artikelVarianteRepository).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("alle vorhandene Artikel zurueckliefern")
	void alle_vorhandene_Artikel_zurueckliefern() {

		val artikel1Entity = beispielArtikel1Entity();
		val artikel2Entity = beispielArtikel2Entity();

		given(artikelRepository.findAll()).willReturn(Arrays.asList(artikel1Entity, artikel2Entity));

		val expectedArtikel1 = beispielArtikel1();
		val expectedArtikel2 = beispielArtikel2();

		assertThat(//
				sucheService.findAlleArtikel(), //
				containsInAnyOrder(expectedArtikel1, expectedArtikel2));

		then(artikelKategorieRepository).shouldHaveNoInteractions();
		then(artikelRepository).should().findAll();
		then(artikelRepository).shouldHaveNoMoreInteractions();
		then(artikelVarianteRepository).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("alle vorhandene Artikel zurueckliefern, wenn Name leer ist")
	void alle_vorhandene_Artikel_zurueckliefern_wenn_Name_leer_ist() {

		val artikel1Entity = beispielArtikel1Entity();
		val artikel2Entity = beispielArtikel2Entity();

		given(artikelRepository.findAll()).willReturn(Arrays.asList(artikel1Entity, artikel2Entity));

		val expectedArtikel1 = beispielArtikel1();
		val expectedArtikel2 = beispielArtikel2();

		assertThat(//
				sucheService.findByNameLike("  "), //
				containsInAnyOrder(expectedArtikel1, expectedArtikel2));

		then(artikelKategorieRepository).shouldHaveNoInteractions();
		then(artikelRepository).should().findAll();
		then(artikelRepository).shouldHaveNoMoreInteractions();
		then(artikelVarianteRepository).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("alle vorhandene Artikel mit Teil des Names zurueckliefern")
	void alle_vorhandene_Artikel_mit_Teil_des_Names_zurueckliefern() {

		val artikel1Entity = beispielArtikel1Entity();
		val artikel2Entity = beispielArtikel2Entity();

		given(artikelRepository.findByNameContainingIgnoreCase("rti"))
				.willReturn(Arrays.asList(artikel1Entity, artikel2Entity));

		val expectedArtikel1 = beispielArtikel1();
		val expectedArtikel2 = beispielArtikel2();

		assertThat(//
				sucheService.findByNameLike("rti"), //
				containsInAnyOrder(expectedArtikel1, expectedArtikel2));

		then(artikelKategorieRepository).shouldHaveNoInteractions();
		then(artikelRepository).should().findByNameContainingIgnoreCase("rti");
		then(artikelRepository).shouldHaveNoMoreInteractions();
		then(artikelVarianteRepository).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("gesuchtes Artikel zurueckliefern")
	void gesuchtes_Artikel_zurueckliefern() {

		val artikelId = beispielArtikelId();
		val artikelEntity = beispielArtikelEntity();

		given(artikelRepository.findById(artikelId.getValue())).willReturn(Optional.of(artikelEntity));

		val expectedArtikel = beispielArtikel();

		assertEquals(Optional.of(expectedArtikel), sucheService.findArtikel(artikelId));

		then(artikelKategorieRepository).shouldHaveNoInteractions();
		then(artikelRepository).should().findById(artikelId.getValue());
		then(artikelRepository).shouldHaveNoMoreInteractions();
		then(artikelVarianteRepository).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("gesuchte Artikel Variante zurueckliefern")
	void gesuchte_Artikel_Variante_zurueckliefern() {

		val artikelVarianteId = beispielArtikelVarianteId();
		val artikelVarianteEntity = beispielArtikelVarianteEntity();

		given(artikelVarianteRepository.findById(artikelVarianteId.getValue()))
				.willReturn(Optional.of(artikelVarianteEntity));

		val expectedArtikelVariante = beispielArtikelVariante();

		assertEquals(Optional.of(expectedArtikelVariante), sucheService.findArtikelVariante(artikelVarianteId));

		then(artikelKategorieRepository).shouldHaveNoInteractions();
		then(artikelRepository).shouldHaveNoInteractions();
		then(artikelVarianteRepository).should().findById(artikelVarianteId.getValue());
		then(artikelVarianteRepository).shouldHaveNoMoreInteractions();
	}
}
