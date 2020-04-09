package io.remedymatch.bedarf.controller;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.institution.api.InstitutionDTO;
import io.remedymatch.institution.api.InstitutionStandortDTO;
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
class BedarfAnfrageRO {

	@NotNull
	private UUID id;

	@NotNull
	@Valid
	private BedarfRO bedarf;

	@NotNull
	@Valid
	private InstitutionDTO institutionVon;

	@NotNull
	@Valid
	private InstitutionStandortDTO standortVon;

	@NotNull
	@Positive
	private BigDecimal anzahl;

	@NotBlank
	private String kommentar;

	private String prozessInstanzId;

	@NotNull
	private BedarfAnfrageStatus status;
}