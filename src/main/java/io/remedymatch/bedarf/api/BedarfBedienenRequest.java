package io.remedymatch.bedarf.api;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
public class BedarfBedienenRequest {

    @NotNull
    private UUID bedarfId;

    private String kommentar;

    @NotNull
    private String standort;

    @NotNull
    private double anzahl;
}
