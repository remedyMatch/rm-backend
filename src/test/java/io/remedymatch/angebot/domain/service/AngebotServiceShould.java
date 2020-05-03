package io.remedymatch.angebot.domain.service;

import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.service.InstitutionTestFixtures;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.usercontext.UserContextService;
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
import java.util.Optional;
import java.util.UUID;

import static io.remedymatch.angebot.domain.service.AngebotAnfrageTestFixtures.*;
import static io.remedymatch.angebot.domain.service.AngebotTestFixtures.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
        AngebotService.class, //
        AngebotJpaRepository.class, //
        AngebotAnfrageJpaRepository.class, //
        UserContextService.class, //
        AngebotProzessService.class //
})
@Tag("Spring")
@DisplayName("AngebotService soll")
class AngebotServiceShould {
    @Autowired
    private AngebotService angebotService;

    @MockBean
    private AngebotJpaRepository angebotRepository;

    @MockBean
    private AngebotAnfrageJpaRepository anfrageRepository;

    @MockBean
    private UserContextService userService;

    @MockBean
    private AngebotProzessService angebotProzessService;

    @Test
    @DisplayName("Fehler werfen bei Bearbeitung von nicht existierender Angebpt")
    void fehler_werfen_bei_Bearbeitung_von_nicht_existierender_Angebot() {
        val unbekannteAngebotId = beispielAngebotId();

        assertThrows(ObjectNotFoundException.class, //
                () -> angebotService.getNichtBedienteAngebot(unbekannteAngebotId));
    }

    @Test
    @DisplayName("Fehler werfen bei Bearbeitung von bediente Angebpt")
    void fehler_werfen_bei_Bearbeitung_von_bediente_Angebot() {
        val bedienteAngebotId = beispielAngebotId();
        val bedienteAngebotEntity = beispielAngebotEntity();
        bedienteAngebotEntity.setBedient(true);

        given(angebotRepository.findById(bedienteAngebotId.getValue())).willReturn(Optional.of(bedienteAngebotEntity));

        assertThrows(OperationNotAlloudException.class, //
                () -> angebotService.getNichtBedienteAngebot(bedienteAngebotId));
    }

    @Test
    @DisplayName("Fehler werfen bei Bearbeitung von Angebpt, die nicht UserContext Institution gehoert")
    void fehler_werfen_bei_Bearbeitung_von_Angebot_die_nicht_UserContext_Institution_gehoert() {
        val angebotId = beispielAngebotId();
        val angebot = beispielAngebot();
        val angebotEntity = beispielAngebotEntity();

        given(angebotRepository.findById(angebotId.getValue())).willReturn(Optional.of(angebotEntity));
        given(userService.isUserContextInstitution(angebot.getInstitution().getId())).willReturn(false);

        assertThrows(NotUserInstitutionObjectException.class, //
                () -> angebotService.getNichtBedienteAngebotDerUserInstitution(angebotId));
    }

    @Test
    @DisplayName("Fehler werfen bei Bearbeitung von nicht existierender Anfrage")
    void fehler_werfen_bei_Bearbeitung_von_nicht_existierender_Anfrage() {
        val unbekannteAnfrageId = beispielAngebotAnfrageId();

        assertThrows(ObjectNotFoundException.class, //
                () -> angebotService.getOffeneAnfrage(unbekannteAnfrageId));
    }

    @Test
    @DisplayName("Fehler werfen bei Bearbeitung von Anfragea aus anderer Angebot")
    void fehler_werfen_bei_Bearbeitung_von_Anfrage_aus_anderer_Angebot() {
        val andereAngebotId = new AngebotId(UUID.randomUUID());

        val anfrageId = beispielAngebotAnfrageId();
        val anfrageEntity = beispielAngebotAnfrageEntity();

        given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

        assertThrows(OperationNotAlloudException.class, //
                () -> angebotService.getOffeneAnfrage(andereAngebotId, anfrageId));
    }

