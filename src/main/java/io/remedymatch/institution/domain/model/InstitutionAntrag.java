package io.remedymatch.institution.domain.model;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class InstitutionAntrag {

    private InstitutionAntragId id;

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

    @NotBlank
    private String webseite;

    @NotNull
    private InstitutionRolle rolle;

    @NotNull
    private InstitutionAntragStatus status;

    @NotNull
    private InstitutionTyp institutionTyp;

    @NotNull
    private UUID antragsteller;

}
