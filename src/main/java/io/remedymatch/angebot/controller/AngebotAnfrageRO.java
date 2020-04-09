package io.remedymatch.angebot.controller;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.institution.api.InstitutionDTO;
import io.remedymatch.institution.api.InstitutionStandortDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
class AngebotAnfrageRO {

	@NotNull
	private UUID id;

	@NotNull
	@Valid
	private AngebotRO angebot;

	@NotNull
	@Valid
	private InstitutionDTO institution;

	@NotNull
	@Valid
	private InstitutionStandortDTO standort;

	@NotNull
	@Positive
	private BigDecimal anzahl;

	@NotBlank
	private String kommentar;

	private String prozessInstanzId;

	@NotNull
	private AngebotAnfrageStatus status;
}
