package io.remedymatch.properties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

/**
 * Einstellungen zur Keycloak
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "io.remedymatch.keycloak")
public class KeycloakProperties {

	@NotNull
	@NotBlank
	private String url;

	@NotNull
	@NotBlank
	private String realm;

	@NotNull
	@NotBlank
	private String username;

	@NotNull
	@NotBlank
	private String password;

	@NotNull
	@NotBlank
	private String clientId;

	@NotNull
	@NotBlank
	private String clientSecret;
}
