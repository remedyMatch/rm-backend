package io.remedymatch.institution.domain.model;

import javax.validation.constraints.NotBlank;

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
public class NeuesInstitutionStandort {
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
