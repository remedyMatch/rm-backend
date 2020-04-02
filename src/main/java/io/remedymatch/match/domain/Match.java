package io.remedymatch.match.domain;

import java.math.BigDecimal;
import java.util.UUID;

import io.remedymatch.institution.domain.Institution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Match {
    private MatchId id;
    private UUID anfrageId;
    private Institution institutionVon;
    private MatchStandort standortVon;
    private Institution institutionAn;
    private MatchStandort standortAn;
    private String kommentar;
    private String prozessInstanzId;
    private MatchStatus status;
    private BigDecimal entfernung;
}
