package io.remedymatch.engine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageKorrelierenRequest {

    private String prozessInstanzId;

    private String messageKey;

    private Map<String, Object> variables;

}
