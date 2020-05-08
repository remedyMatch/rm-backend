package io.remedymatch.person.domain.model;

import java.util.ArrayList;
import java.util.List;

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
	private PersonStandort aktuellesStandort;

	@Valid
	@NotNull
	@Builder.Default
	private List<PersonStandort> standorte = new ArrayList<>();
}
