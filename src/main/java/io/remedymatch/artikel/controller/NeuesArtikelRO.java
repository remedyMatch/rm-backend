package io.remedymatch.artikel.controller;

import java.util.ArrayList;
import java.util.List;
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
class NeuesArtikelRO {

	@NotNull
	@Valid
	private UUID artikelKategorieId;

	@NotBlank
	private String name;

	@NotBlank
	private String beschreibung;

	@Valid
	@Builder.Default
	private List<NeueArtikelVarianteRO> varianten = new ArrayList<>();
}