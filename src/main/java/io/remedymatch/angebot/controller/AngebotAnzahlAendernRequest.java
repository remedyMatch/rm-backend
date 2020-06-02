package io.remedymatch.angebot.controller;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class AngebotAnzahlAendernRequest {

    @NotNull
    private BigDecimal anzahl;

}
