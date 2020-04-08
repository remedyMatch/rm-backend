package io.remedymatch.bedarf.domain.service;

import static io.remedymatch.bedarf.domain.service.BedarfConverterFixtures.beispielBedarf;
import static io.remedymatch.bedarf.domain.service.BedarfConverterFixtures.beispielBedarfEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("BedarfEntityConverter soll")
public class BedarfEntityConverterShould {

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(beispielBedarf(), BedarfEntityConverter.convert(beispielBedarfEntity()));
	}

	@Test
	@DisplayName("null Entity in Domain Objekt konvertieren")
	void null_Entity_in_Domain_Objekt_konvertieren() {
		assertNull(BedarfEntityConverter.convert((BedarfEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt in Entity konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(beispielBedarfEntity(), BedarfEntityConverter.convert(beispielBedarf()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(BedarfEntityConverter.convert((Bedarf) null));
	}
}
