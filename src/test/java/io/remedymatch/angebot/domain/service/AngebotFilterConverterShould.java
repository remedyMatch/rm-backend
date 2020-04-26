package io.remedymatch.angebot.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.model.AngebotFilterEntry;
import io.remedymatch.angebot.infrastructure.FilterEntry;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotFilterConverter soll")
public class AngebotFilterConverterShould {

	private static final UUID FILTER_ENTRY_ID = UUID.randomUUID();
	private static final BigInteger FILTER_ENTRY_ANZAHL = BigInteger.valueOf(123);

	@Test
	@DisplayName("eine leere Liste der FilterEntries in leere Liste der Domain Objekte konvertieren")
	void eine_leere_Liste_der_FilterEntries_in_leere_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Collections.emptyList(), AngebotFilterConverter.convertFilterEntries(Collections.emptyList()));
	}

	@Test
	@DisplayName("eine Liste der FilterEntries in Liste der Domain Objekte konvertieren")
	void eine_Liste_der_FilterEntries_in_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Arrays.asList(angebotFilterEntry()),
				AngebotFilterConverter.convertFilterEntries(Arrays.asList(filterEntry())));
	}

	@Test
	@DisplayName("eine FilterEntry in ein Domain Objekt konvertieren")
	void eine_FilterEntry_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(angebotFilterEntry(), AngebotFilterConverter.convertFilterEntry(filterEntry()));
	}

	private AngebotFilterEntry angebotFilterEntry() {
		return AngebotFilterEntry.builder() //
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
