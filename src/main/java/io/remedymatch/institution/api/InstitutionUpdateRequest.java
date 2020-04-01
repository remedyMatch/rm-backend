package io.remedymatch.institution.api;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.remedymatch.institution.domain.InstitutionTyp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
class InstitutionUpdateRequest {
	@NotNull
	private UUID id;

	@NotBlank
	private String name;

	@NotBlank
	private InstitutionTyp typ;
}
