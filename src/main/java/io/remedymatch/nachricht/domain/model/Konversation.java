package io.remedymatch.nachricht.domain.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Konversation {

    @NotNull
    private KonversationId id;

    @Builder.Default
    private List<String> beteiligte = new ArrayList<>();

    @Builder.Default
    private List<Nachricht> nachrichten = new ArrayList<>();

    //Anfrage -> dann Match -> Liste!?
    @NotNull
    private NachrichtReferenz referenzId;

    //Anfrage -> dann Match
    @NotNull
    private NachrichtReferenzTyp referenzTyp;

}
