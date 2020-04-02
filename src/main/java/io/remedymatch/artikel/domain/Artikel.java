package io.remedymatch.artikel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Artikel {
    private ArtikelId id;
    private String ean;
    private String name;
    private String beschreibung;
    private String hersteller;
    private ArtikelKategorie artikelKategorie;
}