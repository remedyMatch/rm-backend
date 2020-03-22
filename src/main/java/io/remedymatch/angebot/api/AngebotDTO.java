package io.remedymatch.angebot.api;

import io.remedymatch.artikel.api.ArtikelDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AngebotDTO {
    private UUID id;
    private ArtikelDTO artikel;
    private double anzahl;

    private String standort;
    private LocalDateTime gueltigkeit;
    private boolean steril;
    private boolean originalverpackt;
    private boolean medizinisch;

}
