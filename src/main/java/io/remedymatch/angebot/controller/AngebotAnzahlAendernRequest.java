package io.remedymatch.angebot.controller;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class AngebotAnzahlAendernRequest {

    private BigDecimal anzahl;

}
