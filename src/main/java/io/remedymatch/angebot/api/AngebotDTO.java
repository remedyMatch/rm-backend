package io.remedymatch.angebot.api;

import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.institution.api.InstitutionDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AngebotDTO {
    private UUID id;
    private InstitutionDTO institution;
    private ArtikelDTO artikel;
    private double anzahl;

    private String kommentar;
    private String standort;
    private LocalDateTime haltbarkeit;
    private boolean steril;
    private boolean originalverpackt;
    private boolean medizinisch;
    private boolean bedient;

}
