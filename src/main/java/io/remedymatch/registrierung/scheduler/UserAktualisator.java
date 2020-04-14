package io.remedymatch.registrierung.scheduler;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.registrierung.keycloak.KeycloakService;
import io.remedymatch.registrierung.keycloak.RegistrierterUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
class UserAktualisator {

	private final KeycloakService keycloakClient;
	private final RegistrierungUebernahmeService registrierungUebernahmeService;

	/* Jede 30 Sekunden */
	@Scheduled(fixedRate = 30 * 1000)
	void uberpruefeNeueRegistrationen() {

		// 1) Users mit bestätigtem Mail finden
		List<RegistrierterUser> verifizierteUsers = keycloakClient.findFreigegebeneUsers();

		// 2) Prozess starten
		verifizierteUsers.forEach(user -> userUebernehmen(user));
	}

	@Transactional
	private void userUebernehmen(final @NotNull @Valid RegistrierterUser registrierterUser) {
		try {
			registrierungUebernahmeService.registrierungUebernehmen(registrierterUser);
			keycloakClient.userAufAktiviertSetzen(registrierterUser.getKeycloakUserId());
		} catch (Exception e) {
			log.error("Übernahme des Users hat nicht geklappt: " + registrierterUser, e);
		}
	}
}
