package io.remedymatch.artikel.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.artikel.domain.model.ArtikelKategorie;
import io.remedymatch.artikel.domain.model.ArtikelKategorieId;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;

final class ArtikelKategorieEntityConverter {
	private ArtikelKategorieEntityConverter() {

	}

	static List<ArtikelKategorie> convertKategorien(final List<ArtikelKategorieEntity> entities) {
		return entities.stream().map(ArtikelKategorieEntityConverter::convertKategorie).collect(Collectors.toList());
	}

	static ArtikelKategorie convertKategorie(final ArtikelKategorieEntity entity) {
		if (entity == null) {
			return null;
		}

		return ArtikelKategorie.builder() //
				.id(new ArtikelKategorieId(entity.getId())) //
				.name(entity.getName()) //
				.icon(entity.getIcon()) //
				.build();
	}
}
