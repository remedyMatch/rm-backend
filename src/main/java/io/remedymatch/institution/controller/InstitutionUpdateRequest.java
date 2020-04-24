package io.remedymatch.institution.controller;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.remedymatch.institution.domain.model.InstitutionTyp;
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
class InstitutionUpdateRequest {

	@NotBlank
	private String name;

	@NotNull
	private InstitutionTyp typ;

//	@NotNull
	private UUID hauptstandortId;
}
