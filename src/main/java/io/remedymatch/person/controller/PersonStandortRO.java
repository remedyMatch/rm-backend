package io.remedymatch.person.controller;

import java.util.UUID;

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
class PersonStandortRO {

	@NotNull
	private UUID id;
	
	@NotNull
	@Valid
	private InstitutionRO institution;

	@NotNull
	@Valid
	private InstitutionStandortRO standort;
	
	private boolean oefentlich;
}
