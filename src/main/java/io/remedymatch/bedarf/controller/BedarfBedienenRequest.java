package io.remedymatch.bedarf.controller;

import java.math.BigDecimal;
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
class BedarfBedienenRequest {

	@NotNull
	private UUID standortId;

	@NotNull
	@Positive
	private BigDecimal anzahl;

	private String kommentar;
}
