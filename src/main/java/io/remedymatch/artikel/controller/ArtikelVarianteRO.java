package io.remedymatch.artikel.controller;

import java.util.UUID;

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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ArtikelVarianteRO {

	@NotNull
	private UUID id;

	private Integer sort;

	@NotNull
	@Valid
	private UUID artikelId;

	@NotBlank
	private String variante;

	private String norm;

	@NotBlank
	private String beschreibung;

	private boolean medizinischAuswaehlbar;
}