package io.remedymatch.angebot.controller;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterEntryRO {

	@NotNull
	private UUID id;

	@NotNull
	@Valid
	private UUID name;

	@NotNull
	@PositiveOrZero
	private BigDecimal anzahl;
}
