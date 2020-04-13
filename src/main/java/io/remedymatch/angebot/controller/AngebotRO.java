package io.remedymatch.angebot.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import io.remedymatch.institution.controller.InstitutionStandortRO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AngebotRO {

	@NotNull
	private UUID id;

	@NotNull
	@Valid
	private UUID artikelVarianteId;

	@NotNull
	@Positive
	private BigDecimal anzahl;

	@NotNull
	@PositiveOrZero
	private BigDecimal rest;

	@NotNull
	private UUID institutionId;

	@NotNull
	@Valid
	private InstitutionStandortRO standort;

	@NotNull
	private LocalDateTime haltbarkeit;

	private boolean steril;

	private boolean originalverpackt;

	private boolean medizinisch;

	@NotBlank
	private String kommentar;

	@NotNull
	@Positive
	private BigDecimal entfernung;
}
