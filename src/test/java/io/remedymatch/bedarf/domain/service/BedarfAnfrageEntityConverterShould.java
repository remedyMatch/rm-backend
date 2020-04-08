package io.remedymatch.bedarf.domain.service;

import static io.remedymatch.bedarf.domain.service.BedarfAnfrageConverterFixtures.beispielBedarfAnfrage;
import static io.remedymatch.bedarf.domain.service.BedarfAnfrageConverterFixtures.beispielBedarfAnfrageEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.bedarf.domain.model.BedarfAnfrage;

@ExtendWith(SpringExtension.class)
@DisplayName("BedarfAnfrageEntityConverter soll")
public class BedarfAnfrageEntityConverterShould {

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(beispielBedarfAnfrage(),
				BedarfAnfrageEntityConverter.convertAnfrage(beispielBedarfAnfrageEntity()));
	}

	@Test
	@DisplayName("Domain Objekt in Entity konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(beispielBedarfAnfrageEntity(), BedarfAnfrageEntityConverter.convert(beispielBedarfAnfrage()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(BedarfAnfrageEntityConverter.convert((BedarfAnfrage) null));
	}
}
