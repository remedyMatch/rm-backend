package io.remedymatch.institution.domain.model;

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
public class NeueInstitution {

	@NotBlank
	private String name;

	@NotBlank
	private String institutionKey;

	@NotNull
	private InstitutionTyp typ;

	@Valid
	@NotNull
	private NeuerInstitutionStandort hauptstandort;
}
