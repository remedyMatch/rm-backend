package io.remedymatch.institution.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
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
public class InstitutionRO {

	@NotNull
	private UUID id;

	@NotBlank
	private String name;

	@NotBlank
	private String institutionKey;

	@NotNull
	private InstitutionTyp typ;

	@NotNull
	@Valid
	private InstitutionStandortRO hauptstandort;

	@Valid
	@Builder.Default
	private List<InstitutionStandortRO> standorte = new ArrayList<>();
}
