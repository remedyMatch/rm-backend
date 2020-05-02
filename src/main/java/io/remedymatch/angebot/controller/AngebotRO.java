package io.remedymatch.angebot.controller;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@MappedSuperclass
public class AngebotRO {

    @NotNull
    private UUID id;

    @NotNull
    @Valid
    private UUID artikelId;

    @NotNull
    @Valid
    private UUID artikelVarianteId;

    @NotNull
    @Valid
    private UUID artikelKategorieId;

    @NotNull
    @PositiveOrZero
    private BigDecimal verfuegbareAnzahl;

    @NotNull
    private String ort;

    @NotNull
    private LocalDateTime haltbarkeit;

    private boolean steril;

    private boolean originalverpackt;

    private boolean medizinisch;

    @NotBlank
    private String kommentar;

    @NotNull
    @Positive
    private BigDecimal entfernung;

}
