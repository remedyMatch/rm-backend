package io.remedymatch.article.domain;

import io.remedymatch.article.infrastructure.ArticleEntity;

public class ArticleMapper {
    public static ArticleEntity getArticleEntity(Article article) {
        return ArticleEntity.builder()
                .id(article.getId())
                .description(article.getDescription())
                .ean(article.getEan())
                .manufacturer(article.getManufacturer())
                .name(article.getName())
                .build();
    }

    public static Article getArticle(ArticleEntity article) {
        return Article.builder()
                .id(article.getId())
                .description(article.getDescription())
                .ean(article.getEan())
                .manufacturer(article.getManufacturer())
                .name(article.getName())
                .build();
    }
}
