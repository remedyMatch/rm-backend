package io.remedymatch.angebot.controller;

import lombok.*;

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
@Builder
class AngebotAnfragenRequest {

    @NotNull
    @Positive
    private BigDecimal anzahl;

    @NotNull
    private UUID bedarfId;

    private String nachricht;
}
