package io.remedymatch.anfrage.api;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AnfrageDTO {

    private UUID id;
    private String kommentar;
    private UUID institutionVon;
    private UUID institutionAn;
    private UUID bedarf;
    private UUID angebot;
    private String standortAn;
    private String standortVon;
    private boolean storniert;
    private boolean angenommen;

}
