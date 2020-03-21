package io.remedymatch.article.domain;

import io.remedymatch.article.infrastructure.ArticleEntity;

public class ArtikelMapper {
    public static ArticleEntity getArticleEntity(Artikel artikel) {
        return ArticleEntity.builder()
                .id(artikel.getId())
                .beschreibung(artikel.getBeschreibung())
                .ean(artikel.getEan())
                .hersteller(artikel.getHersteller())
                .name(artikel.getName())
                .build();
    }

    public static Artikel getArticle(ArticleEntity article) {
        return Artikel.builder()
                .id(article.getId())
                .beschreibung(article.getBeschreibung())
                .ean(article.getEan())
                .hersteller(article.getHersteller())
                .name(article.getName())
                .build();
    }
}
