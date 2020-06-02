package io.remedymatch.institution.domain.service;

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.geodaten.domain.GeocodingService;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.institution.domain.model.*;
import io.remedymatch.institution.infrastructure.*;
import io.remedymatch.usercontext.UserContextService;
import io.remedymatch.usercontext.UserContextTestFixtures;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
        InstitutionService.class, //
        UserContextService.class, //
        InstitutionJpaRepository.class, //
        InstitutionStandortJpaRepository.class, //
        InstitutionAntragJpaRepository.class, //
        InstitutionProzessService.class, //
        GeocodingService.class //
})
@Tag("Spring")
@DisplayName("InstitutionService soll")
class InstitutionServiceShould {

    @Autowired
    private InstitutionService institutionService;

    @MockBean
    private UserContextService userService;

    @MockBean
    private InstitutionJpaRepository institutionRepository;

    @MockBean
    private InstitutionStandortJpaRepository institutionStandortRepository;

    @MockBean
    private InstitutionAntragJpaRepository institutionAntragJpaRepository;

    @MockBean
    private GeocodingService geocodingService;

    @MockBean
    private EngineClient engineClient;

    @Test
    @DisplayName("Fehler werfen bei Bearbeitung von Standort, der sich nicht in Institution befindet")
    void fehler_werfen_bei_Bearbeitung_von_Standort_der_sich_nicht_in_Institution_befindet() {
        val unbekanntesStandortId = new InstitutionStandortId(UUID.randomUUID());

        assertThrows(ObjectNotFoundException.class, //
                () -> institutionService.getStandort(InstitutionTestFixtures.beispielInstitutionEntity(),
                        unbekanntesStandortId));
    }

    @Test
    @DisplayName("Fehler werfen wenn fuer die Standort Addresse keine Geodaten gefunden werden")
    void fehler_werfen_wenn_fuer_die_Standort_Addresse_keine_Geodaten_gefunden_werden() {
        assertThrows(ObjectNotFoundException.class, //
                () -> institutionService.mitGeodatenErweitern(InstitutionTestFixtures.beispielStandort1Entity()));
    }

//    @Test
//    @DisplayName("Fehler werfen bei update der Institution nichts zu aktualisieren gibt")
//    void fehler_werfen_wenn_bei_update_der_Institution_nichts_zu_aktualisieren_gibt() {
//        assertThrows(OperationNotAlloudException.class, //
//                () -> institutionService.userInstitutionAktualisieren(new InstitutionUpdate()));
//    }

