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
                .artikelKategorie(ArtikelKategorieMapper.getArtikelKategorieEntity(artikel.getArtikelKategorie()))
                .build();
    }

    public static ArtikelDTO getArticleDTO(ArtikelEntity article) {
        return ArtikelDTO.builder()
                .id(article.getId())
                .beschreibung(article.getBeschreibung())
                .ean(article.getEan())
                .hersteller(article.getHersteller())
                .name(article.getName())
                .artikelKategorie(
                        ArtikelKategorieMapper.getArtikelKategorieDTO(article.getArtikelKategorie())
                )
                .build();
    }
}
