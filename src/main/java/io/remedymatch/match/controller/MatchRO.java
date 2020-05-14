package io.remedymatch.match.controller;

import io.remedymatch.artikel.controller.ArtikelRO;
import io.remedymatch.institution.controller.InstitutionRO;
import io.remedymatch.institution.controller.InstitutionStandortRO;
import io.remedymatch.match.domain.InseratTyp;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class MatchRO {

    private InstitutionRO institutionVon;

    private InstitutionStandortRO standortVon;

    private InstitutionStandortRO standortAn;

    private InstitutionRO institutionAn;

    private UUID anfrageId;

    private UUID inseratId;

    private BigDecimal entfernung;

    private ArtikelRO artikel;

    private UUID artikelVarianteId;

    private BigDecimal anzahl;

    private InseratTyp inseratTyp;
}
