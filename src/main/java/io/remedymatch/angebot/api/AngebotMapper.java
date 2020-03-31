package io.remedymatch.angebot.api;

import static io.remedymatch.artikel.api.ArtikelMapper.getArticleDTO;
import static io.remedymatch.artikel.api.ArtikelMapper.getArticleEntity;

import java.util.UUID;

import io.remedymatch.angebot.domain.Angebot;
import io.remedymatch.angebot.domain.AngebotId;
import io.remedymatch.institution.api.InstitutionStandortMapper;

class AngebotMapper {

    static AngebotDTO mapToDto(final Angebot angebot) {
    	if (angebot == null)
    	{
    		return null;
    	}
    	
        var builder = AngebotDTO.builder()
                .id(angebot.getId().getValue())
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

    static Angebot mapToAngebot(final AngebotDTO dto) {
    	if (dto == null)
    	{
    		return null;
    	}
    	
        var builder = Angebot.builder()
                .id(maptToAngebotId(dto.getId()))
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
    
    static AngebotId maptToAngebotId(final String angebotId)
    {
    	return maptToAngebotId(UUID.fromString(angebotId));
    }
    
    static AngebotId maptToAngebotId(final UUID angebotId)
    {
    	return new AngebotId(angebotId);
    }
}