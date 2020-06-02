package io.remedymatch.bedarf.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.bedarf.domain.model.BedarfFilterEntry;
import io.remedymatch.bedarf.infrastructure.FilterEntry;

@ExtendWith(SpringExtension.class)
@DisplayName("BedarfFilterConverter soll")
public class BedarfFilterConverterShould {

	private static final UUID FILTER_ENTRY_ID = UUID.randomUUID();
	private static final BigInteger FILTER_ENTRY_ANZAHL = BigInteger.valueOf(123);

	@Test
	@DisplayName("eine leere Liste der FilterEntries in leere Liste der Domain Objekte konvertieren")
	void eine_leere_Liste_der_FilterEntries_in_leere_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Collections.emptyList(), BedarfFilterConverter.convertFilterEntries(Collections.emptyList()));
	}

	@Test
	@DisplayName("eine Liste der FilterEntries in Liste der Domain Objekte konvertieren")
	void eine_Liste_der_FilterEntries_in_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Arrays.asList(bedarfFilterEntry()),
				BedarfFilterConverter.convertFilterEntries(Arrays.asList(filterEntry())));
	}

	@Test
	@DisplayName("eine FilterEntry in ein Domain Objekt konvertieren")
	void eine_FilterEntry_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(bedarfFilterEntry(), BedarfFilterConverter.convertFilterEntry(filterEntry()));
	}

	private BedarfFilterEntry bedarfFilterEntry() {
		return BedarfFilterEntry.builder() //
				.id(FILTER_ENTRY_ID) //
				.anzahl(FILTER_ENTRY_ANZAHL) //
				.build();
	}

	private FilterEntry filterEntry() {
		return FilterEntry.builder() //
				.id(FILTER_ENTRY_ID) //
				.anzahl(FILTER_ENTRY_ANZAHL.longValue()) //
				.build();
	}
}
