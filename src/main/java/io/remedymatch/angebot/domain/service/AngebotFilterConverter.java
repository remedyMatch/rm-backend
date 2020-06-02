package io.remedymatch.angebot.domain.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.angebot.domain.model.AngebotFilterEntry;
import io.remedymatch.angebot.infrastructure.FilterEntry;

final class AngebotFilterConverter {

	private AngebotFilterConverter() {

	}

	static List<AngebotFilterEntry> convertFilterEntries(final List<FilterEntry> filterEntries) {
		return filterEntries.stream().map(AngebotFilterConverter::convertFilterEntry).collect(Collectors.toList());
	}

	static AngebotFilterEntry convertFilterEntry(final FilterEntry filterEntry) {

		return AngebotFilterEntry.builder() //
				.id(filterEntry.getId()) //
				.anzahl(BigInteger.valueOf(filterEntry.getAnzahl())) //
				.build();
	}
}