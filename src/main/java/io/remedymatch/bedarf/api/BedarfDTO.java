package io.remedymatch.bedarf.api;

import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.institution.api.InstitutionDTO;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BedarfDTO {
    private UUID id;
    private InstitutionDTO institution;
    private ArtikelDTO artikel;
    private double anzahl;

    private String kommentar;
    private String standort;
    private boolean steril;
    private boolean originalverpackt;
    private boolean medizinisch;

}
