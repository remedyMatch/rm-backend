package io.remedymatch.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Diese Klasse enthält alle Eigenschaften für das Ausstellen und Lesen von JSON Web Tokens.
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "io.remedymatch.geodaten")
public class GeodatenProperties {
    @NotNull
    @NotBlank
    private String geocoderServiceBaseUrl;

    @NotNull
    @NotBlank
    private String geocoderServiceApiKey;
}

