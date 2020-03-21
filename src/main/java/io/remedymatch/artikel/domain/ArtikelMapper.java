package io.remedymatch.artikel.domain;

import io.remedymatch.artikel.infrastructure.ArtikelEntity;

public class ArtikelMapper {
    public static ArtikelEntity getArticleEntity(Artikel artikel) {
        return ArtikelEntity.builder()
                .id(artikel.getId())
                .beschreibung(artikel.getBeschreibung())
                .ean(artikel.getEan())
                .hersteller(artikel.getHersteller())
                .name(artikel.getName())
                .build();
    }

    public static Artikel getArticle(ArtikelEntity article) {
        return Artikel.builder()
                .id(article.getId())
                .beschreibung(article.getBeschreibung())
                .ean(article.getEan())
                .hersteller(article.getHersteller())
                .name(article.getName())
                .build();
    }
}
