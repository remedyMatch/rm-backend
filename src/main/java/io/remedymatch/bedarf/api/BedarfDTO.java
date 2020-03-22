package io.remedymatch.bedarf.api;

import io.remedymatch.artikel.api.ArtikelDTO;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BedarfDTO {
    private UUID id;
    private ArtikelDTO artikel;
    private double anzahl;

}
