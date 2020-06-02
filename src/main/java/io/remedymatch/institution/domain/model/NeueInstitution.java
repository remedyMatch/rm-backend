package io.remedymatch.institution.domain.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class NeueInstitution {

    @NotBlank
    private String name;

    @NotBlank
    private String institutionKey;

    @NotNull
    private InstitutionTyp typ;

    @Valid
    @NotNull
    private NeuerInstitutionStandort standort;
}
