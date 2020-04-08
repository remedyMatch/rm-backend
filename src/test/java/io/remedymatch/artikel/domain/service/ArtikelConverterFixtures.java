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

public final class ArtikelConverterFixtures {
	public static final ArtikelKategorieId ARTIKEL_KATEGORIE_ID = new ArtikelKategorieId(UUID.randomUUID());
	public static final String ARTIKEL_KATEGORIE_NAME = "Artikel Kategorie Name";
	public static final String ARTIKEL_KATEGORIE_ICON = "Artikel Kategorie Icon";

	public static ArtikelKategorie beispielArtikelKategorie() {
		return ArtikelKategorie.builder() //
				.id(ARTIKEL_KATEGORIE_ID) //
				.name(ARTIKEL_KATEGORIE_NAME) //
				.icon(ARTIKEL_KATEGORIE_ICON) //
				.build();
	}

	public static ArtikelKategorieEntity beispielArtikelKategorieEntity() {
		return ArtikelKategorieEntity.builder() //
				.id(ARTIKEL_KATEGORIE_ID.getValue()) //
				.name(ARTIKEL_KATEGORIE_NAME) //
				.icon(ARTIKEL_KATEGORIE_ICON) //
				.build();
	}

	public static final ArtikelId ARTIKEL_ID = new ArtikelId(UUID.randomUUID());
	public static final String ARTIKEL_NAME = "Artikel Name";
	public static final String ARTIKEL_BESCHREIBUNG = "Artikel Beschreibung";

	public static Artikel beispielArtikel() {
		return Artikel.builder() //
				.id(ARTIKEL_ID) //
				.artikelKategorieId(ARTIKEL_KATEGORIE_ID) //
				.name(ARTIKEL_NAME) //
				.beschreibung(ARTIKEL_BESCHREIBUNG) //
				.varianten(new ArrayList<>(Arrays.asList(beispielArtikelVariante1(), beispielArtikelVariante2()))) //
				.build();
	}

	public static ArtikelEntity beispielArtikelEntity() {
		return ArtikelEntity.builder() //
				.id(ARTIKEL_ID.getValue()) //
				.artikelKategorie(ARTIKEL_KATEGORIE_ID.getValue()) //
				.name(ARTIKEL_NAME) //
				.beschreibung(ARTIKEL_BESCHREIBUNG) //
				.varianten(new ArrayList<>(Arrays.asList(beispielArtikelVariante1Entity(), beispielArtikelVariante2Entity()))) //
				.build();
	}
	
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

	public static ArtikelVariante beispielArtikelVariante1() {
		return ArtikelVariante.builder() //
				.id(ARTIKEL_VARIANTE_1_ID) //
				.artikelId(ARTIKEL_ID) //
				.variante(ARTIKEL_VARIANTE_1_NAME) //
				.variante(ARTIKEL_VARIANTE_1_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_1_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_1_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}

	public static ArtikelVariante beispielArtikelVariante2() {
		return ArtikelVariante.builder() //
				.id(ARTIKEL_VARIANTE_2_ID) //
				.artikelId(ARTIKEL_ID) //
				.variante(ARTIKEL_VARIANTE_2_NAME) //
				.variante(ARTIKEL_VARIANTE_2_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_2_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_2_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}

	public static ArtikelVarianteEntity beispielArtikelVariante1Entity() {
		return ArtikelVarianteEntity.builder() //
				.id(ARTIKEL_VARIANTE_1_ID.getValue()) //
				.artikel(ARTIKEL_ID.getValue()) //
				.variante(ARTIKEL_VARIANTE_1_NAME) //
				.variante(ARTIKEL_VARIANTE_1_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_1_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_1_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}
	
	public static ArtikelVarianteEntity beispielArtikelVariante2Entity() {
		return ArtikelVarianteEntity.builder() //
				.id(ARTIKEL_VARIANTE_2_ID.getValue()) //
				.artikel(ARTIKEL_ID.getValue()) //
				.variante(ARTIKEL_VARIANTE_2_NAME) //
				.variante(ARTIKEL_VARIANTE_2_NORM) //
				.beschreibung(ARTIKEL_VARIANTE_2_BESCHREIBUNG) //
				.medizinischAuswaehlbar(ARTIKEL_VARIANTE_2_MEDIZINISCH_AUSWAEHLBAR) //
				.build();
	}
}
