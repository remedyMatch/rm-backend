package io.remedymatch.artikel.domain.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ArtikelVariante {
    @NotNull
    @Valid
    private ArtikelVarianteId id;

    @NotNull
    @Valid
    private ArtikelId artikelId;

    @NotNull
    @Valid
    private ArtikelKategorieId artikelKategorieId;

    private Integer sort;

    @NotBlank
    private String variante;

    private String norm;

    @NotBlank
    private String beschreibung;

    private boolean medizinischAuswaehlbar;
}