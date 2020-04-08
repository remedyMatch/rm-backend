package io.remedymatch.angebot.domain.service;

import static io.remedymatch.angebot.domain.service.AngebotAnfrageConverterFixtures.beispielAngebotAnfrage;
import static io.remedymatch.angebot.domain.service.AngebotAnfrageConverterFixtures.beispielAngebotAnfrageEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotAnfrageEntityConverter soll")
public class AngebotAnfrageEntityConverterShould {

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(beispielAngebotAnfrage(),
				AngebotAnfrageEntityConverter.convertAnfrage(beispielAngebotAnfrageEntity()));
	}

	@Test
	@DisplayName("Domain Objekt in Entity konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(beispielAngebotAnfrageEntity(), AngebotAnfrageEntityConverter.convert(beispielAngebotAnfrage()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(AngebotAnfrageEntityConverter.convert((AngebotAnfrage) null));
	}
}
