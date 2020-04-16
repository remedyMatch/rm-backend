package io.remedymatch.registrierung.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.remedymatch.properties.KeycloakProperties;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
class KeycloakClientProvider {

	private final KeycloakProperties properties;

	@Bean
	Keycloak keycloak() {
		return KeycloakBuilder.builder() //
				.serverUrl(properties.getServerUrl()) //
				.realm(properties.getClient().getRealm()) //
				.username(properties.getClient().getUsername()) //
				.password(properties.getClient().getPassword()) //
				.clientId(properties.getClient().getClientId()) //
				.clientSecret(properties.getClient().getClientSecret()) //
				.resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()) //
				.build();
	}
}
