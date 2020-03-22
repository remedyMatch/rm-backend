package io.remedymatch.artikel.api;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtikelKategorieDTO {
    private UUID id;
    private String name;
}
