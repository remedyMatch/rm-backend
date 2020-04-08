package io.remedymatch.bedarf.controller;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
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
}
