package io.remedymatch.institution.domain.model;

import javax.validation.Valid;

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
public class InstitutionUpdate {
	private String neueName;

	private InstitutionTyp neuesTyp;

	@Valid
	private InstitutionStandortId neuesHauptstandortId;
}
