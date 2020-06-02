package io.remedymatch.bedarf.domain.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.bedarf.domain.model.BedarfFilterEntry;
import io.remedymatch.bedarf.infrastructure.FilterEntry;

final class BedarfFilterConverter {

	private BedarfFilterConverter() {

	}

	static List<BedarfFilterEntry> convertFilterEntries(final List<FilterEntry> filterEntries) {
		return filterEntries.stream().map(BedarfFilterConverter::convertFilterEntry).collect(Collectors.toList());
	}

	static BedarfFilterEntry convertFilterEntry(final FilterEntry filterEntry) {

		return BedarfFilterEntry.builder() //
				.id(filterEntry.getId()) //
				.anzahl(BigInteger.valueOf(filterEntry.getAnzahl())) //
				.build();
	}
}