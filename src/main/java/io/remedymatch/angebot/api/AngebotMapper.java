package io.remedymatch.angebot.api;

import io.remedymatch.angebot.domain.Angebot;
import io.remedymatch.angebot.domain.AngebotId;
import io.remedymatch.angebot.domain.NeuesAngebot;
import io.remedymatch.artikel.domain.ArtikelId;
import io.remedymatch.institution.api.InstitutionStandortMapper;
import io.remedymatch.institution.domain.InstitutionStandortId;
import org.springframework.util.Assert;

import java.util.UUID;

import static io.remedymatch.artikel.api.ArtikelMapper.getArtikel;
import static io.remedymatch.artikel.api.ArtikelMapper.getArtikelDTO;

public class AngebotMapper {

    public static AngebotDTO mapToDto(final Angebot angebot) {
        if (angebot == null) {
            return null;
        }

        var builder = AngebotDTO.builder()
                .id(angebot.getId().getValue())
                .rest(angebot.getRest())
                .anzahl(angebot.getAnzahl())
                .artikel(getArtikelDTO(angebot.getArtikel()))
                .haltbarkeit(angebot.getHaltbarkeit())
                .medizinisch(angebot.isMedizinisch())
                .originalverpackt(angebot.isOriginalverpackt())
                .standort(angebot.getStandort() != null ? InstitutionStandortMapper.mapToDTO(angebot.getStandort()) : null)
                .steril(angebot.isSteril())
                .bedient(angebot.isBedient())
                .entfernung(angebot.getEntfernung())
                .kommentar(angebot.getKommentar());

        if (angebot.getInstitution() != null) {
            builder = builder.institutionId(angebot.getInstitution().getId().getValue());
        }
        return builder.build();
    }

    static NeuesAngebot mapToNeueAngebot(final NeuesAngebotRequest neueAngebotRequest) {
        Assert.notNull(neueAngebotRequest, "NeueAngebotRequest ist null");

        return NeuesAngebot.builder()
                .anzahl(neueAngebotRequest.getAnzahl())
                .artikelId(new ArtikelId(neueAngebotRequest.getArtikelId()))
                .standortId(new InstitutionStandortId(neueAngebotRequest.getStandortId()))
                .haltbarkeit(neueAngebotRequest.getHaltbarkeit())
                .steril(neueAngebotRequest.isSteril())
                .originalverpackt(neueAngebotRequest.isOriginalverpackt())
                .medizinisch(neueAngebotRequest.isMedizinisch())
                .kommentar(neueAngebotRequest.getKommentar())
                .build();
    }

    static Angebot mapToAngebot(final AngebotDTO dto) {
        if (dto == null) {
            return null;
        }

        var builder = Angebot.builder()
                .id(maptToAngebotId(dto.getId()))
                .rest(dto.getRest())
                .anzahl(dto.getAnzahl())
                .artikel(getArtikel(dto.getArtikel()))
                .haltbarkeit(dto.getHaltbarkeit())
                .medizinisch(dto.isMedizinisch())
                .originalverpackt(dto.isOriginalverpackt())
                .standort(dto.getStandort() != null ? InstitutionStandortMapper.mapToStandort(dto.getStandort()) : null)
                .steril(dto.isSteril())
                .bedient(dto.isBedient())
                .kommentar(dto.getKommentar());
        return builder.build();
    }

    static AngebotId maptToAngebotId(final String angebotId) {
        return maptToAngebotId(UUID.fromString(angebotId));
    }

    static AngebotId maptToAngebotId(final UUID angebotId) {
        return new AngebotId(angebotId);
    }
}