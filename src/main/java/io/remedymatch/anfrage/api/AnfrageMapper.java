package io.remedymatch.anfrage.api;

import io.remedymatch.anfrage.domain.AnfrageEntity;

public class AnfrageMapper {

    public static AnfrageDTO mapToDTO(AnfrageEntity entity) {
        return AnfrageDTO.builder()
                .id(entity.getId())
                .kommentar(entity.getKommentar())
                .institutionAn(entity.getInstitutionAn().getId())
                .institutionVon(entity.getInstitutionVon().getId())
                .bedarf(entity.getBedarf().getId())
                .angebot(entity.getAngebot().getId())
                .storniert(entity.isStorniert())
                .standortAn(entity.getStandortAn())
                .standortVon(entity.getStandortVon())
                .angenommen(entity.isAngenommen())
                .build();
    }

}
