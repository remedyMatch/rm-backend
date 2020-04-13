package io.remedymatch.institution.controller;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
class InstitutionAnfrageRO {

	@NotNull
	private UUID id;

	private UUID angebotId;

	private UUID bedarfId;

	@NotNull
	@Valid
	private InstitutionRO institutionAn;

	@NotNull
	@Valid
	private InstitutionStandortRO standortAn;

	@NotNull
	@Valid
	private InstitutionRO institutionVon;

	@NotNull
	@Valid
	private InstitutionStandortRO standortVon;

	@NotNull
	private BigDecimal anzahl;

	@NotNull
	private UUID artikelId;

	private UUID artikelVarianteId;

	@NotBlank
	private String kommentar;

	@NotBlank
	private String prozessInstanzId;

	@NotBlank
	private String status;

	@NotNull
	private BigDecimal entfernung;
}
