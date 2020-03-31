package io.remedymatch.institution.api;

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

    private InstitutionStandortDTO standortAn;

    private InstitutionStandortDTO standortVon;

    private String prozessInstanzId;

    private double anzahl;

    private String status;

}
