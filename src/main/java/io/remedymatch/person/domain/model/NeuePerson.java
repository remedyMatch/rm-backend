package io.remedymatch.person.domain.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
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
public class NeuePerson {

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
	private InstitutionId institutionId;

	@Valid
	@NotNull
	private InstitutionStandortId standortId;
	
	private boolean standortOeffentlich;
}
