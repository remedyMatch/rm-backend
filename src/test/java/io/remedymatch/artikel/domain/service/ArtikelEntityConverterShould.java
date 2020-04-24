package io.remedymatch.artikel.domain.service;

import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikel;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikel1;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikel1Entity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikel2;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikel2Entity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelEntity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVariante;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVariante1;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVariante1Entity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVariante2;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVariante2Entity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVarianteEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("ArtikelEntityConverter soll")
class ArtikelEntityConverterShould {

	/*
	 * Entities -> Domain Objekte
	 */

	@Test
	@DisplayName("fuer Artikel eine leere Liste der Entities in leere Liste der Domain Objekte konvertieren")
	void fuer_Artikel_eine_leere_Liste_der_Entities_in_leere_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Collections.emptyList(), ArtikelEntityConverter.convertArtikel(Collections.emptyList()));
	}

	@Test
	@DisplayName("fuer Artikel leere Liste der Entities in Liste der Domain Objekte konvertieren")
	void fuer_Artikel_Liste_der_Entities_in_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Arrays.asList(beispielArtikel1(), beispielArtikel2()), ArtikelEntityConverter
				.convertArtikel(Arrays.asList(beispielArtikel1Entity(), beispielArtikel2Entity())));
	}

	@Test
	@DisplayName("fuer Artikel eine Entity in ein Domain Objekt konvertieren")
	void fuer_Artikel_eine_Entity_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(beispielArtikel(), ArtikelEntityConverter.convertArtikel(beispielArtikelEntity()));
	}

	@Test
	@DisplayName("fuer ArtikelVarianten eine leere Liste der Entities in leere Liste der Domain Objekte konvertieren")
	void fuer_ArtikelVarianten_eine_leere_Liste_der_Entities_in_leere_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Collections.emptyList(), ArtikelEntityConverter.convertVarianten(Collections.emptyList()));
	}

	@Test
	@DisplayName("fuer ArtikelVarianten leere Liste der Entities in Liste der Domain Objekte konvertieren")
	void fuer_ArtikelVarianten_eine_Liste_der_Entities_in_Liste_der_Domain_Objekte_konvertieren() {
		assertEquals(Arrays.asList(beispielArtikelVariante1(), beispielArtikelVariante2()), ArtikelEntityConverter
				.convertVarianten(Arrays.asList(beispielArtikelVariante1Entity(), beispielArtikelVariante2Entity())));
	}

	@Test
	@DisplayName("fuer ArtikelVariante eine Entity in ein Domain Objekt konvertieren")
	void fuer_ArtikelVariante_eine_Entity_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(beispielArtikelVariante(),
				ArtikelEntityConverter.convertVariante(beispielArtikelVarianteEntity()));
	}

	/*
	 * Domain Objekte -> Entities
	 */

	@Test
	@DisplayName("fuer Artikel ein Domain Objekt in eine Entity konvertieren")
	void fuer_Artikel_ein_Domain_Objekt_in_eine_Entity_konvertieren() {
		assertEquals(beispielArtikelEntity(), ArtikelEntityConverter.convertArtikel(beispielArtikel()));
	}

	@Test
	@DisplayName("fuer ArtikelVariante ein Domain Objekt in eine Entity  konvertieren")
	void fuer_ArtikelVariante_ein_Domain_Objekt_in_eine_Entity_konvertieren() {
		assertEquals(beispielArtikelVarianteEntity(),
				ArtikelEntityConverter.convertVariante(beispielArtikelVariante()));
	}
}
