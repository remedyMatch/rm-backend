package io.remedymatch.bedarf.domain.model;

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class NeuerBedarf {

    @NotNull
    @Positive
    private BigDecimal anzahl;

    @Valid
    private ArtikelId artikelId;

    @Valid
    private ArtikelVarianteId artikelVarianteId;

    private InstitutionStandortId standortId;

    private boolean steril;

    private boolean medizinisch;

    private boolean oeffentlich;

    @NotBlank
    private String kommentar;
}
