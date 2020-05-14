package io.remedymatch.person.controller;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
class PersonUpdateRequest {

    private UUID aktuelleStandortId;

    boolean hasAenderungen() {
        return aktuelleStandortId != null;
    }
}
