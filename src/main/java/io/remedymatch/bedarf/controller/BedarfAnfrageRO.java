package io.remedymatch.bedarf.controller;

import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.institution.controller.InstitutionRO;
import io.remedymatch.institution.controller.InstitutionStandortRO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@MappedSuperclass
class BedarfAnfrageRO {

    @NotNull
    private UUID id;

    @NotNull
    @Valid
    private InstitutionRO institution;

    @NotNull
    @Valid
    private InstitutionStandortRO standort;

    @NotNull
    @Positive
    private BigDecimal anzahl;

    @NotBlank
    private String kommentar;

    @NotNull
    private UUID angebotId;

    @NotNull
    private BedarfAnfrageStatus status;

    private BigDecimal entfernung;
}
