package io.remedymatch.person.controller;

import io.remedymatch.institution.controller.InstitutionRO;
import io.remedymatch.institution.controller.InstitutionStandortRO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Institution2StandortRO {

    private InstitutionRO institution;

    private List<InstitutionStandortRO> standorte;
}
