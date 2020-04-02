package io.remedymatch.match.api;

import java.math.BigDecimal;
import java.util.UUID;

import io.remedymatch.institution.api.InstitutionDTO;
import io.remedymatch.match.domain.MatchStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchDTO {

	private UUID id;

	private InstitutionDTO institutionVon;

	private MatchStandortDTO standortVon;

	private MatchStandortDTO standortAn;

	private InstitutionDTO institutionAn;

	private UUID anfrageId;

	private MatchStatus status;

	private String prozessInstanzId;

	private BigDecimal entfernung;
}
