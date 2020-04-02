package io.remedymatch.artikel.domain;

import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity.ArtikelKategorieEntityBuilder;

class ArtikelKategorieEntityConverter {

	static ArtikelKategorie convert(final ArtikelKategorieEntity entity) {
		if (entity == null) {
			return null;
		}

		return ArtikelKategorie.builder() //
				.id(new ArtikelKategorieId(entity.getId())) //
				.name(entity.getName()) //
				.build();
	}

	static ArtikelKategorieEntity convert(final ArtikelKategorie artikelKategorie) {
		if (artikelKategorie == null) {
			return null;
		}

		ArtikelKategorieEntityBuilder builder = ArtikelKategorieEntity.builder();
		if (artikelKategorie.getId() != null) {
			builder.id(artikelKategorie.getId().getValue());
		}

		return builder.name(artikelKategorie.getName()) //
				.build();
	}
}
