package io.remedymatch.bedarf.controller;

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
	private UUID artikelVarianteId;

	@NotNull
	private BigDecimal anzahl;

	@NotNull
	private UUID standortId;

	@NotNull
	private String kommentar;

	private boolean steril;

	private boolean medizinisch;

	private boolean bedient;
}
