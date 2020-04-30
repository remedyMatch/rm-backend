package io.remedymatch.person.controller;

import java.util.ArrayList;
import java.util.List;
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
class PersonRO {

	@NotNull
	private UUID id;

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

	@NotNull
	@Valid
	private PersonInstitutionRO aktuelleInstitution;

	@NotNull
	@Builder.Default
	private List<PersonInstitutionRO> institutionen = new ArrayList<>();
}
