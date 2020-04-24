package io.remedymatch.artikel.domain.model;

import java.util.ArrayList;
import java.util.List;

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
public class Artikel {
    @NotNull
    @Valid
    private ArtikelId id;
    
    @NotNull
    @Valid
    private ArtikelKategorieId artikelKategorieId;
    
    @NotBlank
    private String name;

    @NotBlank
    private String beschreibung;
    
    @Valid
    @Builder.Default
    private List<ArtikelVariante> varianten = new ArrayList<>();
}