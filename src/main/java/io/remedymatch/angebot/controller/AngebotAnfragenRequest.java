package io.remedymatch.angebot.controller;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
class AngebotAnfragenRequest {

	@NotNull
	private UUID standortId;
    
    @NotNull
    @Positive
    private BigDecimal anzahl;

    private String kommentar;
}
