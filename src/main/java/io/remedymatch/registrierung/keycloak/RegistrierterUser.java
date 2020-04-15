package io.remedymatch.registrierung.keycloak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class RegistrierterUser {

	private KeycloakUserId keycloakUserId;

	// Institution Attribute

	private String institutionName;
	private String institutionTyp;

	// Person Attribute

	private String username;
	private String vorname;
	private String nachname;
	private String email;
	private String telefon;

	// Standort Attribute

	private String strasse;
	private String hausnummer;
	private String plz;
	private String ort;
	private String land;
}
