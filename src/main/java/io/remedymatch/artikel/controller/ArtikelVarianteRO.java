package io.remedymatch.artikel.controller;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

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

    @NotBlank
    private String variante;

    private String norm;

    @NotBlank
    private String beschreibung;

    private boolean medizinischAuswaehlbar;
}