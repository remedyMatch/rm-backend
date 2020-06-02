package io.remedymatch.angebot.domain.model;

import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import lombok.*;

import javax.validation.Valid;
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
public class AngebotAnfrage {

    @NotNull
    @Valid
    private AngebotAnfrageId id;

    @NotNull
    @Valid
    private BedarfId bedarfId;

    @NotNull
    @Valid
    private Angebot angebot;

    @NotNull
    @Valid
    private Institution institution;

    @NotNull
    @Valid
    private InstitutionStandort standort;

    @NotNull
    @Positive
    private BigDecimal anzahl;

    @NotNull
    private AngebotAnfrageStatus status;

    private BigDecimal entfernung;
}
