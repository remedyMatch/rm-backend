package io.remedymatch.institution.controller;

import io.remedymatch.institution.domain.model.InstitutionRolle;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class InstitutionBeantragenRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String strasse;

    @NotBlank
    private String hausnummer;

    @NotBlank
    private String plz;

    @NotBlank
    private String ort;

    @NotBlank
    private String land;

    @NotNull
    private String webseite;

    @NotNull
    private InstitutionRolle rolle;

    @NotNull
    private InstitutionTyp institutionTyp;

}
