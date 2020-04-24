package io.remedymatch.match.domain;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class MatchStandort {
	@NotNull
	@Valid
	private MatchStandortId id;

	@NotNull
	private UUID institutionStandortId;

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
