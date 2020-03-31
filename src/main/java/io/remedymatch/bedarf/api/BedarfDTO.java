package io.remedymatch.bedarf.api;

import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.institution.api.InstitutionStandortDTO;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
public class BedarfDTO {

    private UUID id;

    @NotNull
    private ArtikelDTO artikel;

    @NotNull
    private double anzahl;

    private double rest;

    @NotNull
    private InstitutionStandortDTO standort;

    private UUID institutionId;

    private String kommentar;

    private boolean steril;

    private boolean originalverpackt;

    private boolean medizinisch;

    private boolean bedient;
}
