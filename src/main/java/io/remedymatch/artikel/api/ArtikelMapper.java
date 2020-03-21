package io.remedymatch.artikel.api;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

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

    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @Builder
    public static class ArtikelEntity {

        @Id
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        @Column(name = "id", nullable = false, updatable = false)
        private UUID id;

        @Column(name = "EAN", nullable = false, updatable = false)
        private String ean;

        @Column(name = "NAME", nullable = false, updatable = true)
        private String name;

        @Column(name = "description", nullable = false, updatable = true)
        private String beschreibung;

        @Column(name = "manufacturer", nullable = false, updatable = true)
        private String hersteller;
    }
}
