package io.remedymatch.bedarf.controller;

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
class BedarfBedienenRequest {

    @NotNull
    private UUID standortId;

    @NotNull
    @Positive
    private BigDecimal anzahl;

    @NotNull
    private UUID anfrageId;

    private String kommentar;
}
