package io.remedymatch.artikel.controller;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import io.remedymatch.artikel.domain.model.ArtikelId;

@Component
class StringToArtikelIdConverter implements Converter<String, ArtikelId> {
	@Override
	public ArtikelId convert(@NonNull String uuid) {
		return new ArtikelId(UUID.fromString(uuid));
	}
}