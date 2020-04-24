package io.remedymatch.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Engine Properties
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "io.remedymatch.engine")
public class EngineProperties {
    /**
     * Process Start - URL
     */
    @NotNull
    @NotBlank
    private String remedyRestApiUrl;

    /**
     * ExternalTask - URL
     */
    @NotNull
    @NotBlank
    private String externalTaskUrl;
}