    @Test
    @DisplayName("Fehler werfen bei Bearbeitung von nicht offene Anfrage")
    void fehler_werfen_bei_Bearbeitung_von_nicht_offene_Anfrage() {
        val stornierteAnfrageId = beispielAngebotAnfrageId();
        val stornierteAnfrageAngebotId = beispielAngebotAnfrage().getAngebot().getId();
        val stornierteAnfrageEntity = beispielAngebotAnfrageEntity();
        stornierteAnfrageEntity.setStatus(AngebotAnfrageStatus.STORNIERT);

        given(anfrageRepository.findById(stornierteAnfrageId.getValue()))
                .willReturn(Optional.of(stornierteAnfrageEntity));

        assertThrows(OperationNotAlloudException.class, //
                () -> angebotService.getOffeneAnfrage(stornierteAnfrageAngebotId, stornierteAnfrageId));
    }

    @Test
    @DisplayName("Fehler werfen bei Bearbeitung von Anfrage, die nicht UserContext Institution gehoert")
    void fehler_werfen_bei_Bearbeitung_von_Anfrage_die_nicht_UserContext_Institution_gehoert() {
        val anfrageId = beispielAngebotAnfrageId();
        val anfrage = beispielAngebotAnfrage();
        val anfrageAngebotId = anfrage.getAngebot().getId();
        val anfrageEntity = beispielAngebotAnfrageEntity();

        given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));
        given(userService.isUserContextInstitution(anfrage.getInstitution().getId())).willReturn(false);

        assertThrows(NotUserInstitutionObjectException.class, //
                () -> angebotService.getOffeneAnfrageDerUserInstitution(anfrageAngebotId, anfrageId));
    }

    @Test
    @DisplayName("Fehler werfen wenn der Standort nicht in UserContext Institution gefunden wird")
    void fehler_werfen_wenn_der_Standort_nicht_in_UserContext_Institution_gefunden_wird() {

        assertThrows(NotUserInstitutionObjectException.class, //
                () -> angebotService.getUserInstitutionStandort(InstitutionTestFixtures.beispielInstitutionEntity(),
                        new InstitutionStandortId(UUID.randomUUID())));
    }

    @Test
    @DisplayName("Angebot der UserContext Institution loeschen koennen")
    void angebot_der_UserContext_Institution_loeschen_koennen() {
        val angebotId = beispielAngebotId();
        val angebot = beispielAngebot();
        val angebotInstitutionId = angebot.getInstitution().getId();
        val angebotEntity = beispielAngebotEntity();
        val angebotEntityBedient = beispielAngebotEntity();
        angebotEntityBedient.setDeleted(true);

        given(angebotRepository.findById(angebotId.getValue())).willReturn(Optional.of(angebotEntity));
        given(userService.isUserContextInstitution(angebotInstitutionId)).willReturn(true);
        given(angebotRepository.save(angebotEntityBedient)).willReturn(angebotEntityBedient);

        angebotService.angebotDerUserInstitutionLoeschen(angebotId);

        then(angebotRepository).should().findById(angebotId.getValue());
        then(angebotRepository).should().save(angebotEntityBedient);
        then(angebotRepository).shouldHaveNoMoreInteractions();
//        then(anfrageRepository).should().updateStatus(angebotId.getValue(), AngebotAnfrageStatus.OFFEN,
//                AngebotAnfrageStatus.STORNIERT);
        then(anfrageRepository).shouldHaveNoMoreInteractions();
        then(userService).should().isUserContextInstitution(angebotInstitutionId);
        then(userService).shouldHaveNoMoreInteractions();
        then(angebotProzessService).should().angebotSchliessen(angebotId);
        then(angebotProzessService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("neue Angebot Anfrage einstellen koennen")
    void neue_anfrage_einstellen_koennen() {
        val kommentar = "Anfrage Kommentar";
        val anzahl = BigDecimal.valueOf(10000);

        val anfrageId = beispielAngebotAnfrageId();
        val anfrage = beispielAngebotAnfrage();
        anfrage.setAnzahl(anzahl);
        anfrage.setKommentar(kommentar);

        val anfrageEntity = beispielAngebotAnfrageEntity();
        anfrageEntity.setAnzahl(anzahl);
        anfrageEntity.setKommentar(kommentar);

        val institution = anfrage.getInstitution();
        val institutionEntity = anfrageEntity.getInstitution();
        val standortEntity = anfrageEntity.getStandort();
        val standortId = anfrage.getStandort().getId();

        val angebot = anfrage.getAngebot();
        val angebotEntity = anfrageEntity.getAngebot();
        val angebotSteller = new PersonId(angebotEntity.getCreatedBy());
        val angebotId = angebot.getId();
        val angebotInstitutionId = angebot.getInstitution().getId();

        AngebotAnfrageEntity neueAnfrageEntity = AngebotAnfrageEntity.builder()//
                .angebot(angebotEntity) //
                .institution(institutionEntity) //
                .standort(standortEntity) //
                .anzahl(anzahl) //
                .kommentar(kommentar) //
                .status(AngebotAnfrageStatus.OFFEN) //
                .build();

        given(angebotRepository.findById(angebotId.getValue())).willReturn(Optional.of(angebotEntity));
        given(userService.getContextInstitution()).willReturn(institution);
        // given(anfrageProzessService.prozessStarten(angebotId, angebotSteller, anfrageId, angebotInstitutionId));
        given(anfrageRepository.save(neueAnfrageEntity)).willReturn(anfrageEntity);
        given(anfrageRepository.save(anfrageEntity)).willReturn(anfrageEntity);

        assertEquals(anfrage, angebotService.angebotAnfrageErstellen(//
                angebotId, //
                standortId, //
                kommentar, //
                anzahl));

        then(angebotRepository).should().findById(angebotId.getValue());
        then(angebotRepository).shouldHaveNoMoreInteractions();
        then(anfrageRepository).should().save(neueAnfrageEntity);
        then(anfrageRepository).shouldHaveNoMoreInteractions();
        then(userService).should().getContextInstitution();
        then(userService).shouldHaveNoMoreInteractions();
        then(angebotProzessService).should().anfrageErhalten(anfrageId, angebotId);
        then(angebotProzessService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Angebot Anfrage der UserContext Institution stornieren koennen")
    void anfrage_der_UserContext_Institution_stornieren_koennen() {
        val anfrageId = beispielAngebotAnfrageId();
        val anfrage = beispielAngebotAnfrage();
        val anfrageInstitutionId = anfrage.getInstitution().getId();
        val anfrageAngebotId = anfrage.getAngebot().getId();
        val anfrageEntity = beispielAngebotAnfrageEntity();
        val anfrageEntityStorniert = beispielAngebotAnfrageEntity();
        anfrageEntityStorniert.setStatus(AngebotAnfrageStatus.STORNIERT);

        given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));
        given(userService.isUserContextInstitution(anfrageInstitutionId)).willReturn(true);
        given(anfrageRepository.save(anfrageEntityStorniert)).willReturn(anfrageEntityStorniert);

        angebotService.angebotAnfrageDerUserInstitutionStornieren(anfrageAngebotId, anfrageId);

        then(angebotRepository).shouldHaveNoInteractions();
        then(anfrageRepository).should().findById(anfrageId.getValue());
        then(anfrageRepository).should().save(anfrageEntityStorniert);
        then(anfrageRepository).shouldHaveNoMoreInteractions();
        then(userService).should().isUserContextInstitution(anfrageInstitutionId);
        then(userService).shouldHaveNoMoreInteractions();
        then(angebotProzessService).should().anfrageStornieren(anfrageId, anfrage.getAngebot().getId());
        then(angebotProzessService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Angebot Anfrage ablehnen koennen")
    void anfrage_ablehnen_koennen() {
        val anfrageId = beispielAngebotAnfrageId();
        val anfrageEntity = beispielAngebotAnfrageEntity();
        val anfrageEntityStorniert = beispielAngebotAnfrageEntity();
        anfrageEntityStorniert.setStatus(AngebotAnfrageStatus.ABGELEHNT);

        given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

        angebotService.anfrageAbgelehnt(anfrageEntity);

        then(angebotRepository).shouldHaveNoInteractions();
        then(anfrageRepository).should().save(anfrageEntityStorniert);
        then(anfrageRepository).shouldHaveNoMoreInteractions();
        then(userService).shouldHaveNoInteractions();
        then(angebotProzessService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("eine OperationNotAlloudException werfen wenn bei Annahme der Anzahl in Anfrage hoeher ist als Rest in Angebot")
    void eine_OperationNotAlloudException_werfen_wenn_bei_Annahme_der_Anzahl_in_Anfrage_hoeher_ist_als_Rest_in_Angebot() {

        val angebotRestDavor = BigDecimal.valueOf(800);
        val anfrageAnzahl = BigDecimal.valueOf(4000);

        val anfrageId = beispielAngebotAnfrageId();
        val anfrageEntity = beispielAngebotAnfrageEntity();
        anfrageEntity.setAnzahl(anfrageAnzahl);
        anfrageEntity.getAngebot().setRest(angebotRestDavor);

        given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

        assertThrows(OperationNotAlloudException.class, //
                () -> angebotService.anfrageAngenommen(anfrageEntity));
    }

    @Test
    @DisplayName("Angebot Anfrage annehmen und Angebot-Rest reduzieren koennen, wenn die Anfrage die Angebot dekt")
    void anfrage_annehmen_und_Angebot_Rest_reduzieren_koennen_wenn_die_Anfrage_die_Angebot_dekt() {

        val angebotRestDavor = BigDecimal.valueOf(1000);
        val anfrageAnzahl = BigDecimal.valueOf(800);
        val angebotRestDanach = BigDecimal.valueOf(200);

        val anfrageId = beispielAngebotAnfrageId();
        val anfrageEntity = beispielAngebotAnfrageEntity();
        anfrageEntity.setAnzahl(anfrageAnzahl);
        anfrageEntity.getAngebot().setRest(angebotRestDavor);

        val anfrageEntityAngenommen = beispielAngebotAnfrageEntity();
        anfrageEntityAngenommen.setAnzahl(anfrageAnzahl);
        anfrageEntityAngenommen.setStatus(AngebotAnfrageStatus.ANGENOMMEN);

        val angebotDanach = anfrageEntityAngenommen.getAngebot();
        angebotDanach.setRest(angebotRestDanach);

        given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

        angebotService.anfrageAngenommen(anfrageEntity);

        then(angebotRepository).should().save(angebotDanach);
        then(angebotRepository).shouldHaveNoMoreInteractions();
        then(anfrageRepository).should().save(anfrageEntityAngenommen);
        then(anfrageRepository).shouldHaveNoMoreInteractions();
        then(userService).shouldHaveNoInteractions();
        then(angebotProzessService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Angebot Anfrage annehmen und Angebot als bedient schliessen")
    void anfrage_annehmen_und_Angebot_als_bedient_schliessen() {

        val angebotRestDavor = BigDecimal.valueOf(1000);
        val anfrageAnzahl = BigDecimal.valueOf(1000);
        val angebotRestDanach = BigDecimal.ZERO;

        val anfrageId = beispielAngebotAnfrageId();
        val anfrageEntity = beispielAngebotAnfrageEntity();
        anfrageEntity.setAnzahl(anfrageAnzahl);
        anfrageEntity.getAngebot().setRest(angebotRestDavor);

        val anfrageEntityAngenommen = beispielAngebotAnfrageEntity();
        anfrageEntityAngenommen.setAnzahl(anfrageAnzahl);
        anfrageEntityAngenommen.setStatus(AngebotAnfrageStatus.ANGENOMMEN);

        val angebotDanach = anfrageEntityAngenommen.getAngebot();
        angebotDanach.setRest(angebotRestDanach);
        angebotDanach.setBedient(true);

        given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

        angebotService.anfrageAngenommen(anfrageEntity);

        then(angebotRepository).should().save(angebotDanach);
        then(angebotRepository).shouldHaveNoMoreInteractions();
        then(anfrageRepository).should().save(anfrageEntityAngenommen);
        then(anfrageRepository).shouldHaveNoMoreInteractions();
        then(userService).shouldHaveNoInteractions();
        then(angebotProzessService).shouldHaveNoInteractions();
    }
}
