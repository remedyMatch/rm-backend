package io.remedymatch.match.api;

import io.remedymatch.institution.api.InstitutionDTO;
import io.remedymatch.match.domain.MatchStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MatchDTO {

    private UUID id;
    private InstitutionDTO institutionVon;
    private String adresseVon;
    private InstitutionDTO institutionAn;
    private UUID anfrageId;
    private MatchStatus status;
    private String prozessInstanzId;

}
