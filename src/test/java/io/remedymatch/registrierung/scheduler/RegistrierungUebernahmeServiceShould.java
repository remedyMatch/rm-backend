package io.remedymatch.registrierung.scheduler;

import io.remedymatch.institution.domain.model.*;
import io.remedymatch.institution.domain.service.InstitutionService;
import io.remedymatch.person.domain.model.NeuePerson;
import io.remedymatch.person.domain.service.PersonService;
import io.remedymatch.registrierung.keycloak.KeycloakUserId;
import io.remedymatch.registrierung.keycloak.RegistrierterUser;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
        RegistrierungUebernahmeService.class, //
        InstitutionService.class, //
        PersonService.class //
})
@Tag("Spring")
@DisplayName("RegistrierungUebernahmeService soll")
class RegistrierungUebernahmeServiceShould {

    @Autowired
    private RegistrierungUebernahmeService registrierungUebernahmeService;

    @MockBean
    private InstitutionService institutionService;

    @MockBean
    private PersonService personService;

    @Test
    @DisplayName("Privatperson uebernehmen koennen")
    void privatperson_uebernehmen_koennen() {

        val registrierterUserPrivat = RegistrierterUser.builder() //
                .keycloakUserId(new KeycloakUserId("privatpersonKeycloakUserId")) //
                .username("usernamePrivatperson") //
                .vorname("Privatperson Vorname") //
                .nachname("Privatperson Nachname") //
                .email("privatperson@remedymatch.test") //
                .telefon("010225566") //
                .strasse("Privatperson Strasse") //
                .hausnummer("155") //
                .plz("82021") //
                .ort("München") //
                .land("Deutschland") //
                .build();

        val neueInstitution = NeueInstitution.builder() //
                .name("Privatperson Vorname Privatperson Nachname") //
                .institutionKey("privat__usernameprivatperson") //
                .typ(InstitutionTyp.PRIVAT) //
                .standort(NeuerInstitutionStandort.builder() //
                        .name("Privatperson Vorname Privatperson Nachname") //
                        .strasse("Privatperson Strasse") //
                        .hausnummer("155") //
                        .plz("82021") //
                        .ort("München") //
                        .land("Deutschland") //
                        .build()) //
                .build();

        Institution institution = Institution.builder() //
                .id(new InstitutionId(UUID.randomUUID())) //
                .name("Privatperson Vorname Privatperson Nachname") //
                .institutionKey("privat__usernameprivatperson") //
                .typ(InstitutionTyp.PRIVAT) //
                .standorte(Arrays.asList(InstitutionStandort.builder() //
                        .id(new InstitutionStandortId(UUID.randomUUID())) //
                        .name("Privatperson Vorname Privatperson Nachname") //
                        .strasse("Privatperson Strasse") //
                        .hausnummer("155") //
                        .plz("82021") //
                        .ort("München") //
                        .land("Deutschland") //
                        .longitude(BigDecimal.valueOf(10)) //
                        .latitude(BigDecimal.valueOf(20)) //
                        .build())) //
                .build();

        val neuePerson = NeuePerson.builder() //
                .username("usernamePrivatperson") //
                .vorname("Privatperson Vorname") //
                .nachname("Privatperson Nachname") //
                .email("privatperson@remedymatch.test") //
                .telefon("010225566") //
                .institutionId(institution.getId()) //
                .standortId(institution.getStandorte().get(0).getId()) //
                .build();

        given(institutionService.institutionAnlegen(neueInstitution)).willReturn(institution);

        registrierungUebernahmeService.registrierungUebernehmen(registrierterUserPrivat);

        then(institutionService).should().institutionAnlegen(neueInstitution);
        then(institutionService).shouldHaveNoMoreInteractions();
        then(personService).should().personAnlegen(neuePerson);
        then(personService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("nicht Privatperson uebernehmen koennen")
    void nicht_rivatperson_uebernehmen_koennen() {

        val registrierterUserPrivat = RegistrierterUser.builder() //
                .keycloakUserId(new KeycloakUserId("keycloakUserId")) //
                .institutionName("Irgendein Lieferant") //
                .institutionTyp("Lieferant") //
                .username("usernameLieferant") //
                .vorname("Vorname") //
                .nachname("Nachname") //
                .email("lieferantn@remedymatch.test") //
                .telefon("0102241565566") //
                .strasse("Lieferant Strasse") //
                .hausnummer("84/2a") //
                .plz("82021") //
                .ort("München") //
                .land("Deutschland") //
                .build();

        val neueInstitution = NeueInstitution.builder() //
                .name("Irgendein Lieferant") //
                .institutionKey("lieferant__irgendein_lieferant") //
                .typ(InstitutionTyp.LIEFERANT) //
                .standort(NeuerInstitutionStandort.builder() //
                        .name("Irgendein Lieferant") //
                        .strasse("Lieferant Strasse") //
                        .hausnummer("84/2a") //
                        .plz("82021") //
                        .ort("München") //
                        .land("Deutschland") //
                        .build()) //
                .build();

        Institution institution = Institution.builder() //
                .id(new InstitutionId(UUID.randomUUID())) //
                .name("Irgendein Lieferant") //
                .institutionKey("lieferant__irgendein_lieferant") //
                .typ(InstitutionTyp.LIEFERANT) //
                .standorte(Arrays.asList(InstitutionStandort.builder() //
                        .id(new InstitutionStandortId(UUID.randomUUID())) //
                        .name("Irgendein Lieferant") //
                        .strasse("Lieferant Strasse") //
                        .hausnummer("84/2a") //
                        .plz("82021") //
                        .ort("München") //
                        .land("Deutschland") //
                        .longitude(BigDecimal.valueOf(11)) //
                        .latitude(BigDecimal.valueOf(22)) //
                        .build())) //
                .build();

        val neuePerson = NeuePerson.builder() //
                .username("usernameLieferant") //
                .vorname("Vorname") //
                .nachname("Nachname") //
                .email("lieferantn@remedymatch.test") //
                .telefon("0102241565566") //
                .institutionId(institution.getId()) //
                .standortId(institution.getStandorte().get(0).getId()) //
                .build();

        given(institutionService.institutionAnlegen(neueInstitution)).willReturn(institution);

        registrierungUebernahmeService.registrierungUebernehmen(registrierterUserPrivat);

        then(institutionService).should().institutionAnlegen(neueInstitution);
        then(institutionService).shouldHaveNoMoreInteractions();
        then(personService).should().personAnlegen(neuePerson);
        then(personService).shouldHaveNoMoreInteractions();
    }
}
