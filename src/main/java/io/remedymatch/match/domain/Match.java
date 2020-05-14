package io.remedymatch.match.domain;

import io.remedymatch.artikel.domain.model.Artikel;
import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionStandort;
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

    private UUID anfrageId;

    private UUID inseratId;

    private Artikel artikel;

    private UUID artikelVarianteId;

    private BigDecimal anzahl;

    private String anfrageTyp;

    private Institution institutionVon;

    private InstitutionStandort standortVon;

    private Institution institutionAn;

    private InstitutionStandort standortAn;

    private BigDecimal entfernung;
}
