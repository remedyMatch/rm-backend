package io.remedymatch.angebot.api;

import static io.remedymatch.artikel.api.ArtikelMapper.getArticleDTO;
import static io.remedymatch.artikel.api.ArtikelMapper.getArticleEntity;

import io.remedymatch.angebot.domain.Angebot;
import io.remedymatch.institution.api.InstitutionStandortMapper;

class AngebotMapper {

    static AngebotDTO mapToDto(Angebot angebot) {
    	if (angebot == null)
    	{
    		return null;
    	}
    	
        var builder = AngebotDTO.builder()
                .id(angebot.getId())
                .rest(angebot.getRest())
                .anzahl(angebot.getAnzahl())
                .artikel(getArticleDTO(angebot.getArtikel()))
                .haltbarkeit(angebot.getHaltbarkeit())
                .medizinisch(angebot.isMedizinisch())
                .originalverpackt(angebot.isOriginalverpackt())
                .standort(angebot.getStandort() != null ? InstitutionStandortMapper.mapToDTO(angebot.getStandort()) : null)
                .steril(angebot.isSteril())
                .bedient(angebot.isBedient())
                .kommentar(angebot.getKommentar());

        if (angebot.getInstitution() != null) {
            builder = builder.institutionId(angebot.getInstitution().getId());
        }
        return builder.build();
    }

    static Angebot mapToAngebot(AngebotDTO dto) {
    	if (dto == null)
    	{
    		return null;
    	}
    	
        var builder = Angebot.builder()
                .id(dto.getId())
                .rest(dto.getRest())
                .anzahl(dto.getAnzahl())
                .artikel(getArticleEntity(dto.getArtikel()))
                .haltbarkeit(dto.getHaltbarkeit())
                .medizinisch(dto.isMedizinisch())
                .originalverpackt(dto.isOriginalverpackt())
                .standort(dto.getStandort() != null ? InstitutionStandortMapper.mapToEntity(dto.getStandort()) : null)
                .steril(dto.isSteril())
                .bedient(dto.isBedient())
                .kommentar(dto.getKommentar());
        return builder.build();
    }
}