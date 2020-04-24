package io.remedymatch.engine.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageKorrelierenRequest {

	@NotBlank
	private String prozessKey;
    
    private String prozessInstanzId;

	@NotBlank
    private String messageKey;

    private Map<String, Object> variablesEqual;
    
    private Map<String, Object> variables;
}
