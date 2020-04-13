package io.remedymatch.institution.controller;

import javax.validation.constraints.NotBlank;

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
class NeuesInstitutionStandortRequest {

	@NotBlank
	private String name;

	@NotBlank
	private String strasse;

	// TODO: Rausbauen, sobald UI Hausnummer hat
//	@NotBlank
	private String hausnummer;

	@NotBlank
	private String plz;

	@NotBlank
	private String ort;

	@NotBlank
	private String land;
}
