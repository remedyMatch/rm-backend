package io.remedymatch.bedarf.controller;

import io.remedymatch.artikel.controller.ArtikelRO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@MappedSuperclass
public class BedarfRO {

    @NotNull
    private UUID id;

    @NotNull
    @Valid
    private ArtikelRO artikel;

    @Valid
    private UUID artikelVarianteId;

    @NotNull
    @Positive
    private BigDecimal verfuegbareAnzahl;

    @NotNull
    private UUID institutionId;

    @NotNull
    private String ort;

    private boolean steril;

    private boolean medizinisch;

    private boolean oeffentlich;

    @NotBlank
    private String kommentar;

    @NotNull
    @Positive
    private BigDecimal entfernung;
}
