package io.remedymatch.engine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProzessStartRequest {
    private String prozessKey;
    private String anfrageId;
    private String institutionId;
    private String typ;
}
