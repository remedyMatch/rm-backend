package io.remedymatch.bedarf.controller;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class BedarfBedienenRequest {

    private String kommentar;

    @NotNull
    private UUID standortId;

    @NotNull
    private BigDecimal anzahl;
}
