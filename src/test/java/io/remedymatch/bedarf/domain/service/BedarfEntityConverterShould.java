package io.remedymatch.bedarf.domain.service;

import static io.remedymatch.bedarf.domain.service.BedarfTestFixtures.beispielBedarf;
import static io.remedymatch.bedarf.domain.service.BedarfTestFixtures.beispielBedarfEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.val;

@ExtendWith(SpringExtension.class)
@DisplayName("BedarfEntityConverter soll")
class BedarfEntityConverterShould {

	@Test
	@DisplayName("eine leere Liste der Entities in leere Liste der Domain Objekte konvertieren")
	void eine_leere_Liste_der_Entities_in_leere_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Collections.emptyList(), BedarfEntityConverter.convertBedarfe(Collections.emptyList()));
	}

	@Test
	@DisplayName("eine Liste der Entities in Liste der Domain Objekte konvertieren")
	void eine_Liste_der_Entities_in_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Arrays.asList(beispielBedarf()),
				BedarfEntityConverter.convertBedarfe(Arrays.asList(beispielBedarfEntity())));
	}

	@Test
	@DisplayName("eine null Entity in ein null Domain Objekt konvertieren")
	void eine_null_Entity_in_ein_null_Domain_Objekt_konvertieren() {
		assertNull(BedarfEntityConverter.convertBedarf(null));
	}

	@Test
	@DisplayName("eine Entity in ein Domain Objekt konvertieren")
	void eine_Entity_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(beispielBedarf(), BedarfEntityConverter.convertBedarf(beispielBedarfEntity()));
	}
	
	@Test
	@DisplayName("eine Entity ohne ArtikelVariante in ein Domain Objekt konvertieren")
	void eine_Entity_ohne_ArtikelVariante_in_ein_Domain_Objekt_konvertieren() {
		val bedarfEntity = beispielBedarfEntity();
		bedarfEntity.setArtikelVariante(null);
		val expectedBedarf = beispielBedarf();
		expectedBedarf.setArtikelVariante(null);

		assertEquals(expectedBedarf, BedarfEntityConverter.convertBedarf(bedarfEntity));
	}
}
