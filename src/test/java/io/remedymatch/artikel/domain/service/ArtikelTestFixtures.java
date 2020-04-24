package io.remedymatch.artikel.domain.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import io.remedymatch.artikel.domain.model.Artikel;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelKategorie;
import io.remedymatch.artikel.domain.model.ArtikelKategorieId;
import io.remedymatch.artikel.domain.model.ArtikelVariante;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;

public final class ArtikelTestFixtures {
	public static final ArtikelKategorieId ARTIKEL_KATEGORIE_ID = new ArtikelKategorieId(UUID.randomUUID());
	public static final String ARTIKEL_KATEGORIE_NAME = "Artikel Kategorie Name";
	public static final String ARTIKEL_KATEGORIE_ICON = "Artikel Kategorie Icon";
	
	public static final ArtikelKategorieId ARTIKEL_KATEGORIE_1_ID = new ArtikelKategorieId(UUID.randomUUID());
	public static final String ARTIKEL_KATEGORIE_1_NAME = "Artikel Kategorie 1 Name";
	public static final String ARTIKEL_KATEGORIE_1_ICON = "Artikel Kategorie 1 Icon";
	
	public static final ArtikelKategorieId ARTIKEL_KATEGORIE_2_ID = new ArtikelKategorieId(UUID.randomUUID());
	public static final String ARTIKEL_KATEGORIE_2_NAME = "Artikel Kategorie 2 Name";
	public static final String ARTIKEL_KATEGORIE_2_ICON = "Artikel Kategorie 2 Icon";

	public static ArtikelKategorieId beispielArtikelKategorieId() {
		return ARTIKEL_KATEGORIE_ID;
	}
	
	public static ArtikelKategorie beispielArtikelKategorie() {
		return ArtikelKategorie.builder() //
				.id(beispielArtikelKategorieId()) //
				.name(ARTIKEL_KATEGORIE_NAME) //
				.icon(ARTIKEL_KATEGORIE_ICON) //
				.build();
	}

	public static ArtikelKategorieEntity beispielArtikelKategorieEntity() {
		return ArtikelKategorieEntity.builder() //
				.id(beispielArtikelKategorieId().getValue()) //
				.name(ARTIKEL_KATEGORIE_NAME) //
				.icon(ARTIKEL_KATEGORIE_ICON) //
				.build();
	}
	
	public static ArtikelKategorieId beispielArtikelKategorie1Id() {
		return ARTIKEL_KATEGORIE_1_ID;
	}
	
	public static ArtikelKategorie beispielArtikelKategorie1() {
		return ArtikelKategorie.builder() //
				.id(beispielArtikelKategorie1Id()) //
				.name(ARTIKEL_KATEGORIE_1_NAME) //
				.icon(ARTIKEL_KATEGORIE_1_ICON) //
				.build();
	}

	public static ArtikelKategorieEntity beispielArtikelKategorie1Entity() {
		return ArtikelKategorieEntity.builder() //
				.id(beispielArtikelKategorie1Id().getValue()) //
				.name(ARTIKEL_KATEGORIE_1_NAME) //
				.icon(ARTIKEL_KATEGORIE_1_ICON) //
				.build();
	}
	
	public static ArtikelKategorieId beispielArtikelKategorie2Id() {
		return ARTIKEL_KATEGORIE_2_ID;
	}
	
	public static ArtikelKategorie beispielArtikelKategorie2() {
		return ArtikelKategorie.builder() //
				.id(beispielArtikelKategorie2Id()) //
				.name(ARTIKEL_KATEGORIE_2_NAME) //
				.icon(ARTIKEL_KATEGORIE_2_ICON) //
				.build();
	}

	public static ArtikelKategorieEntity beispielArtikelKategorie2Entity() {
		return ArtikelKategorieEntity.builder() //
				.id(beispielArtikelKategorie2Id().getValue()) //
				.name(ARTIKEL_KATEGORIE_2_NAME) //
				.icon(ARTIKEL_KATEGORIE_2_ICON) //
				.build();
	}

	public static final ArtikelId ARTIKEL_ID = new ArtikelId(UUID.randomUUID());
	public static final String ARTIKEL_NAME = "Artikel Name";
	public static final String ARTIKEL_BESCHREIBUNG = "Artikel Beschreibung";