    @Test
    @DisplayName("Neue Institution anlegen")
    void neue_Institution_anlegen() {

        val institutionName = "Meine neue Institution";
        val institutionKey = "meine_neue_institution";
        val institutionTyp = InstitutionTyp.LIEFERANT;

        val standortName = "Mein Hauptstandort";
        val standortPlz = "85050";
        val standortOrt = "Irgendwo in Bayern";
        val standortStrasse = "Eine Strasse";
        val standortHausnummer = "10";
        val standortLand = "Deutschland";

        val addresse = "Eine Strasse 10, 85050 Irgendwo in Bayern, Deutschland";

        val neuesStandort = NeuerInstitutionStandort.builder()//
                .name(standortName) //
                .plz(standortPlz) //
                .ort(standortOrt) //
                .strasse(standortStrasse) //
                .hausnummer(standortHausnummer) //
                .land(standortLand) //
                .build();
        val neuesHauptstandortId = new InstitutionStandortId(UUID.randomUUID());

        val longitude = 100.0;
        val latitude = 500.0;
        InstitutionStandortEntity neuesHauptstandortEntityOhneId = InstitutionStandortEntity.builder()//
                .name(standortName) //
                .plz(standortPlz) //
                .ort(standortOrt) //
                .strasse(standortStrasse) //
                .hausnummer(standortHausnummer) //
                .land(standortLand) //
                .longitude(BigDecimal.valueOf(longitude)) //
                .latitude(BigDecimal.valueOf(latitude)) //
                .build();
        InstitutionStandortEntity neuesHauptstandortEntity = InstitutionStandortEntity.builder()//
                .id(neuesHauptstandortId.getValue()) //
                .name(standortName) //
                .plz(standortPlz) //
                .ort(standortOrt) //
                .strasse(standortStrasse) //
                .hausnummer(standortHausnummer) //
                .land(standortLand) //
                .longitude(BigDecimal.valueOf(longitude)) //
                .latitude(BigDecimal.valueOf(latitude)) //
                .build();
        val neuesHauptstandort = InstitutionStandort.builder()//
                .id(neuesHauptstandortId) //
                .name(standortName) //
                .plz(standortPlz) //
                .ort(standortOrt) //
                .strasse(standortStrasse) //
                .hausnummer(standortHausnummer) //
                .land(standortLand) //
                .longitude(BigDecimal.valueOf(longitude)) //
                .latitude(BigDecimal.valueOf(latitude)) //
                .build();

        val neueInstitution = NeueInstitution.builder()//
                .name(institutionName) //
                .institutionKey(institutionKey) //
                .typ(institutionTyp) //
                .standort(neuesStandort) //
                .build();
        val neueInstitutionId = new InstitutionId(UUID.randomUUID());

        InstitutionEntity neueInstitutionMitHauptstandortEntityOhneId = InstitutionEntity.builder() //
                .name(institutionName) //
                .institutionKey(institutionKey) //
                .typ(institutionTyp) //
                .standorte(Arrays.asList(neuesHauptstandortEntity)) //
                .build();
        InstitutionEntity neueInstitutionMitHauptstandortEntity = InstitutionEntity.builder() //
                .id(neueInstitutionId.getValue()) //
                .name(institutionName) //
                .institutionKey(institutionKey) //
                .typ(institutionTyp) //
                .standorte(Arrays.asList(neuesHauptstandortEntity)) //
                .build();

        val neueInstitutionMitHauptstandort = Institution.builder() //
                .id(neueInstitutionId) //
                .name(institutionName) //
                .institutionKey(institutionKey) //
                .typ(institutionTyp) //
                .standorte(Arrays.asList(neuesHauptstandort)) //
                .build();

        given(institutionStandortRepository.save(neuesHauptstandortEntityOhneId)).willReturn(neuesHauptstandortEntity);
        given(institutionRepository.save(neueInstitutionMitHauptstandortEntityOhneId))
                .willReturn(neueInstitutionMitHauptstandortEntity);
        given(geocodingService.findePointsByAdressString(addresse))
                .willReturn(Arrays.asList(new Point(latitude, longitude)));

        assertEquals(neueInstitutionMitHauptstandort, institutionService.institutionAnlegen(neueInstitution));

        then(userService).shouldHaveNoInteractions();
        then(institutionRepository).should().save(neueInstitutionMitHauptstandortEntityOhneId);
        then(institutionRepository).shouldHaveNoMoreInteractions();
        then(institutionStandortRepository).should().save(neuesHauptstandortEntityOhneId);
        then(institutionStandortRepository).shouldHaveNoMoreInteractions();
        then(geocodingService).should().findePointsByAdressString(addresse);
        then(geocodingService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Name der Institution aktualisieren")
    void name_der_Institution_aktualisieren() {

        val userStandort = UserContextTestFixtures.beispielUserContextPerson().getAktuellesStandort();

        val institutionMitNeuemName = UserContextTestFixtures.beispielUserContextInstitution();
        institutionMitNeuemName.setName("Neue Name");
        val institutionEntityMitNeuemName = UserContextTestFixtures.beispielUserContextInstitutionEntity();
        institutionEntityMitNeuemName.setName("Neue Name");

        given(userService.getContextStandort()).willReturn(userStandort);
        given(institutionRepository.save(institutionEntityMitNeuemName)).willReturn(institutionEntityMitNeuemName);

        assertEquals(institutionMitNeuemName, institutionService
                .userInstitutionAktualisieren(InstitutionUpdate.builder().neueName("Neue Name").build()));

        then(userService).should().getContextStandort();
        then(userService).shouldHaveNoMoreInteractions();
        then(institutionRepository).should().save(institutionEntityMitNeuemName);
        then(institutionRepository).shouldHaveNoMoreInteractions();
        then(institutionStandortRepository).shouldHaveNoInteractions();
        then(geocodingService).shouldHaveNoInteractions();
    }

//    @Test
//    @DisplayName("Typ der Institution aktualisieren")
//    void typ_der_Institution_aktualisieren() {
//
//        val userStandort = UserContextTestFixtures.beispielUserContextPerson().getAktuellesStandort();
//
//        val institutionMitNeuemTyp = UserContextTestFixtures.beispielUserContextInstitution();
//        institutionMitNeuemTyp.setTyp(InstitutionTyp.KRANKENHAUS);
//        val institutionEntityMitNeuemTyp = UserContextTestFixtures.beispielUserContextInstitutionEntity();
//        institutionEntityMitNeuemTyp.setTyp(InstitutionTyp.KRANKENHAUS);
//
//        given(userService.getContextStandort()).willReturn(userStandort);
//        given(institutionRepository.save(institutionEntityMitNeuemTyp)).willReturn(institutionEntityMitNeuemTyp);
//
//        assertEquals(institutionMitNeuemTyp, institutionService.userInstitutionAktualisieren(
//                InstitutionUpdate.builder().neuesTyp(InstitutionTyp.KRANKENHAUS).build()));
//
//        then(userService).should().getContextStandort();
//        then(userService).shouldHaveNoMoreInteractions();
//        then(institutionRepository).should().save(institutionEntityMitNeuemTyp);
//        then(institutionRepository).shouldHaveNoMoreInteractions();
//        then(institutionStandortRepository).shouldHaveNoInteractions();
//        then(geocodingService).shouldHaveNoInteractions();
//    }

//    @Test
//    @DisplayName("neues Hauptstandort in Institution hinzufuegen koennen")
//    void neues_Hauptstandort_in_Institution_hinzufuegen_koennen() {
//
//        val name = "Mein Hauptstandort";
//        val plz = "85050";
//        val ort = "Irgendwo in Bayern";
//        val strasse = "Eine Strasse";
//        val hausnummer = "10";
//        val land = "Deutschland";
//
//        val addresse = "Eine Strasse 10, 85050 Irgendwo in Bayern, Deutschland";
//
//        val userStandort = UserContextTestFixtures.beispielUserContextPerson().getAktuellesStandort();
//
//        val neuesStandort = NeuerInstitutionStandort.builder()//
//                .name(name) //
//                .plz(plz) //
//                .ort(ort) //
//                .strasse(strasse) //
//                .hausnummer(hausnummer) //
//                .land(land) //
//                .build();
//        val neuesHauptstandortId = new InstitutionStandortId(UUID.randomUUID());
//
//        val longitude = 100.0;
//        val latitude = 500.0;
//        InstitutionStandortEntity neuesHauptstandortEntityOhneId = InstitutionStandortEntity.builder()//
//                .name(name) //
//                .plz(plz) //
//                .ort(ort) //
//                .strasse(strasse) //
//                .hausnummer(hausnummer) //
//                .land(land) //
//                .longitude(BigDecimal.valueOf(longitude)) //
//                .latitude(BigDecimal.valueOf(latitude)) //
//                .build();
//        InstitutionStandortEntity neuesHauptstandortEntity = InstitutionStandortEntity.builder()//
//                .id(neuesHauptstandortId.getValue()) //
//                .name(name) //
//                .plz(plz) //
//                .ort(ort) //
//                .strasse(strasse) //
//                .hausnummer(hausnummer) //
//                .land(land) //
//                .longitude(BigDecimal.valueOf(longitude)) //
//                .latitude(BigDecimal.valueOf(latitude)) //
//                .build();
//        val neuesHauptstandort = InstitutionStandort.builder()//
//                .id(neuesHauptstandortId) //
//                .name(name) //
//                .plz(plz) //
//                .ort(ort) //
//                .strasse(strasse) //
//                .hausnummer(hausnummer) //
//                .land(land) //
//                .longitude(BigDecimal.valueOf(longitude)) //
//                .latitude(BigDecimal.valueOf(latitude)) //
//                .build();
//
//        val institutionMitNeuemHauptstandort = UserContextTestFixtures.beispielUserContextInstitution();
//        institutionMitNeuemHauptstandort.setHauptstandort(neuesHauptstandort);
//        institutionMitNeuemHauptstandort.getStandorte().add(neuesHauptstandort);
//        val institutionEntityMitNeuemHauptstandort = UserContextTestFixtures.beispielUserContextInstitutionEntity();
//        institutionEntityMitNeuemHauptstandort.setHauptstandort(neuesHauptstandortEntity);
//        institutionEntityMitNeuemHauptstandort.addStandort(neuesHauptstandortEntity);
//
//        given(userService.getContextStandort()).willReturn(userStandort);
//        given(institutionStandortRepository.save(neuesHauptstandortEntityOhneId)).willReturn(neuesHauptstandortEntity);
//        given(institutionRepository.save(institutionEntityMitNeuemHauptstandort))
//                .willReturn(institutionEntityMitNeuemHauptstandort);
//        given(geocodingService.findePointsByAdressString(addresse))
//                .willReturn(Arrays.asList(new Point(latitude, longitude)));
//
//        assertEquals(institutionMitNeuemHauptstandort,
//                institutionService.userInstitutionHauptstandortHinzufuegen(neuesStandort));
//
//        then(userService).should().getContextStandort();
//        then(userService).shouldHaveNoMoreInteractions();
//        then(institutionRepository).should().save(institutionEntityMitNeuemHauptstandort);
//        then(institutionRepository).shouldHaveNoMoreInteractions();
//        then(institutionStandortRepository).should().save(neuesHauptstandortEntityOhneId);
//        then(institutionStandortRepository).shouldHaveNoMoreInteractions();
//        then(geocodingService).should().findePointsByAdressString(addresse);
//        then(geocodingService).shouldHaveNoMoreInteractions();
//    }

    @Test
    @DisplayName("neues Standort in Institution hinzufuegen koennen")
    void neues_Standort_in_Institution_hinzufuegen_koennen() {

        val name = "Mein Hauptstandort";
        val plz = "85050";
        val ort = "Irgendwo in Bayern";
        val strasse = "Eine Strasse";
        val hausnummer = "10";
        val land = "Deutschland";

        val addresse = "Eine Strasse 10, 85050 Irgendwo in Bayern, Deutschland";

        val userStandort = UserContextTestFixtures.beispielUserContextPerson().getAktuellesStandort();

        val neuesStandort = NeuerInstitutionStandort.builder()//
                .name(name) //
                .plz(plz) //
                .ort(ort) //
                .strasse(strasse) //
                .hausnummer(hausnummer) //
                .land(land) //
                .build();
        val neuesStandortId = new InstitutionStandortId(UUID.randomUUID());

        val longitude = 100.0;
        val latitude = 500.0;
        InstitutionStandortEntity neuesStandortEntityOhneId = InstitutionStandortEntity.builder()//
                .name(name) //
                .plz(plz) //
                .ort(ort) //
                .strasse(strasse) //
                .hausnummer(hausnummer) //
                .land(land) //
                .longitude(BigDecimal.valueOf(longitude)) //
                .latitude(BigDecimal.valueOf(latitude)) //
                .build();
        InstitutionStandortEntity neuesStandortEntity = InstitutionStandortEntity.builder()//
                .id(neuesStandortId.getValue()) //
                .name(name) //
                .plz(plz) //
                .ort(ort) //
                .strasse(strasse) //
                .hausnummer(hausnummer) //
                .land(land) //
                .longitude(BigDecimal.valueOf(longitude)) //
                .latitude(BigDecimal.valueOf(latitude)) //
                .build();

        val institutionMitNeuemStandort = UserContextTestFixtures.beispielUserContextInstitution();
        institutionMitNeuemStandort.getStandorte().add(InstitutionStandort.builder()//
                .id(neuesStandortId) //
                .name(name) //
                .plz(plz) //
                .ort(ort) //
                .strasse(strasse) //
                .hausnummer(hausnummer) //
                .land(land) //
                .longitude(BigDecimal.valueOf(longitude)) //
                .latitude(BigDecimal.valueOf(latitude)) //
                .build());
        val institutionEntityMitNeuemStandort = UserContextTestFixtures.beispielUserContextInstitutionEntity();
        institutionEntityMitNeuemStandort.addStandort(neuesStandortEntity);

        given(userService.getContextStandort()).willReturn(userStandort);
        given(institutionStandortRepository.save(neuesStandortEntityOhneId)).willReturn(neuesStandortEntity);
        given(institutionRepository.save(institutionEntityMitNeuemStandort))
                .willReturn(institutionEntityMitNeuemStandort);
        given(geocodingService.findePointsByAdressString(addresse))
                .willReturn(Arrays.asList(new Point(latitude, longitude)));

        assertEquals(institutionMitNeuemStandort, institutionService.userInstitutionStandortHinzufuegen(neuesStandort));

        then(userService).should().getContextStandort();
        then(userService).shouldHaveNoMoreInteractions();
        then(institutionRepository).should().save(institutionEntityMitNeuemStandort);
        then(institutionRepository).shouldHaveNoMoreInteractions();
        then(institutionStandortRepository).should().save(neuesStandortEntityOhneId);
        then(institutionStandortRepository).shouldHaveNoMoreInteractions();
        then(geocodingService).should().findePointsByAdressString(addresse);
        then(geocodingService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Institution Antrag ablehnen")
    void institution_antrag_ablehnen() {

        val personId = UUID.randomUUID();
        val antragId = UUID.randomUUID();
        InstitutionAntragEntity antrag = InstitutionAntragEntity.builder() //
                .id(antragId)
                .name("Mein Standort") //
                .strasse("Strasse") //
                .hausnummer("10a") //
                .plz("PLZ") //
                .ort("Ort") //
                .land("Land") //
                .rolle(InstitutionRolle.EMPFAENGER) //
                .status(InstitutionAntragStatus.OFFEN) //
                .institutionTyp(InstitutionTyp.ANDERE) //
                .webseite("HTTP") //
                .antragsteller(personId) //
                .build();

        given(institutionAntragJpaRepository.findById(antragId)).willReturn(Optional.ofNullable(antrag));
        given(institutionAntragJpaRepository.save(antrag)).willReturn(antrag);

        institutionService.antragAblehnen(new InstitutionAntragId(antragId));

        then(institutionAntragJpaRepository).should().save(antrag);
        then(institutionAntragJpaRepository).should().findById(antragId);
        then(institutionAntragJpaRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Institution Antrag genehmigen")
    void institution_antrag_genehmigen() {
//
//        val personId = UUID.randomUUID();
//        val antragId = UUID.randomUUID();
//        val antrag = InstitutionAntragEntity.builder() //
//                .id(antragId)
//                .name("Mein Standort") //
//                .strasse("Strasse") //
//                .hausnummer("10a") //
//                .plz("PLZ") //
//                .ort("Ort") //
//                .land("Land") //
//                .rolle(InstitutionRolle.EMPFAENGER) //
//                .status(InstitutionAntragStatus.OFFEN) //
//                .institutionTyp(InstitutionTyp.ANDERE) //
//                .webseite("HTTP") //
//                .antragsteller(personId) //
//                .build();
//
//        given(institutionAntragJpaRepository.findById(antragId)).willReturn(java.util.Optional.ofNullable(antrag));
//        given(institutionAntragJpaRepository.save(antrag)).willReturn(antrag);
//
//        institutionService.antragAblehnen(new InstitutionAntragId(antragId));
//
//        then(institutionAntragJpaRepository).should().save(antrag);
//        then(institutionAntragJpaRepository).should().findById(antragId);
//        then(institutionAntragJpaRepository).shouldHaveNoMoreInteractions();
    }
}
