package io.remedymatch.angebot.domain;

import io.remedymatch.artikel.domain.ArtikelId;
import io.remedymatch.institution.domain.InstitutionStandortId;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class NeuesAngebot {
    private BigDecimal anzahl;
    private ArtikelId artikelId;
    private InstitutionStandortId standortId;
    private LocalDateTime haltbarkeit;
    private boolean steril;
    private boolean originalverpackt;
    private boolean medizinisch;
    private String kommentar;
}
