package io.remedymatch.artikel.domain.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.artikel.domain.model.Artikel;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelKategorieId;
import io.remedymatch.artikel.domain.model.ArtikelVariante;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelJpaRepository;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieJpaRepository;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
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
@Disabled
@DisplayName("ArtikelSucheService soll")
public class ArtikelSucheServiceShould {
	@Autowired
	private ArtikelSucheService sucheService;

	@MockBean
	private ArtikelKategorieJpaRepository artikelKategorieRepository;

	@MockBean
	private ArtikelJpaRepository artikelRepository;

	@MockBean
	private ArtikelVarianteJpaRepository artikelVarianteRepository;

	@Test
	@DisplayName("alle vorhandene Artikel zurueckliefern")
	void alle_vorhandene_Artikel_zurueckliefern() {

		val artikelKategorieId = randomArtikelKategorieId();

		val artikel1Id = randomArtikelId();
		val artikel1 = artikelMitVarianten(artikel1Id, artikelKategorieId, "Artikel 1", "Artikel 1 M", "Artikel 1 XL");
		val artikel1Entity = artikelEntityMitVarianten(artikel1Id, artikelKategorieId, "Artikel 1", "Artikel 1 M",
				"Artikel 1 XL");

		val artikel2Id = randomArtikelId();
		val artikel2 = artikelMitVarianten(artikel2Id, artikelKategorieId, "Artikel 2", "Artikel 2 500ml",
				"Artikel 2 1l");
		val artikel2Entity = artikelEntityMitVarianten(artikel2Id, artikelKategorieId, "Artikel", "Artikel 2 500ml",
				"Artikel 2 1l");

		given(artikelRepository.findAll()).willReturn(Arrays.asList(artikel1Entity, artikel2Entity));

		assertThat(//
				sucheService.findAlleArtikel(), //
				containsInAnyOrder(artikel1, artikel2));

		then(artikelRepository).should().findAll();
		then(artikelRepository).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("gesuchtes Artikel zurueckliefern")
	void gesuchtes_Artikel_zurueckliefern() {

		val artikelKategorieId = randomArtikelKategorieId();

		val artikelId = randomArtikelId();
		val artikel = artikelMitVarianten(artikelId, artikelKategorieId, "Artikel", "Artikel M", "Artikel XL");
		val artikelEntity = artikelEntityMitVarianten(artikelId, artikelKategorieId, "Artikel", "Artikel M",
				"Artikel XL");

		given(artikelRepository.findById(artikelId.getValue())).willReturn(Optional.of(artikelEntity));

		assertEquals(Optional.of(artikel), sucheService.findArtikel(artikelId));

		then(artikelRepository).should().findById(artikelId.getValue());
		then(artikelRepository).shouldHaveNoMoreInteractions();
	}

	private ArtikelKategorieId randomArtikelKategorieId() {
		return new ArtikelKategorieId(UUID.randomUUID());
	}

	private ArtikelId randomArtikelId() {
		return new ArtikelId(UUID.randomUUID());
	}

	private Artikel artikelMitVarianten(//
			final ArtikelId artikelId, //
			final ArtikelKategorieId artikelKategorieId, //
			final String name, //
			final String... varianten) {
		val builder = Artikel.builder() //
				.id(artikelId) //
				.artikelKategorieId(artikelKategorieId) //
				.name(name) //
				.beschreibung("Artikel Beschreibung " + name);

		if (varianten != null && varianten.length > 0) {
			builder.varianten(Stream.of(varianten)//
					.map(variante -> ArtikelVariante.builder() //
							.artikelId(artikelId) //
							.variante(variante) //
							.norm("Norm " + variante) //
							.beschreibung("Beschreibung " + variante) //
							.build())
					.collect(Collectors.toList()));
		}

		return builder.build();
	}

	private ArtikelEntity artikelEntityMitVarianten(//
			final ArtikelId artikelId, //
			final ArtikelKategorieId artikelKategorieId, //
			final String name, //
			final String... varianten) {

		val builder = ArtikelEntity.builder() //
				.id(artikelId.getValue()) //
				.artikelKategorie(artikelKategorieId.getValue()) //
				.name(name) //
				.beschreibung("Artikel Beschreibung " + name);

		if (varianten != null && varianten.length > 0) {
			builder.varianten(Stream.of(varianten)//
					.map(variante -> ArtikelVarianteEntity.builder() //
							.artikel(artikelId.getValue()) //
							.variante(variante) //
							.norm("Norm " + variante) //
							.beschreibung("Beschreibung " + variante) //
							.build())
					.collect(Collectors.toList()));
		}

		return builder.build();
	}
}
