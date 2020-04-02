package io.remedymatch.bedarf.api;

import io.remedymatch.artikel.domain.ArtikelId;
import io.remedymatch.bedarf.domain.Bedarf;
import io.remedymatch.bedarf.domain.BedarfId;
import io.remedymatch.bedarf.domain.NeuesBedarf;
import io.remedymatch.institution.api.InstitutionStandortMapper;
import io.remedymatch.institution.domain.InstitutionStandortId;
import org.springframework.util.Assert;

import java.util.UUID;

import static io.remedymatch.artikel.api.ArtikelMapper.getArtikel;
import static io.remedymatch.artikel.api.ArtikelMapper.getArtikelDTO;

public class BedarfMapper {

    public static BedarfDTO mapToDTO(Bedarf bedarf) {
        if (bedarf == null) {
            return null;
        }

        var builder = BedarfDTO.builder()
                .id(bedarf.getId().getValue())
                .rest(bedarf.getRest())
                .anzahl(bedarf.getAnzahl())
                .artikel(getArtikelDTO(bedarf.getArtikel()))
                .medizinisch(bedarf.isMedizinisch())
                .originalverpackt(bedarf.isOriginalverpackt())
                .standort(bedarf.getStandort() != null ? InstitutionStandortMapper.mapToDTO(bedarf.getStandort()) : null)
                .steril(bedarf.isSteril())
                .kommentar(bedarf.getKommentar())
                .entfernung(bedarf.getEntfernung())
                .bedient(bedarf.isBedient());

        if (bedarf.getInstitution() != null) {
            builder = builder.institutionId(bedarf.getInstitution().getId().getValue());
        }

        return builder.build();
    }

    static NeuesBedarf mapToNeuesBedarf(final NeuesBedarfRequest neuesBedarfRequest) {
        Assert.notNull(neuesBedarfRequest, "NeuesBedarfRequest ist null");

        return NeuesBedarf.builder() //
                .anzahl(neuesBedarfRequest.getAnzahl()) //
                .artikelId(new ArtikelId(neuesBedarfRequest.getArtikelId())) //
                .standortId(new InstitutionStandortId(neuesBedarfRequest.getStandortId())) //
                .steril(neuesBedarfRequest.isSteril()) //
                .originalverpackt(neuesBedarfRequest.isOriginalverpackt()) //
                .medizinisch(neuesBedarfRequest.isMedizinisch()) //
                .kommentar(neuesBedarfRequest.getKommentar()) //
                .build();
    }

    static Bedarf mapToBedarf(BedarfDTO dto) {
        if (dto == null) {
            return null;
        }

        var builder = Bedarf.builder()
                .id(maptToBedarfId(dto.getId()))
                .rest(dto.getRest())
                .anzahl(dto.getAnzahl())
                .artikel(getArtikel(dto.getArtikel()))
                .medizinisch(dto.isMedizinisch())
                .originalverpackt(dto.isOriginalverpackt())
                .standort(dto.getStandort() != null ? InstitutionStandortMapper.mapToStandort(dto.getStandort()) : null)
                .steril(dto.isSteril())
                .kommentar(dto.getKommentar())
                .bedient(dto.isBedient());
        return builder.build();
    }


    static BedarfId maptToBedarfId(final String bedarfId) {
        return maptToBedarfId(UUID.fromString(bedarfId));
    }

    static BedarfId maptToBedarfId(final UUID bedarfId) {
        return new BedarfId(bedarfId);
    }
}