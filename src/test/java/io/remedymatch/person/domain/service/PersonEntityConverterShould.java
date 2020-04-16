package io.remedymatch.person.domain.service;

import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPerson;
import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPersonEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("PersonEntityConverter soll")
class PersonEntityConverterShould {
	
	@Test
	@DisplayName("eine Entity in ein Domain Objekt konvertieren")
	void eine_entity_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(beispielPerson(), PersonEntityConverter.convert(beispielPersonEntity()));
	}
}
