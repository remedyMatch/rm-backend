package io.remedymatch.nachricht.domain.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

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

}
