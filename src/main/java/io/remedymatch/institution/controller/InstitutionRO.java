package io.remedymatch.institution.controller;

import io.remedymatch.institution.domain.model.InstitutionTyp;
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
public class InstitutionRO {

    @NotNull
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String institutionKey;

    @NotNull
    private InstitutionTyp typ;

    @Valid
    @Builder.Default
    private List<InstitutionStandortRO> standorte = new ArrayList<>();
}
