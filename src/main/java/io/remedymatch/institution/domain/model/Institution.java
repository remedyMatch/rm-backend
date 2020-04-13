package io.remedymatch.institution.domain.model;

import java.util.ArrayList;
import java.util.List;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Institution {

	@NotNull
	@Valid
	private InstitutionId id;
	
	@NotBlank
    private String name;
	
	@NotBlank
    private String institutionKey;
	
	@NotNull
    private InstitutionTyp typ;
	
	@Valid
	@NotNull
    private InstitutionStandort  hauptstandort;	
	
	@Valid
    @Builder.Default
    private List<InstitutionStandort> standorte = new ArrayList<>();
}
