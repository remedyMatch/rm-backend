package io.remedymatch.institutions;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class InstitutionDTO {
    private UUID id;
    private String institutionKey;
    private String name;
    private InstitutionType type;
}
