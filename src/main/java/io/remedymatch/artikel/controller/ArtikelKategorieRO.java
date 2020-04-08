package io.remedymatch.artikel.controller;

import lombok.*;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtikelKategorieRO {
	
	@NotNull
    private UUID id;
	
	@NotBlank
    private String name;

    private String icon;
}
