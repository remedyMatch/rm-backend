package io.remedymatch.angebot.domain.model;

import io.remedymatch.artikel.domain.model.Artikel;
import io.remedymatch.artikel.domain.model.ArtikelVariante;
import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Angebot {

    @NotNull
    @Valid
    private AngebotId id;

    @NotNull
    @Valid
    private ArtikelVariante artikelVariante;

    @NotNull
    @Valid
    private Artikel artikel;

    @NotNull
    @Positive
    private BigDecimal anzahl;

    @NotNull
    @PositiveOrZero
    private BigDecimal rest;

    @NotNull
    @Valid
    private Institution institution;

    @NotNull
    @Valid
    private InstitutionStandort standort;

    @NotNull
    private LocalDateTime haltbarkeit;

    private boolean steril;

    private boolean originalverpackt;

    private boolean medizinisch;

    @NotBlank
    private String kommentar;

    private boolean bedient;

    private boolean oeffentlich;

    @Positive
    private transient BigDecimal entfernung;

    @Builder.Default
    private List<AngebotAnfrage> anfragen = new ArrayList();

}
