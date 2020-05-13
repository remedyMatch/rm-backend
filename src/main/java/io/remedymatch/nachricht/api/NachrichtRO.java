package io.remedymatch.nachricht.api;

import io.remedymatch.nachricht.domain.model.NachrichtId;
import io.remedymatch.nachricht.domain.model.NachrichtReferenzTyp;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class NachrichtRO {

    @NotNull
    private NachrichtId id;

    @NotBlank
    private String nachricht;

    @NotNull
    private UUID ersteller;

    @NotNull
    private UUID erstellerInstitution;

    @NotNull
    private String erstellerName;

    @NotNull
    private UUID referenzId;

    @NotNull
    private NachrichtReferenzTyp referenzTyp;

    @NotNull
    private LocalDateTime erstelltAm;

}
