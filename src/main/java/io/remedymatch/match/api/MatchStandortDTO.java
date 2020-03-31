package io.remedymatch.match.api;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
public class MatchStandortDTO {

    private UUID id;

    @NotNull
    private String plz;

    @NotNull
    private String ort;

    @NotNull
    private String strasse;

    @NotNull
    private String land;
}
