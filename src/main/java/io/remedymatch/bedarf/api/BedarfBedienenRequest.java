package io.remedymatch.bedarf.api;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BedarfBedienenRequest {

    private UUID bedarfId;
    private String kommentar;
    private String standort;
}
