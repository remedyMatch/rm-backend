package io.remedymatch.artikel.api;

import io.remedymatch.artikel.domain.ArtikelEntity;

public class ArtikelMapper {
    public static ArtikelEntity getArticleEntity(ArtikelDTO artikel) {
        return ArtikelEntity.builder()
                .id(artikel.getId())
                .beschreibung(artikel.getBeschreibung())
                .ean(artikel.getEan())
                .hersteller(artikel.getHersteller())
                .name(artikel.getName())
                .build();
    }

    public static io.remedymatch.artikel.api.ArtikelDTO getArticleDTO(ArtikelEntity article) {
        return io.remedymatch.artikel.api.ArtikelDTO.builder()
                .id(article.getId())
                .beschreibung(article.getBeschreibung())
                .ean(article.getEan())
                .hersteller(article.getHersteller())
                .name(article.getName())
                .build();
    }
}
