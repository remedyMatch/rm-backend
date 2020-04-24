package io.remedymatch.match.api;

import java.math.BigDecimal;
import java.util.UUID;

import io.remedymatch.institution.controller.InstitutionRO;
import io.remedymatch.match.domain.MatchStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchDTO {

	private UUID id;

	private InstitutionRO institutionVon;

	private MatchStandortRO standortVon;

	private MatchStandortRO standortAn;

	private InstitutionRO institutionAn;

	private String kommentar;

	private UUID anfrageId;

	private MatchStatus status;

	private String prozessInstanzId;

	private BigDecimal entfernung;

	private UUID artikelId;

	private UUID artikelVarianteId;

	private BigDecimal anzahl;

	private String anfrageTyp;
}
