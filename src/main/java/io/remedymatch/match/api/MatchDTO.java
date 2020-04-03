package io.remedymatch.match.api;

import io.remedymatch.institution.api.InstitutionDTO;
import io.remedymatch.match.domain.MatchStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class MatchDTO {

    private UUID id;

    private InstitutionDTO institutionVon;

    private MatchStandortDTO standortVon;

    private MatchStandortDTO standortAn;

    private InstitutionDTO institutionAn;

    private String kommentar;

    private UUID anfrageId;

    private MatchStatus status;

    private String prozessInstanzId;

    private BigDecimal entfernung;

    private UUID aritkelId;

    private BigDecimal anzahl;

    private String anfrageTyp;
}
