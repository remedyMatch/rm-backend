package io.remedymatch.institution.api;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstitutionStandortDTO {


    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String plz;

    @NotNull
    private String ort;

    @NotNull
    private String strasse;

    @NotNull
    private String land;

    private BigDecimal longitude;

    private BigDecimal latitude;
}
