package io.remedymatch.person.controller;

import java.util.UUID;

import javax.validation.constraints.NotNull;

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
class NeuesPersonStandortRequest {

	@NotNull
	private UUID institutionId;

	@NotNull
	private UUID standortId;

	private boolean oeffentlich;
}