	public static final ArtikelId ARTIKEL_1_ID = new ArtikelId(UUID.randomUUID());
	public static final String ARTIKEL_1_NAME = "Artikel 1 Name";
	public static final String ARTIKEL_1_BESCHREIBUNG = "Artikel 1 Beschreibung";

	public static final ArtikelId ARTIKEL_2_ID = new ArtikelId(UUID.randomUUID());
	public static final String ARTIKEL_2_NAME = "Artikel 2 Name";
	public static final String ARTIKEL_2_BESCHREIBUNG = "Artikel 2 Beschreibung";

	
	public static ArtikelId beispielArtikelId() {
		return ARTIKEL_ID;
	}
	
	public static Artikel beispielArtikel() {
		return Artikel.builder() //
				.id(beispielArtikelId()) //
				.artikelKategorieId(ARTIKEL_KATEGORIE_ID) //
				.name(ARTIKEL_NAME) //
				.beschreibung(ARTIKEL_BESCHREIBUNG) //
				.varianten(new ArrayList<>(Arrays.asList(beispielArtikelVariante1(), beispielArtikelVariante2()))) //
				.build();
	}

	public static ArtikelEntity beispielArtikelEntity() {
		return ArtikelEntity.builder() //
				.id(beispielArtikelId().getValue()) //
				.artikelKategorie(ARTIKEL_KATEGORIE_ID.getValue()) //
				.name(ARTIKEL_NAME) //
				.beschreibung(ARTIKEL_BESCHREIBUNG) //
				.varianten(new ArrayList<>(Arrays.asList(beispielArtikelVariante1Entity(), beispielArtikelVariante2Entity()))) //
				.build();
	}
	
	public static ArtikelId beispielArtikel1Id() {
		return ARTIKEL_1_ID;
	}
	
	public static Artikel beispielArtikel1() {
		return Artikel.builder() //
				.id(beispielArtikelId()) //
				.artikelKategorieId(ARTIKEL_KATEGORIE_ID) //
				.name(ARTIKEL_1_NAME) //
				.beschreibung(ARTIKEL_1_BESCHREIBUNG) //
				.varianten(new ArrayList<>(Arrays.asList(beispielArtikelVariante1(), beispielArtikelVariante2()))) //
				.build();
	}

	public static ArtikelEntity beispielArtikel1Entity() {
		return ArtikelEntity.builder() //
				.id(beispielArtikelId().getValue()) //
				.artikelKategorie(ARTIKEL_KATEGORIE_ID.getValue()) //
				.name(ARTIKEL_1_NAME) //
				.beschreibung(ARTIKEL_1_BESCHREIBUNG) //
				.varianten(new ArrayList<>(Arrays.asList(beispielArtikelVariante1Entity(), beispielArtikelVariante2Entity()))) //
				.build();
	}
	
	public static ArtikelId beispielArtikel2Id() {
		return ARTIKEL_2_ID;
	}
	
	public static Artikel beispielArtikel2() {
		return Artikel.builder() //
				.id(beispielArtikelId()) //
				.artikelKategorieId(ARTIKEL_KATEGORIE_ID) //
				.name(ARTIKEL_2_NAME) //
				.beschreibung(ARTIKEL_2_BESCHREIBUNG) //
				.varianten(new ArrayList<>(Arrays.asList(beispielArtikelVariante1(), beispielArtikelVariante2()))) //
				.build();
	}

	public static ArtikelEntity beispielArtikel2Entity() {
		return ArtikelEntity.builder() //
				.id(beispielArtikelId().getValue()) //
				.artikelKategorie(ARTIKEL_KATEGORIE_ID.getValue()) //
				.name(ARTIKEL_2_NAME) //
				.beschreibung(ARTIKEL_2_BESCHREIBUNG) //
				.varianten(new ArrayList<>(Arrays.asList(beispielArtikelVariante1Entity(), beispielArtikelVariante2Entity()))) //
				.build();
	}
	
