package io.remedymatch.artikel.domain.service;

import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelKategorie;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelKategorie1;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelKategorie1Entity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelKategorie2;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelKategorie2Entity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelKategorieEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("ArtikelKategorieEntityConverter soll")
class ArtikelKategorieEntityConverterShould {

	@Test
	@DisplayName("eine leere Liste der Entities in leere Liste der Domain Objekte konvertieren")
	void eine_leere_Liste_der_Entities_in_leere_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Collections.emptyList(),
				ArtikelKategorieEntityConverter.convertKategorien(Collections.emptyList()));
	}

	@Test
	@DisplayName("eine Liste der Entities in Liste der Domain Objekte konvertieren")
	void eine_Liste_der_Entities_in_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Arrays.asList(beispielArtikelKategorie1(), beispielArtikelKategorie2()),
				ArtikelKategorieEntityConverter.convertKategorien(
						Arrays.asList(beispielArtikelKategorie1Entity(), beispielArtikelKategorie2Entity())));
	}

	@Test
	@DisplayName("eine null Entity in ein null Domain Objekt konvertieren")
	void eine_null_Entity_in_ein_null_Domain_Objekt_konvertieren() {
		assertNull(ArtikelKategorieEntityConverter.convertKategorie(null));
	}

	@Test
	@DisplayName("eine Entity in ein Domain Objekt konvertieren")
	void eine_Entity_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(beispielArtikelKategorie(),
				ArtikelKategorieEntityConverter.convertKategorie(beispielArtikelKategorieEntity()));
	}
}
