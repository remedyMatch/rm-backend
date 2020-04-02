package io.remedymatch.artikel.api;

import java.util.UUID;

import io.remedymatch.artikel.domain.Artikel;
import io.remedymatch.artikel.domain.ArtikelId;

public class ArtikelMapper {
    public static Artikel getArtikel(ArtikelDTO artikel) {
        var builder = Artikel.builder() //
        		.id(maptToArtikelId(artikel.getId()))//
				.beschreibung(artikel.getBeschreibung()) //
                .ean(artikel.getEan()) //
                .hersteller(artikel.getHersteller()) //
                .name(artikel.getName());

        if (artikel.getArtikelKategorie() != null) {
            builder = builder.artikelKategorie(ArtikelKategorieMapper.getArtikelKategorie(artikel.getArtikelKategorie()));
        }

        return builder.build();
    }

    public static ArtikelDTO getArtikelDTO(Artikel artikel) {
        var builder = ArtikelDTO.builder() //
                .id(artikel.getId().getValue()) //
                .beschreibung(artikel.getBeschreibung()) //
                .ean(artikel.getEan()) //
                .hersteller(artikel.getHersteller()) //
                .name(artikel.getName());

        if (artikel.getArtikelKategorie() != null) {
            builder = builder.artikelKategorie(ArtikelKategorieMapper.getArtikelKategorieDTO(artikel.getArtikelKategorie()));
        }

        return builder.build();
    }
    
	static ArtikelId maptToArtikelId(final String artikelId)
    {
    	return maptToArtikelId(UUID.fromString(artikelId));
    }
    
    static ArtikelId maptToArtikelId(final UUID artikelId)
    {
    	return new ArtikelId(artikelId);
    }
}
