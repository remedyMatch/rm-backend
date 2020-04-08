package io.remedymatch.bedarf.controller;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import io.remedymatch.institution.api.InstitutionStandortDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BedarfRO {

	@NotNull
	private UUID id;

	@NotNull
	@Valid
	private UUID artikelId;

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
	private InstitutionStandortDTO standort;

	private boolean steril;

	private boolean medizinisch;

	@NotBlank
	private String kommentar;

	private boolean bedient;

	@NotNull
	@Positive
	private BigDecimal entfernung;

}
