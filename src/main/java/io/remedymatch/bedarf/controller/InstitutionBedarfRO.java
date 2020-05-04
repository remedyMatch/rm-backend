package io.remedymatch.bedarf.controller;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Der InstitutionBedarf enthält zusätzlich alle Anfragen, die zu diesem Bedarf offen sind
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class InstitutionBedarfRO extends BedarfRO {

    @NotNull
    @Builder.Default
    private List<BedarfAnfrageRO> anfragen = new ArrayList();

}
