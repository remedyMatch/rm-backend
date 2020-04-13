package io.remedymatch.bedarf.domain.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionStandort;
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
public class BedarfAnfrage {
	@NotNull
	@Valid
	private BedarfAnfrageId id;

	@NotNull
	@Valid
	private Bedarf bedarf;

	@NotNull
	@Valid
	private Institution institution;

	@NotNull
	@Valid
	private InstitutionStandort standort;

	@NotNull
	@Positive
	private BigDecimal anzahl;

	@NotBlank
	private String kommentar;

	private String prozessInstanzId;

	@NotNull
	private BedarfAnfrageStatus status;
}
