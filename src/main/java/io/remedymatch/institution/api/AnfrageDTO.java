package io.remedymatch.institution.api;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

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

    private double entfernung;

    private String prozessInstanzId;

    private BigDecimal anzahl;

    private String status;

}
