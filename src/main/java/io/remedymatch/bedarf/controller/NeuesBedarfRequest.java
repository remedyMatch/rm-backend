package io.remedymatch.bedarf.controller;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
class NeuesBedarfRequest {

    private UUID artikelId;

    private UUID artikelVarianteId;

    @NotNull
    private BigDecimal anzahl;

    @NotNull
    private UUID standortId;

    @NotNull
    private String kommentar;

    private boolean steril;

    private boolean medizinisch;

    private boolean oeffentlich;
}
