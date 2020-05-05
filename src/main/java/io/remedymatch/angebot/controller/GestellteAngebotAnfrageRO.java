package io.remedymatch.angebot.controller;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class GestellteAngebotAnfrageRO extends AngebotAnfrageRO {

    @NotNull
    @Valid
    private AngebotRO angebot;

}
