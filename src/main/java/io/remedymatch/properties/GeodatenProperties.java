package io.remedymatch.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Diese Klasse enthält alle Eigenschaften für das Geocoding.
 */
@Getter
@Setter
@Profile("geo")
@ConfigurationProperties(prefix = "io.remedymatch.geodaten")
public class GeodatenProperties {

    private String geocoderServiceBaseUrl;

    private String geocoderServiceApiKey;
}

