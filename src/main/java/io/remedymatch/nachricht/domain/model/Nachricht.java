package io.remedymatch.nachricht.domain.model;

import io.remedymatch.person.domain.model.PersonId;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Nachricht {

    @NotNull
    @Valid
    private NachrichtId id;

    @NotBlank
    private String nachricht;

    @NotNull
    private PersonId von;

    @NotNull
    private NachrichtReferenz referenzId;

    @NotNull
    private NachrichtReferenzTyp referenzTyp;

    @NotNull
    private LocalDateTime erstelltAm;

}
