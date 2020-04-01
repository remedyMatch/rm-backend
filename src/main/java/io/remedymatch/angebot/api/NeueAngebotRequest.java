package io.remedymatch.angebot.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class NeueAngebotRequest {

    @NotNull
    private UUID artikelId;

    @NotNull
    private BigDecimal anzahl;

    @NotNull
    private UUID standortId;

    @NotNull
    private String kommentar;

    private LocalDateTime haltbarkeit;

    private boolean steril;

    private boolean originalverpackt;

    private boolean medizinisch;

    private boolean bedient;
}
