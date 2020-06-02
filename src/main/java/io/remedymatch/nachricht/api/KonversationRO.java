package io.remedymatch.nachricht.api;


import io.remedymatch.nachricht.domain.model.NachrichtReferenzTyp;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class KonversationRO {

    @NotNull
    private UUID id;

    @Builder.Default
    private List<String> beteiligte = new ArrayList<>();

    @Builder.Default
    private List<NachrichtRO> nachrichten = new ArrayList<>();

    @NotNull
    private UUID referenzId;

    @NotNull
    private NachrichtReferenzTyp referenzTyp;

}
