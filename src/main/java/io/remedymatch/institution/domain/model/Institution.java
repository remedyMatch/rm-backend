package io.remedymatch.institution.domain.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Institution {

    @NotNull
    @Valid
    private InstitutionId id;

    @NotBlank
    private String name;

    @NotBlank
    private String institutionKey;

    @NotNull
    private InstitutionTyp typ;

    @Valid
    @Builder.Default
    private List<InstitutionStandort> standorte = new ArrayList<>();
}
