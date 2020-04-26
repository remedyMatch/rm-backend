package io.remedymatch.bedarf.domain.model;

import java.math.BigInteger;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
public class BedarfFilterEntry {

	@NotNull
	private UUID id;

	@NotNull
	@Positive
	private BigInteger anzahl;
}
