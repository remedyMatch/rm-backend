package io.remedymatch.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import lombok.Getter;
import lombok.Setter;

/**
 * Diese Klasse enthält alle Eigenschaften für das Geocoding.
 */
@Getter
@Setter
@Profile("geo")
@ConfigurationProperties(prefix = "io.remedymatch.geodaten")
public class GeodatenProperties {
	private String geocoderServiceApiKey;
}
