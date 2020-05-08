package io.remedymatch.person.controller;

import io.remedymatch.institution.controller.InstitutionRO;
import io.remedymatch.institution.controller.InstitutionStandortRO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
class PersonStandortRO {

    @NotNull
    private UUID id;

    @NotNull
    @Valid
    private InstitutionRO institution;

    @NotNull
    @Valid
    private InstitutionStandortRO standort;

    private boolean oeffentlich;
}
