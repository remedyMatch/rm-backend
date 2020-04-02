package io.remedymatch.bedarf.api;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class BedarfBedienenRequest {

    @NotNull
    private UUID bedarfId;

    private String kommentar;

    @NotNull
    private UUID standortId;

    @NotNull
    private BigDecimal anzahl;
}
