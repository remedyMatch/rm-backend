package io.remedymatch.artikel;

import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.artikel.domain.ArtikelKategorieEntity;

import java.util.UUID;

public class ArtikelFixtures {
    public static ArtikelKategorieEntity artikelKategorieEntity(String prefix) {
        return ArtikelKategorieEntity.builder()
                .name(prefixed(prefix, "name"))
                .build();
    }

    public static ArtikelEntity completeArtikelEntity(String prefix) {
        return ArtikelEntity.builder()
                .beschreibung(prefixed(prefix, "description"))
                .ean(prefixed(prefix, "ean"))
                .hersteller(prefixed(prefix, "hersteller"))
                .beschreibung(prefixed(prefix,"beschreibung"))
                .name(prefixed(prefix, "name"))
                .artikelKategorie(artikelKategorieEntity(prefix))
                .build();
    }

    public static ArtikelEntity completeArtikelEntity() {
        return completeArtikelEntity(UUID.randomUUID().toString());
    }

    public static String prefixed(String prefix, String name) {
        return prefix + "-" + name;
    }
}
