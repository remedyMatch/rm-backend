package io.remedymatch.institution.domain.model;

import java.math.BigDecimal;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class InstitutionStandort {
	@NotNull
	@Valid
	private InstitutionStandortId id;

	@NotBlank
	private String name;

	@NotBlank
	private String strasse;

	@NotBlank
	private String hausnummer;

	@NotBlank
	private String plz;

	@NotBlank
	private String ort;

	@NotBlank
	private String land;
	
	private BigDecimal longitude;
	private BigDecimal latitude;
}
