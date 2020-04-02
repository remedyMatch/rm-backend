package io.remedymatch.bedarf.api;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class NeuesBedarfRequest {

    @NotNull
    private UUID artikelId;

    @NotNull
    private BigDecimal anzahl;

    @NotNull
    private UUID standortId;

    @NotNull
    private String kommentar;

    private boolean steril;

    private boolean originalverpackt;

    private boolean medizinisch;

    private boolean bedient;
}
