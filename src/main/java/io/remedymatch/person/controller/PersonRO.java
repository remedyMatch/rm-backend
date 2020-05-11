package io.remedymatch.person.controller;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
class PersonRO {

    @NotNull
    private UUID id;

    @NotBlank
    private String username;

    @NotBlank
    private String vorname;

    @NotBlank
    private String nachname;

    @NotBlank
    private String email;

    @NotBlank
    private String telefon;

    @NotNull
    @Valid
    private PersonStandortRO aktuellerStandort;

    @NotNull
    @Builder.Default
    private List<Institution2StandortRO> institutionen = new ArrayList<>();
}
