package io.remedymatch.anfrage.api;

import io.remedymatch.anfrage.domain.AnfrageEntity;
import io.remedymatch.institution.api.InstitutionMapper;

public class AnfrageMapper {

    public static AnfrageDTO mapToDTO(AnfrageEntity entity) {
        return AnfrageDTO.builder()
                .id(entity.getId())
                .kommentar(entity.getKommentar())
                .institutionAn(InstitutionMapper.mapToDTO(entity.getInstitutionAn()))
                .institutionVon(InstitutionMapper.mapToDTO(entity.getInstitutionVon()))
                .bedarfId(entity.getBedarf() != null ? entity.getBedarf().getId() : null)
                .angebotId(entity.getAngebot() != null ? entity.getAngebot().getId() : null)
                .storniert(entity.isStorniert())
                .standortAn(entity.getStandortAn())
                .standortVon(entity.getStandortVon())
                .angenommen(entity.isAngenommen())
                .build();
    }

}
