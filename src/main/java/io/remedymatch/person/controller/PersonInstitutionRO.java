package io.remedymatch.person.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.remedymatch.institution.controller.InstitutionRO;
import io.remedymatch.institution.controller.InstitutionStandortRO;
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
public class PersonInstitutionRO {

	@NotNull
	@Valid
	private InstitutionRO institution;

	@NotNull
	@Valid
	private InstitutionStandortRO standort;
}
