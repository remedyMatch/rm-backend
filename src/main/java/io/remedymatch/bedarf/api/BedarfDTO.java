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

    @NotNull
    private InstitutionStandortDTO standort;

    @NotNull
    private String kommentar;

    private UUID institutionId;

    private double rest;

    private boolean steril;

    private boolean originalverpackt;

    private boolean medizinisch;

    private boolean bedient;

    private double entfernung;

}
