package io.remedymatch.artikel.api;

import java.util.UUID;

import io.remedymatch.artikel.domain.ArtikelKategorie;
import io.remedymatch.artikel.domain.ArtikelKategorieId;

public class ArtikelKategorieMapper {
	public static ArtikelKategorie getArtikelKategorie(ArtikelKategorieDTO dto) {
		return ArtikelKategorie.builder()//
				.id(maptToArtikelKategorieId(dto.getId()))//
				.name(dto.getName())//
				.build();
	}

	public static ArtikelKategorieDTO getArtikelKategorieDTO(ArtikelKategorie artikelKategorie) {
		return ArtikelKategorieDTO.builder() //
				.id(artikelKategorie.getId().getValue())//
				.name(artikelKategorie.getName())//
				.build();
	}
	
	static ArtikelKategorieId maptToArtikelKategorieId(final String artikelKategorieId)
    {
    	return maptToArtikelKategorieId(UUID.fromString(artikelKategorieId));
    }
    
    static ArtikelKategorieId maptToArtikelKategorieId(final UUID artikelKategorieId)
    {
    	return new ArtikelKategorieId(artikelKategorieId);
    }
}
