package io.remedymatch.anfrage.api;

import io.remedymatch.anfrage.domain.AnfrageStatus;
import io.remedymatch.institution.api.InstitutionDTO;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AnfrageDTO {

    private UUID id;
    private String kommentar;
    private InstitutionDTO institutionVon;
    private InstitutionDTO institutionAn;
    private UUID bedarfId;
    private UUID angebotId;
    private String standortAn;
    private String standortVon;
    private String prozessInstanzId;
    private AnfrageStatus status;

}
