package io.remedymatch.match.domain;

import io.remedymatch.institution.domain.Institution;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

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
    private UUID aritkelId;
    private UUID aritkelVarianteId;
    private BigDecimal anzahl;
    private String anfrageTyp;
    private Institution institutionVon;
    private MatchStandort standortVon;
    private Institution institutionAn;
    private MatchStandort standortAn;
    private String kommentar;
    private String prozessInstanzId;
    private MatchStatus status;
    private BigDecimal entfernung;
}
