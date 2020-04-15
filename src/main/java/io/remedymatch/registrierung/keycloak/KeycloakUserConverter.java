package io.remedymatch.registrierung.keycloak;

import static io.remedymatch.registrierung.keycloak.KeycloakAttribute.KEYCLOAK_USER_ATTRIBUT_CITY;
import static io.remedymatch.registrierung.keycloak.KeycloakAttribute.KEYCLOAK_USER_ATTRIBUT_COMPANY;
import static io.remedymatch.registrierung.keycloak.KeycloakAttribute.KEYCLOAK_USER_ATTRIBUT_COMPANY_TYPE;
import static io.remedymatch.registrierung.keycloak.KeycloakAttribute.KEYCLOAK_USER_ATTRIBUT_COUNTRY;
import static io.remedymatch.registrierung.keycloak.KeycloakAttribute.KEYCLOAK_USER_ATTRIBUT_HOUSENUMBER;
import static io.remedymatch.registrierung.keycloak.KeycloakAttribute.KEYCLOAK_USER_ATTRIBUT_PHONE;
import static io.remedymatch.registrierung.keycloak.KeycloakAttribute.KEYCLOAK_USER_ATTRIBUT_STREET;
import static io.remedymatch.registrierung.keycloak.KeycloakAttribute.KEYCLOAK_USER_ATTRIBUT_ZIPCODE;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.keycloak.representations.idm.UserRepresentation;

final class KeycloakUserConverter {
	private KeycloakUserConverter() {

	}

	static RegistrierterUser convert(final UserRepresentation userRepresentation) {
		Map<String, List<String>> attribute = userRepresentation.getAttributes();

		return RegistrierterUser.builder() //
				// Keycloak
				.keycloakUserId(new KeycloakUserId(userRepresentation.getId())) //
				// Institution
				.institutionName(getAttributWertAlsText(attribute, KEYCLOAK_USER_ATTRIBUT_COMPANY)) //
				.institutionTyp(getAttributWertAlsText(attribute, KEYCLOAK_USER_ATTRIBUT_COMPANY_TYPE)) //
				// Person
				.username(userRepresentation.getUsername()) //
				.vorname(userRepresentation.getFirstName()) //
				.nachname(userRepresentation.getLastName()) //
				.email(userRepresentation.getEmail()) //
				.telefon(getAttributWertAlsText(attribute, KEYCLOAK_USER_ATTRIBUT_PHONE)) //

				.strasse(getAttributWertAlsText(attribute, KEYCLOAK_USER_ATTRIBUT_STREET)) //
				.hausnummer(getAttributWertAlsText(attribute, KEYCLOAK_USER_ATTRIBUT_HOUSENUMBER)) //
				.plz(getAttributWertAlsText(attribute, KEYCLOAK_USER_ATTRIBUT_ZIPCODE)) //
				.ort(getAttributWertAlsText(attribute, KEYCLOAK_USER_ATTRIBUT_CITY)) //
				.land(getAttributWertAlsText(attribute, KEYCLOAK_USER_ATTRIBUT_COUNTRY)) //

				.build();
	}

	private static String getAttributWertAlsText(final Map<String, List<String>> attribute, final String attributKey) {
		if (attribute == null) {
			return null;
		}

		if (attribute.containsKey(attributKey)) {
			return attribute.get(attributKey).stream().collect(Collectors.joining(", "));
		}
		return null;
	}
}
