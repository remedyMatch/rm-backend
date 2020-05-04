package io.remedymatch.bedarf.domain.model;

import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionStandort;
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
public class BedarfAnfrage {

    @NotNull
    @Valid
    private BedarfAnfrageId id;

    @NotNull
    @Valid
    private Bedarf bedarf;

    @NotNull
    private AngebotId angebotId;

    @NotNull
    @Valid
    private Institution institution;

    @NotNull
    @Valid
    private InstitutionStandort standort;

    @NotNull
    @Positive
    private BigDecimal anzahl;

    @NotBlank
    private String kommentar;

    @NotNull
    private BedarfAnfrageStatus status;

    private BigDecimal entfernung;
}
