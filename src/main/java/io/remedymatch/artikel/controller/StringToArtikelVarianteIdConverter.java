package io.remedymatch.artikel.controller;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import io.remedymatch.artikel.domain.model.ArtikelVarianteId;

@Component
class StringToArtikelVarianteIdConverter implements Converter<String, ArtikelVarianteId> {
	@Override
	public ArtikelVarianteId convert(@NonNull String uuid) {
		return new ArtikelVarianteId(UUID.fromString(uuid));
	}
}