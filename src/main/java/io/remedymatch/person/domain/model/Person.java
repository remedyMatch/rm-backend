package io.remedymatch.person.domain.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class Person {
	
	@Valid
	@NotNull
	private PersonId id;

	@NotBlank
	private String username;

	@NotBlank
	private String vorname;

	@NotBlank
	private String nachname;

	@NotBlank
	private String email;

	@NotBlank
	private String telefon;

	@Valid
	@NotNull
	private Institution institution;

	@Valid
	@NotNull
	private InstitutionStandort standort;
}
