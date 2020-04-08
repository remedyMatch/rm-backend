package io.remedymatch.angebot.domain.service;

import static io.remedymatch.angebot.domain.service.AngebotConverterFixtures.beispielAngebot;
import static io.remedymatch.angebot.domain.service.AngebotConverterFixtures.beispielAngebotEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.infrastructure.AngebotEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("AngebotEntityConverter soll")
public class AngebotEntityConverterShould {

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(beispielAngebot(), AngebotEntityConverter.convert(beispielAngebotEntity()));
	}

	@Test
	@DisplayName("null Entity in Domain Objekt konvertieren")
	void null_Entity_in_Domain_Objekt_konvertieren() {
		assertNull(AngebotEntityConverter.convert((AngebotEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt in Entity konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(beispielAngebotEntity(), AngebotEntityConverter.convert(beispielAngebot()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(AngebotEntityConverter.convert((Angebot) null));
	}
}
