package io.remedymatch.artikel.controller;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import io.remedymatch.artikel.domain.model.ArtikelKategorieId;

@Component
class StringToArtikelKategorieIdConverter implements Converter<String, ArtikelKategorieId> {
	@Override
	public ArtikelKategorieId convert(@NonNull String uuid) {
		return new ArtikelKategorieId(UUID.fromString(uuid));
	}
}