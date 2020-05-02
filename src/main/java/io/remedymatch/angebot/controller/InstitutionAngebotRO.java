package io.remedymatch.angebot.controller;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Das InstitutionAngebot enthält zusätzlich alle Anfragen, die zu diesem Angebot existieren
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class InstitutionAngebotRO extends AngebotRO {

    @NotNull
    @Builder.Default
    private List<AngebotAnfrage> anfragen = new ArrayList();

}
