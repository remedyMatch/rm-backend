package io.remedymatch.angebot.domain.service;

import static io.remedymatch.angebot.domain.service.AngebotAnfrageTestFixtures.beispielAngebotAnfrage;
import static io.remedymatch.angebot.domain.service.AngebotAnfrageTestFixtures.beispielAngebotAnfrageEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotAnfrageEntityConverter soll")
class AngebotAnfrageEntityConverterShould {

	@Test
	@DisplayName("eine leere Liste der Entities in leere Liste der Domain Objekte konvertieren")
	void eine_leere_Liste_der_Entities_in_leere_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Collections.emptyList(), AngebotAnfrageEntityConverter.convertAnfragen(Collections.emptyList()));
	}

	@Test
	@DisplayName("eine Liste der Entities in Liste der Domain Objekte konvertieren")
	void eine_Liste_der_Entities_in_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Arrays.asList(beispielAngebotAnfrage()),
				AngebotAnfrageEntityConverter.convertAnfragen(Arrays.asList(beispielAngebotAnfrageEntity())));
	}

	@Test
	@DisplayName("eine Entity in ein Domain Objekt konvertieren")
	void eine_Entity_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(beispielAngebotAnfrage(),
				AngebotAnfrageEntityConverter.convertAnfrage(beispielAngebotAnfrageEntity()));
	}
}
