package io.remedymatch.angebot.domain.service;

import static io.remedymatch.angebot.domain.service.AngebotTestFixtures.beispielAngebot;
import static io.remedymatch.angebot.domain.service.AngebotTestFixtures.beispielAngebotEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotEntityConverter soll")
class AngebotEntityConverterShould {

	@Test
	@DisplayName("eine leere Liste der Entities in leere Liste der Domain Objekte konvertieren")
	void eine_leere_Liste_der_Entities_in_leere_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Collections.emptyList(), AngebotEntityConverter.convertAngebote(Collections.emptyList()));
	}

	@Test
	@DisplayName("eine Liste der Entities in Liste der Domain Objekte konvertieren")
	void eine_Liste_der_Entities_in_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Arrays.asList(beispielAngebot()),
				AngebotEntityConverter.convertAngebote(Arrays.asList(beispielAngebotEntity())));
	}

	@Test
	@DisplayName("eine null Entity in ein null Domain Objekt konvertieren")
	void eine_null_Entity_in_ein_null_Domain_Objekt_konvertieren() {
		assertNull(AngebotEntityConverter.convertAngebot(null));
	}

	@Test
	@DisplayName("eine Entity in ein Domain Objekt konvertieren")
	void eine_Entity_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(beispielAngebot(), AngebotEntityConverter.convertAngebot(beispielAngebotEntity()));
	}
}
