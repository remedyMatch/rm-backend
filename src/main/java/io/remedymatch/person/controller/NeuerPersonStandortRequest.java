package io.remedymatch.person.controller;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
class NeuerPersonStandortRequest {

    @NotNull
    private UUID institutionId;

    @NotNull
    private UUID standortId;

    private boolean oeffentlich;
}
