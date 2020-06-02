package io.remedymatch.registrierung.scheduler;

import io.remedymatch.registrierung.keycloak.KeycloakService;
import io.remedymatch.registrierung.keycloak.RegistrierterUser;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Component
@Log4j2
class UserAktualisator {

    private final KeycloakService keycloakClient;
    private final RegistrierungUebernahmeService registrierungUebernahmeService;

    /* Jede 30 Sekunden */
    @Scheduled(fixedRate = 30 * 1000)
    void uberpruefeNeueRegistrationen() {

        // 1) Users mit bestätigtem Mail finden
        List<RegistrierterUser> verifizierteUsers = keycloakClient.findVerifizierteUsers();

        // 2) Prozess starten
        verifizierteUsers.forEach(this::userUebernehmen);

        // sollte entfernt werden
        // 1) Users mit bestätigtem Mail finden
        List<RegistrierterUser> freigegebeneUsers = keycloakClient.findFreigegebeneUsers();

        // 2) Prozess starten
        freigegebeneUsers.forEach(this::freigegebenenUserUebernehmen);
    }

    @Transactional
    @Deprecated // arbeitet mit freigegebene user
    private void freigegebenenUserUebernehmen(final @NotNull @Valid RegistrierterUser registrierterUser) {
        try {
            registrierungUebernahmeService.registrierungUebernehmen(registrierterUser);
            keycloakClient.freigegebenenUserAufAktiviertSetzen(registrierterUser.getKeycloakUserId());
        } catch (Exception e) {
            log.error("Übernahme des Users hat nicht geklappt: " + registrierterUser, e);
        }
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
