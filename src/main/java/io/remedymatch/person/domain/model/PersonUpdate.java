package io.remedymatch.person.domain.model;

import io.remedymatch.institution.domain.model.InstitutionStandortId;
import lombok.*;

import javax.validation.Valid;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class PersonUpdate {

    @Valid
    private InstitutionStandortId aktuelleStandortId;
}
