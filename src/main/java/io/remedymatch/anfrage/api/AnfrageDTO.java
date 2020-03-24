package io.remedymatch.anfrage.api;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AnfrageDTO {

    private UUID id;
    private String kommentar;
    private UUID anfrager;
    private UUID bedarf;
    private boolean storniert;
    private boolean angenommen;

}
