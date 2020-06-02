package io.remedymatch.angebot.controller;

import java.math.BigInteger;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

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
class AngebotFilterEntryRO {

	@NotNull
	private UUID id;

	@NotNull
	@PositiveOrZero
	private BigInteger anzahl;
}
