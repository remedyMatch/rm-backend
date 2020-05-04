package io.remedymatch.bedarf.controller;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class BedarfAnzahlAendernRequest {

    private BigDecimal anzahl;

}
