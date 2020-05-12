package io.remedymatch.nachricht.domain.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class NeueNachricht {

    @NotBlank
    private String nachricht;

    @NotNull
    private NachrichtReferenz referenzId;

    @NotNull
    private NachrichtReferenzTyp referenzTyp;

}
