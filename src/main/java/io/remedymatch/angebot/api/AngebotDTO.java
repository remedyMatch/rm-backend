package io.remedymatch.angebot.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.institution.api.InstitutionStandortDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AngebotDTO {

    private UUID id;

    @NotNull
    private ArtikelDTO artikel;

    @NotNull
    private BigDecimal anzahl;

    @NotNull
    private InstitutionStandortDTO standort;

    @NotNull
    private String kommentar;

    private BigDecimal rest;

    private UUID institutionId;

    private LocalDateTime haltbarkeit;

    private boolean steril;

    private boolean originalverpackt;

    private boolean medizinisch;

    private boolean bedient;

    private BigDecimal entfernung;

}
