package io.remedymatch.institution.domain.service;

import static io.remedymatch.institution.domain.InstitutionTestFixtures.beispielInstitution;
import static io.remedymatch.institution.domain.InstitutionTestFixtures.beispielInstitutionEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("InstitutionEntityConverter soll")
public class InstitutionEntityConverterShould {

	@Test
	@DisplayName("eine Entity in ein Domain Objekt konvertieren")
	void eine_Entity_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(beispielInstitution(), InstitutionEntityConverter.convertInstitution(beispielInstitutionEntity()));
	}

	@Test
	@DisplayName("ein Domain Objekt in eine Entity konvertieren")
	void ein_Domain_Objekt_in_eine_Entity_konvertieren() {
		assertEquals(beispielInstitutionEntity(), InstitutionEntityConverter.convertInstitution(beispielInstitution()));
	}
}
