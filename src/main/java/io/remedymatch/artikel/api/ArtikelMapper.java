package io.remedymatch.artikel.api;

import io.remedymatch.artikel.domain.ArtikelEntity;

public class ArtikelMapper {
    public static ArtikelEntity getArticleEntity(ArtikelDTO artikel) {
        var builder = ArtikelEntity.builder()
                .id(artikel.getId())
                .beschreibung(artikel.getBeschreibung())
                .ean(artikel.getEan())
                .hersteller(artikel.getHersteller())
                .name(artikel.getName());

        if (artikel.getArtikelKategorie() != null) {
            builder = builder.artikelKategorie(ArtikelKategorieMapper.getArtikelKategorieEntity(artikel.getArtikelKategorie()));
        }

        return builder.build();
    }

    public static ArtikelDTO getArticleDTO(ArtikelEntity article) {
        var builder = ArtikelDTO.builder()
                .id(article.getId())
                .beschreibung(article.getBeschreibung())
                .ean(article.getEan())
                .hersteller(article.getHersteller())
                .name(article.getName());

        if (article.getArtikelKategorie() != null) {
            builder = builder.artikelKategorie(ArtikelKategorieMapper.getArtikelKategorieDTO(article.getArtikelKategorie()));
        }

        return builder.build();
    }
}
