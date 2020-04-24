package io.remedymatch.artikel.domain.model;

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
public class ArtikelVariante {
    @NotNull
    @Valid
    private ArtikelVarianteId id;
    
    @NotNull
    @Valid
    private ArtikelId artikelId;

    private Integer sort;
    
    @NotBlank
    private String variante;

    private String norm;

    @NotBlank
    private String beschreibung;

    private boolean medizinischAuswaehlbar;
}