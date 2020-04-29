package io.remedymatch.institution.controller;

import io.remedymatch.institution.domain.model.InstitutionAntragStatus;
import io.remedymatch.institution.domain.model.InstitutionRolle;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class InstitutionAntragRO {

    private UUID id;

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
    private InstitutionAntragStatus status;

    @NotNull
    private InstitutionTyp institutionTyp;
}
