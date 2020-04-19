package io.remedymatch.registrierung.keycloak;

import static io.remedymatch.registrierung.keycloak.KeycloakAttribute.KEYCLOAK_GRUPPE_FREIGEGEBEN;
import static io.remedymatch.registrierung.keycloak.KeycloakAttribute.KEYCLOAK_GRUPPE_USER;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sun.istack.NotNull;

import io.remedymatch.properties.KeycloakProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Validated
@Service
@Slf4j
public class KeycloakService {

	private final KeycloakProperties properties;

	public List<RegistrierterUser> findFreigegebeneUsers() {

		return keycloakGruppeUsers(KEYCLOAK_GRUPPE_FREIGEGEBEN).stream() //
				.map(KeycloakUserConverter::convert) //
				.collect(Collectors.toList());
	}

	public void userAufAktiviertSetzen(//
			final @NotNull @Valid KeycloakUserId userId) {
		UserResource userResource = keycloakUsers().get(userId.getValue());
		UserRepresentation user = userResource.toRepresentation();

		user.setEnabled(true);

		userResource.update(user);

		try {
			userGruppeAufUserSetzen(userId);
		} catch (Exception e) {
			log.error("Es war nicht möglich die Gruppe von 'fregegeben' auf 'user' zu setzen.", e);
		}
	}

	private List<UserRepresentation> keycloakGruppeUsers(final String gruppe) {
		return keycloakGroups().group(keycloakGroups().groups(gruppe, 0, 1).stream().findFirst().get().getId())
				.members();
	}

	private GroupsResource keycloakGroups() {
		return getUserRealm().groups();
	}

	private UsersResource keycloakUsers() {
		return getUserRealm().users();
	}

	private RealmResource getUserRealm() {
		return getKeycloak().realm(properties.getUser().getRealm());
	}

	private void userGruppeAufUserSetzen(final @NotNull @Valid KeycloakUserId userId) {
		List<GroupRepresentation> alleGruppen = keycloakGroups().groups();

		val freigegebenGruppeId = getKeycloakGruppeId(alleGruppen, KEYCLOAK_GRUPPE_FREIGEGEBEN);
		val userGruppeId = getKeycloakGruppeId(alleGruppen, KEYCLOAK_GRUPPE_USER);

		removeGruppeFromUser(userId.getValue(), freigegebenGruppeId);
		addGruppeToUser(userId.getValue(), userGruppeId);
	}

	private String getKeycloakGruppeId(//
			final List<GroupRepresentation> gruppen, //
			final String gruppeName) {
		return gruppen.stream().filter(gruppe -> StringUtils.equals(gruppeName, gruppe.getName())) //
				.map(GroupRepresentation::getId).findFirst() //
				.get();
	}

	private void removeGruppeFromUser(final String keycloakUserId, final String keycloakGroupId) {
		RestTemplate restTemplate = new RestTemplate();

		log.info(String.format("Removing Gruppe %s from User %s.", keycloakGroupId, keycloakUserId));

		ResponseEntity<Void> response = restTemplate.exchange(//
				getRealmUserGroupURI(keycloakUserId, keycloakGroupId), //
				HttpMethod.DELETE, //
				new HttpEntity<Void>(getHeaderMitToken()), //
				Void.class);

		log.info("Response: " + response.getStatusCodeValue());
	}

	private void addGruppeToUser(final String keycloakUserId, final String keycloakGroupId) {
		RestTemplate restTemplate = new RestTemplate();

		log.info(String.format("Adding Gruppe %s to User %s.", keycloakGroupId, keycloakUserId));

		ResponseEntity<Void> response = restTemplate.exchange(//
				getRealmUserGroupURI(keycloakUserId, keycloakGroupId), //
				HttpMethod.PUT, //
				new HttpEntity<Void>(getHeaderMitToken()), //
				Void.class);

		log.info("Response: " + response.getStatusCodeValue());
	}

	private HttpHeaders getHeaderMitToken() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + getKeycloak().tokenManager().getAccessTokenString());

		return headers;
	}

	private URI getRealmUserGroupURI(final String keycloakUserId, final String keycloakGroupId) {
		return UriComponentsBuilder.fromHttpUrl(properties.getServerUrl()) //
				.path("/admin/realms/").path(properties.getUser().getRealm()) //
				.path("/users/").path(keycloakUserId) //
				.path("/groups/").path(keycloakGroupId) //
				.build().toUri();
	}

	private Keycloak getKeycloak() {
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
