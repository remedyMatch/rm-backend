package io.remedymatch.bedarf.api;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.institution.api.InstitutionStandortDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BedarfDTO {

    private UUID id;

    @NotNull
    private ArtikelDTO artikel;

    @NotNull
    private BigDecimal anzahl;

    @NotNull
    private InstitutionStandortDTO standort;

    @NotNull
    private String kommentar;

    private UUID institutionId;

    private BigDecimal rest;

    private boolean steril;

    private boolean originalverpackt;

    private boolean medizinisch;

    private boolean bedient;

    private BigDecimal entfernung;

}
