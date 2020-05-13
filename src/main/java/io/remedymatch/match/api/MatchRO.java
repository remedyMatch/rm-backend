package io.remedymatch.match.api;

import io.remedymatch.institution.controller.InstitutionRO;
import io.remedymatch.match.domain.MatchStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class MatchRO {

    private UUID id;

    private InstitutionRO institutionVon;

    private MatchStandortRO standortVon;

    private MatchStandortRO standortAn;

    private InstitutionRO institutionAn;

    private UUID anfrageId;

    private UUID inseratId;

    private MatchStatus status;

    private BigDecimal entfernung;

    private UUID artikelId;

    private UUID artikelVarianteId;

    private UUID artikelKategorieId;

    private BigDecimal anzahl;

    private String anfrageTyp;
}
