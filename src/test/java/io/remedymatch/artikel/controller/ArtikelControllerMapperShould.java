package io.remedymatch.artikel.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.artikel.domain.model.Artikel;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelKategorie;
import io.remedymatch.artikel.domain.model.ArtikelKategorieId;
import io.remedymatch.artikel.domain.model.ArtikelVariante;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;

@ExtendWith(SpringExtension.class)
@DisplayName("ArtikelControllerMapper soll")
public class ArtikelControllerMapperShould {
	public static final ArtikelKategorieId ARTIKEL_KATEGORIE_ID = new ArtikelKategorieId(UUID.randomUUID());
	public static final String ARTIKEL_KATEGORIE_NAME = "Artikel Kategorie Name";
	public static final String ARTIKEL_KATEGORIE_ICON = "Artikel Kategorie Icon";

	public static final ArtikelId ARTIKEL_ID = new ArtikelId(UUID.randomUUID());
	public static final String ARTIKEL_NAME = "Artikel Name";
	public static final String ARTIKEL_BESCHREIBUNG = "Artikel Beschreibung";

	public static final ArtikelVarianteId ARTIKEL_VARIANTE_ID = new ArtikelVarianteId(UUID.randomUUID());
	public static final String ARTIKEL_VARIANTE_NAME = "Artikel Variante Name";
	public static final String ARTIKEL_VARIANTE_NORM = "Artikel Variante Norm";
	public static final String ARTIKEL_VARIANTE_BESCHREIBUNG = "Artikel Variante Beschreibung";
	public static final boolean ARTIKEL_VARIANTE_MEDIZINISCH_AUSWAEHLBAR = true;

	@Test
	@DisplayName("fuer Artikel Kategorie eine leere Liste der Domain Objekte in leere Liste der ROs konvertieren")
	void fuer_Artikel_Kategorie_eine_leere_Liste_der_Domain_Objekte_in_leere_Liste_der_ROs_konvertieren() {
		assertEquals(Collections.emptyList(), ArtikelControllerMapper.mapKategorienToRO(Collections.emptyList()));
	}

	@Test
	@DisplayName("fuer Artikel Kategorie leere Liste der Domain Objekte in Liste der ROs konvertieren")
	void fuer_Artikel_Kategorie_Liste_der_Domain_Objekte_in_Liste_der_ROs_konvertieren() {
		assertEquals(Arrays.asList(artikelKategorieRO()),
				ArtikelControllerMapper.mapKategorienToRO(Arrays.asList(artikelKategorie())));
	}

	@Test
	@DisplayName("fuer Artikel Kategorie ein Domain Objekt in ein RO konvertieren")
	void fuer_Artikel_Kategorie_ein_Domain_Objekt_in_ein_RO_konvertieren() {
		assertEquals(artikelKategorieRO(), ArtikelControllerMapper.mapKategorieToRO(artikelKategorie()));
	}

	@Test
	@DisplayName("fuer Artikel eine leere Liste der Domain Objekte in leere Liste der ROs konvertieren")
	void fuer_Artikel_eine_leere_Liste_der_Domain_Objekte_in_leere_Liste_der_ROs_konvertieren() {
		assertEquals(Collections.emptyList(), ArtikelControllerMapper.mapArtikelToRO(Collections.emptyList()));
	}

	@Test
	@DisplayName("fuer Artikel leere Liste der Domain Objekte in Liste der ROs konvertieren")
	void fuer_Artikel_Liste_der_Domain_Objekte_in_Liste_der_ROs_konvertieren() {
		assertEquals(Arrays.asList(artikelRO()), ArtikelControllerMapper.mapArtikelToRO(Arrays.asList(artikel())));
	}

	@Test
	@DisplayName("fuer Artikel ein Domain Objekt in ein RO konvertieren")
	void fuer_Artikel_ein_Domain_Objekt_in_ein_RO_konvertieren() {
		assertEquals(artikelRO(), ArtikelControllerMapper.mapArtikelToRO(artikel()));
	}

	@Test
	@DisplayName("UUID in ArtikelId konvertieren")
	void uuid_in_AngebotId_konvertieren() {
		assertEquals(ARTIKEL_ID, ArtikelControllerMapper.maptToArtikelId(ARTIKEL_ID.getValue()));
	}

	@Test
	@DisplayName("fuer ArtikelVarianten eine leere Liste der Domain Objekte in leere Liste der ROs konvertieren")
	void fuer_ArtikelVarianten_eine_leere_Liste_der_Domain_Objekte_in_leere_Liste_der_ROs_konvertieren() {
		assertEquals(Collections.emptyList(), ArtikelControllerMapper.mapVariantenToRO(Collections.emptyList()));
	}

	@Test
	@DisplayName("fuer ArtikelVarianten leere Liste der Domain Objekte in Liste der ROs konvertieren")
	void fuer_ArtikelVarianten_eine_Liste_der_Domain_Objekte_in_Liste_der_ROs_konvertieren() {
		assertEquals(Arrays.asList(artikelVarianteRO()),
				ArtikelControllerMapper.mapVariantenToRO(Arrays.asList(artikelVariante())));
	}

	@Test
	@DisplayName("fuer ArtikelVariante eine Entity in ein Domain Objekt konvertieren")
	void fuer_ArtikelVariante_eine_Entity_in_ein_Domain_Objekt_konvertieren() {
		assertEquals(artikelVarianteRO(), ArtikelControllerMapper.mapVarianteToRO(artikelVariante()));
	}

	private ArtikelKategorie artikelKategorie() {
		return ArtikelKategorie.builder() //
				.id(ARTIKEL_KATEGORIE_ID) //
				.name(ARTIKEL_KATEGORIE_NAME) //
				.icon(ARTIKEL_KATEGORIE_ICON) //
				.build();
	}

	private ArtikelKategorieRO artikelKategorieRO() {
		return ArtikelKategorieRO.builder() //
				.id(ARTIKEL_KATEGORIE_ID.getValue()) //
				.name(ARTIKEL_KATEGORIE_NAME) //
				.icon(ARTIKEL_KATEGORIE_ICON) //
				.build();
	}

	private Artikel artikel() {
		return Artikel.builder() //
				.id(ARTIKEL_ID) //
				.artikelKategorieId(ARTIKEL_KATEGORIE_ID) //
				.name(ARTIKEL_NAME) //
				.beschreibung(ARTIKEL_BESCHREIBUNG) //
				.varianten(new ArrayList<>(Arrays.asList(artikelVariante()))) //
				.build();
	}

	private ArtikelRO artikelRO() {
		return ArtikelRO.builder() //
				.id(ARTIKEL_ID.getValue()) //
				.artikelKategorieId(ARTIKEL_KATEGORIE_ID.getValue()) //
				.name(ARTIKEL_NAME) //
				.beschreibung(ARTIKEL_BESCHREIBUNG) //
				.varianten(new ArrayList<>(Arrays.asList(artikelVarianteRO()))) //
				.build();
	}

	private ArtikelVariante artikelVariante() {
		return ArtikelVariante.builder() //
				.id(ARTIKEL_VARIANTE_ID) //
				.artikelId(ARTIKEL_ID) //
				.variante(ARTIKEL_VARIANTE_NAME) //
				.variante(ARTIKEL_VARIANTE_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}

	private ArtikelVarianteRO artikelVarianteRO() {
		return ArtikelVarianteRO.builder() //
				.id(ARTIKEL_VARIANTE_ID.getValue()) //
				.artikelId(ARTIKEL_ID.getValue()) //
				.variante(ARTIKEL_VARIANTE_NAME) //
				.variante(ARTIKEL_VARIANTE_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}

}
