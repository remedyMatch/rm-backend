package io.remedymatch.artikel.controller;

import javax.validation.constraints.NotBlank;

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
class NeueArtikelVarianteRO {

	@NotBlank
	private String variante;

	private String norm;

	@NotBlank
	private String beschreibung;

	private boolean medizinischAuswaehlbar;
}