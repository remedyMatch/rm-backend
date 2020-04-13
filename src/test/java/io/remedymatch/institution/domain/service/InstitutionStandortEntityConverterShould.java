package io.remedymatch.institution.domain.service;

import static io.remedymatch.institution.domain.InstitutionTestFixtures.beispielHaupstandort;
import static io.remedymatch.institution.domain.InstitutionTestFixtures.beispielHaupstandortEntity;
import static io.remedymatch.institution.domain.InstitutionTestFixtures.beispielStandort1;
import static io.remedymatch.institution.domain.InstitutionTestFixtures.beispielStandort1Entity;
import static io.remedymatch.institution.domain.InstitutionTestFixtures.beispielStandort2;
import static io.remedymatch.institution.domain.InstitutionTestFixtures.beispielStandort2Entity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("InstitutionStandortEntityConverter soll")
public class InstitutionStandortEntityConverterShould {

	/*
	 * Entities -> Domain Objekte
	 */

	@Test
	@DisplayName("eine leere Liste der Entities in leere Liste der Domain Objekte konvertieren")
	void eine_leere_Liste_der_Entities_in_leere_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Collections.emptyList(),
				InstitutionStandortEntityConverter.convertStandortEntities(Collections.emptyList()));
	}

	@Test
	@DisplayName("eine Liste der Entities in Liste der Domain Objekte konvertieren")
	void eine_Liste_der_Entities_in_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Arrays.asList(beispielStandort1(), beispielStandort2()), InstitutionStandortEntityConverter
				.convertStandortEntities(Arrays.asList(beispielStandort1Entity(), beispielStandort2Entity())));
	}

	@Test
	@DisplayName("eine null Entity in ein null Domain Objekt konvertieren")
	void eine_null_Entity_in_ein_null_Domain_Objekt_konvertieren() {
		assertNull(InstitutionStandortEntityConverter.convertStandort((InstitutionStandortEntity) null));
	}

	@Test
	@DisplayName("eine Entity in ein Domain Objekt konvertieren")
	void eine_Entity_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(beispielHaupstandort(),
				InstitutionStandortEntityConverter.convertStandort(beispielHaupstandortEntity()));
	}

	/*
	 * Domain Objekte -> Entities
	 */

	@Test
	@DisplayName("eine leere Liste der Domain Objekte in leere Liste der Entities konvertieren")
	void eine_leere_Liste_der_Domain_Objekte_in_leere_Liste_der_Entities_konvertieren() {
		assertEquals(Collections.emptyList(),
				InstitutionStandortEntityConverter.convertStandorte(Collections.emptyList()));
	}

	@Test
	@DisplayName("eine Liste der Domain Objekte  in Liste der Entities konvertieren")
	void eine_Liste_der_Domain_Objekte_in_Liste_der_Entities_konvertieren() {
		assertEquals(Arrays.asList(beispielStandort1Entity(), beispielStandort2Entity()),
				InstitutionStandortEntityConverter
						.convertStandorte(Arrays.asList(beispielStandort1(), beispielStandort2())));
	}

	@Test
	@DisplayName("ein null Domain Objekt in eine null Entity konvertieren")
	void ein_null_Domain_Objekt_in_eine_null_Entity_konvertieren() {
		assertNull(InstitutionStandortEntityConverter.convertStandort((InstitutionStandort) null));
	}

	@Test
	@DisplayName("ein Domain Objekt in eine Entity konvertieren")
	void ein_Domain_Objekt_in_eine_Entity_konvertieren() {
		assertEquals(beispielHaupstandortEntity(),
				InstitutionStandortEntityConverter.convertStandort(beispielHaupstandort()));
	}
}
