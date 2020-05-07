package io.remedymatch.angebot.domain.model;

import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @NotNull
    @Positive
    private BigDecimal anzahl;

    @NotNull
    @Valid
    private ArtikelVarianteId artikelVarianteId;

    private InstitutionStandortId standortId;

    private LocalDateTime haltbarkeit;

    private boolean steril;

    private boolean originalverpackt;

    private boolean medizinisch;

    private boolean oeffentlich;

    @NotBlank
    private String kommentar;
}
