package io.remedymatch.angebot.api;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AngebotAnfragenRequest {

    private UUID angebotId;
    private String kommentar;
    private String standort;
}
