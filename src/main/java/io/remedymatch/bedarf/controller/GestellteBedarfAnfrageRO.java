package io.remedymatch.bedarf.controller;

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
public class GestellteBedarfAnfrageRO extends BedarfAnfrageRO {

    @NotNull
    @Valid
    private BedarfRO bedarf;

}
