package io.remedymatch.aufgabe.api;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AufgabeAbschliessenRequest {

    private String taskId;
    private Map<String, Object> variables;

}
