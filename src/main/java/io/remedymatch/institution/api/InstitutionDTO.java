package io.remedymatch.institution.api;

import io.remedymatch.institution.domain.InstitutionTyp;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class InstitutionDTO {

    private UUID id;

    @NotNull
    private String institutionKey;

    @NotNull
    private String name;

    @NotNull
    private InstitutionTyp typ;

    private InstitutionStandortDTO hauptstandort;

    private List<InstitutionStandortDTO> standorte;
}