	public static final ArtikelVarianteId ARTIKEL_VARIANTE_ID = new ArtikelVarianteId(UUID.randomUUID());
	public static final String ARTIKEL_VARIANTE_NAME = "Artikel Variante Name";
	public static final String ARTIKEL_VARIANTE_NORM = "Artikel Variante Norm";
	public static final String ARTIKEL_VARIANTE_BESCHREIBUNG = "Artikel Variante Beschreibung";
	public static final boolean ARTIKEL_VARIANTE_MEDIZINISCH_AUSWAEHLBAR = true;
	
	public static final ArtikelVarianteId ARTIKEL_VARIANTE_1_ID = new ArtikelVarianteId(UUID.randomUUID());
	public static final String ARTIKEL_VARIANTE_1_NAME = "Artikel Variante 1 Name";
	public static final String ARTIKEL_VARIANTE_1_NORM = "Artikel Variante 1 Norm";
	public static final String ARTIKEL_VARIANTE_1_BESCHREIBUNG = "Artikel Variante 1 Beschreibung";
	public static final boolean ARTIKEL_VARIANTE_1_MEDIZINISCH_AUSWAEHLBAR = true;
	
	public static final ArtikelVarianteId ARTIKEL_VARIANTE_2_ID = new ArtikelVarianteId(UUID.randomUUID());
	public static final String ARTIKEL_VARIANTE_2_NAME = "Artikel Variante 2 Name";
	public static final String ARTIKEL_VARIANTE_2_NORM = "Artikel Variante 2 Norm";
	public static final String ARTIKEL_VARIANTE_2_BESCHREIBUNG = "Artikel Variante 2 Beschreibung";
	public static final boolean ARTIKEL_VARIANTE_2_MEDIZINISCH_AUSWAEHLBAR = false;


	public static ArtikelVarianteId beispielArtikelVarianteId() {
		return ARTIKEL_VARIANTE_ID;
	}
	
	public static ArtikelVariante beispielArtikelVariante() {
		return ArtikelVariante.builder() //
				.id(beispielArtikelVarianteId()) //
				.artikelId(ARTIKEL_ID) //
				.variante(ARTIKEL_VARIANTE_NAME) //
				.variante(ARTIKEL_VARIANTE_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}
	
	public static ArtikelVarianteEntity beispielArtikelVarianteEntity() {
		return ArtikelVarianteEntity.builder() //
				.id(beispielArtikelVarianteId().getValue()) //
				.artikel(ARTIKEL_ID.getValue()) //
				.variante(ARTIKEL_VARIANTE_NAME) //
				.variante(ARTIKEL_VARIANTE_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}
	
	public static ArtikelVarianteId beispielArtikelVariante1Id() {
		return ARTIKEL_VARIANTE_1_ID;
	}
	
	public static ArtikelVariante beispielArtikelVariante1() {
		return ArtikelVariante.builder() //
				.id(beispielArtikelVariante1Id()) //
				.artikelId(ARTIKEL_ID) //
				.variante(ARTIKEL_VARIANTE_1_NAME) //
				.variante(ARTIKEL_VARIANTE_1_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_1_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_1_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}

	public static ArtikelVarianteEntity beispielArtikelVariante1Entity() {
		return ArtikelVarianteEntity.builder() //
				.id(beispielArtikelVariante1Id().getValue()) //
				.artikel(ARTIKEL_ID.getValue()) //
				.variante(ARTIKEL_VARIANTE_1_NAME) //
				.variante(ARTIKEL_VARIANTE_1_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_1_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_1_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}
	
	public static ArtikelVarianteId beispielArtikelVariante2Id() {
		return ARTIKEL_VARIANTE_2_ID;
	}
	
	public static ArtikelVariante beispielArtikelVariante2() {
		return ArtikelVariante.builder() //
				.id(beispielArtikelVariante2Id()) //
				.artikelId(ARTIKEL_ID) //
				.variante(ARTIKEL_VARIANTE_2_NAME) //
				.variante(ARTIKEL_VARIANTE_2_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_2_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_2_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}

	public static ArtikelVarianteEntity beispielArtikelVariante2Entity() {
		return ArtikelVarianteEntity.builder() //
				.id(beispielArtikelVariante2Id().getValue()) //
				.artikel(ARTIKEL_ID.getValue()) //
				.variante(ARTIKEL_VARIANTE_2_NAME) //
				.variante(ARTIKEL_VARIANTE_2_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_2_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_2_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}
}
