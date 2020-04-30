package io.remedymatch.person.domain.model;

import javax.validation.Valid;
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
public class PersonInstitution {

	@Valid
	@NotNull
	private PersonInstitutionId id;
	
	@Valid
	@NotNull
	private Institution institution;

	@Valid
	@NotNull
	private InstitutionStandort standort;
}
