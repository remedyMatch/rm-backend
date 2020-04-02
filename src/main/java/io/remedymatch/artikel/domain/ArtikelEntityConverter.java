package io.remedymatch.artikel.domain;

import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelEntity.ArtikelEntityBuilder;

public class ArtikelEntityConverter {
	
	public static Artikel convert(final ArtikelEntity entity) {
		if (entity == null) {
			return null;
		}

		return Artikel.builder() //
				.id(new ArtikelId(entity.getId())) //
				.ean(entity.getEan()) //
				.name(entity.getName()) //
				.beschreibung(entity.getBeschreibung()) //
				.hersteller(entity.getHersteller()) //
				.artikelKategorie(ArtikelKategorieEntityConverter.convert(entity.getArtikelKategorie()))
				.build();
	}

	public static ArtikelEntity convert(final Artikel artikel) {
		if (artikel == null) {
			return null;
		}

		ArtikelEntityBuilder builder = ArtikelEntity.builder();
		if (artikel.getId() != null) {
			builder.id(artikel.getId().getValue());
		}

		return builder.ean(artikel.getEan()) //
				.name(artikel.getName()) //
				.beschreibung(artikel.getBeschreibung()) //
				.hersteller(artikel.getHersteller()) // //
				.artikelKategorie(ArtikelKategorieEntityConverter.convert(artikel.getArtikelKategorie()))
				.build();
	}
}
