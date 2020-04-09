package io.remedymatch.institution.domain;

import static io.remedymatch.institution.domain.InstitutionTestFixtures.beispielInstitution;
import static io.remedymatch.institution.domain.InstitutionTestFixtures.beispielInstitutionEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.infrastructure.InstitutionEntity;
import lombok.val;

@ExtendWith(SpringExtension.class)
@DisplayName("InstitutionEntityConverter soll")
public class InstitutionEntityConverterShould {

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(beispielInstitution(), InstitutionEntityConverter.convert(beispielInstitutionEntity()));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(InstitutionEntityConverter.convert((InstitutionEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt ohne Hauptstandort und Standorte in Entity konvertieren")
	void domain_Objekt_ohne_Hauptstandort_in_Entity_konvertieren() {
		val institution = beispielInstitution();
		institution.setHauptstandort(null);
		val institutionEntity = beispielInstitutionEntity();
		institutionEntity.setHauptstandort(null);
		assertEquals(institutionEntity, InstitutionEntityConverter.convert(institution));
	}

	@Test
	@DisplayName("Domain Objekt mit Hauptstandort und Standorte in Entity konvertieren")
	void domain_Objekt_mit_Hauptstandort_in_Entity_konvertieren() {
		assertEquals(beispielInstitutionEntity(), InstitutionEntityConverter.convert(beispielInstitution()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(InstitutionEntityConverter.convert((Institution) null));
	}
}
