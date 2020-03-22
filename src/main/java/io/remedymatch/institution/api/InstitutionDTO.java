package io.remedymatch.institution.api;

import io.remedymatch.institution.domain.InstitutionTyp;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class InstitutionDTO {
    private UUID id;
    private String institutionKey;
    private String name;
    private InstitutionTyp typ;
    private String standort;
}
