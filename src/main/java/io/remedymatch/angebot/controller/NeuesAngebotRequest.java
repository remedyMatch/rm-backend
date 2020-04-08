package io.remedymatch.angebot.controller;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
class NeuesAngebotRequest {

    @NotNull
    private UUID artikelVarianteId;

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
