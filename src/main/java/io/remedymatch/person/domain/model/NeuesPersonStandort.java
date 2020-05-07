package io.remedymatch.person.domain.model;

import javax.validation.Valid;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class NeuesPersonStandort {

	@NotNull
	@Valid
	private InstitutionId institutionId;

	@NotNull
	@Valid
	private InstitutionStandortId standortId;

	private boolean oeffentlich;
}
