package io.remedymatch.institution.api;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class AnfrageDTO {

    private UUID id;

    private String kommentar;

    private InstitutionRO institutionVon;

    private InstitutionRO institutionAn;

    private UUID bedarfId;

    private UUID angebotId;

    private UUID artikelId;

    private InstitutionStandortRO standortAn;

    private InstitutionStandortRO standortVon;

    private BigDecimal entfernung;

    private String prozessInstanzId;

    private BigDecimal anzahl;

    private String status;

}
