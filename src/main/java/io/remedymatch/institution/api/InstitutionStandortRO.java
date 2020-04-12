package io.remedymatch.institution.api;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstitutionStandortRO {

    private UUID id;

    @NotBlank
    private String name;
    
    @NotBlank
    private String strasse;
    
    @NotBlank
    private String hausnummer;

    @NotBlank
    private String plz;

    @NotBlank
    private String ort;

    @NotBlank
    private String land;

    private BigDecimal longitude;

    private BigDecimal latitude;
}
