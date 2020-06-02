package io.remedymatch.bedarf.controller;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class BedarfAnfragenIdSucheRequest {

    @NotNull
    @Builder.Default
    private List<UUID> ids = new ArrayList<>();

}
