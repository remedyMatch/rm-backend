package io.remedymatch.nachricht.api;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class NeueNachrichtRO {

    @NotBlank
    private String nachricht;

}
