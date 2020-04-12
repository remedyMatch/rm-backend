package io.remedymatch.institution.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import io.remedymatch.institution.domain.InstitutionTyp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstitutionRO {

    private UUID id;

    @NotNull
    private String institutionKey;

    @NotNull
    private String name;

    @NotNull
    private InstitutionTyp typ;

    private InstitutionStandortRO hauptstandort;
    @Builder.Default
    private List<InstitutionStandortRO> standorte = new ArrayList<>();
}
