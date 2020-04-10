package io.remedymatch.artikel.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.remedymatch.artikel.domain.model.Artikel;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelKategorie;
import io.remedymatch.artikel.domain.model.ArtikelVariante;

public class ArtikelControllerMapper {
	private ArtikelControllerMapper() {

	}

	public static List<ArtikelKategorieRO> mapKategorienToRO(final List<ArtikelKategorie> kategorien) {
		return kategorien.stream().map(ArtikelControllerMapper::mapKategorieToRO).collect(Collectors.toList());
	}

	public static ArtikelKategorieRO mapKategorieToRO(final ArtikelKategorie kategorie) {
		return ArtikelKategorieRO.builder() //
				.id(kategorie.getId().getValue()) //
				.name(kategorie.getName()) //
				.icon(kategorie.getIcon()) //
				.build();
	}

	public static List<ArtikelRO> mapArtikelToRO(final List<Artikel> artikel) {
		return artikel.stream().map(ArtikelControllerMapper::mapArtikelToRO).collect(Collectors.toList());
	}

	public static ArtikelRO mapArtikelToRO(final Artikel artikel) {
		return ArtikelRO.builder() //
				.id(artikel.getId().getValue()) //
				.artikelKategorieId(artikel.getArtikelKategorieId().getValue()) //
				.name(artikel.getName()) //
				.beschreibung(artikel.getBeschreibung()) //
				.varianten(mapVariantenToRO(artikel.getVarianten())) //
				.build();
	}

	public static List<ArtikelVarianteRO> mapVariantenToRO(final List<ArtikelVariante> varianten) {
		return varianten.stream().map(ArtikelControllerMapper::mapVarianteToRO).collect(Collectors.toList());
	}

	public static ArtikelVarianteRO mapVarianteToRO(final ArtikelVariante variante) {
		return ArtikelVarianteRO.builder() //
				.id(variante.getId().getValue()) //
				.artikelId(variante.getArtikelId().getValue()) //
				.variante(variante.getVariante()) //
				.norm(variante.getNorm()) //
				.beschreibung(variante.getBeschreibung()) //
				.medizinischAuswaehlbar(variante.isMedizinischAuswaehlbar()) //
				.build();
	}

	static ArtikelId maptToArtikelId(final UUID artikelId) {
		return new ArtikelId(artikelId);
	}
}
