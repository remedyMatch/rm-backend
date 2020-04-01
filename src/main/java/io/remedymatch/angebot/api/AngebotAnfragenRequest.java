package io.remedymatch.angebot.api;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
class AngebotAnfragenRequest {

    @NotNull
    private UUID angebotId;

    private String kommentar;

    @NotNull
    private UUID standortId;

    @NotNull
    private BigDecimal anzahl;
}
