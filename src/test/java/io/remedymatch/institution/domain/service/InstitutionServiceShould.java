package io.remedymatch.institution.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.geodaten.domain.StandortService;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.institution.domain.UserContextTestFixtures;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.domain.model.InstitutionUpdate;
import io.remedymatch.institution.domain.model.NeuesInstitutionStandort;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortJpaRepository;
import io.remedymatch.user.domain.UserService;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		InstitutionService.class, //
		UserService.class, //
		InstitutionJpaRepository.class, //
		InstitutionStandortJpaRepository.class, //
		StandortService.class //
})
@Tag("Spring")
@DisplayName("InstitutionService soll")
class InstitutionServiceShould {

	@Autowired
	private InstitutionService institutionService;

	@MockBean
	private UserService userService;

	@MockBean
	private InstitutionJpaRepository institutionRepository;

	@MockBean
	private InstitutionStandortJpaRepository institutionStandortRepository;

	@MockBean
	private StandortService standortService;

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

	@Test
	@DisplayName("Fehler werfen bei update der Institution nichts zu aktualisieren gibt")
	void fehler_werfen_wenn_bei_update_der_Institution_nichts_zu_aktualisieren_gibt() {
		assertThrows(OperationNotAlloudException.class, //
				() -> institutionService.userInstitutionAktualisieren(new InstitutionUpdate()));
	}

	@Test
	@DisplayName("Name der Institution aktualisieren")
	void name_der_Institution_aktualisieren() {

		val userInstitution = UserContextTestFixtures.beispielUserContextInstitution();

		val institutionMitNeuemName = UserContextTestFixtures.beispielUserContextInstitution();
		institutionMitNeuemName.setName("Neue Name");
		val institutionEntityMitNeuemName = UserContextTestFixtures.beispielUserContextInstitutionEntity();
		institutionEntityMitNeuemName.setName("Neue Name");
		
		given(userService.getContextInstitution()).willReturn(userInstitution);
		given(institutionRepository.save(institutionEntityMitNeuemName)).willReturn(institutionEntityMitNeuemName);

		assertEquals(institutionMitNeuemName, institutionService.userInstitutionAktualisieren(
				InstitutionUpdate.builder().neueName("Neue Name").build()));

		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(institutionRepository).should().save(institutionEntityMitNeuemName);
		then(institutionRepository).shouldHaveNoMoreInteractions();
		then(institutionStandortRepository).shouldHaveNoInteractions();
		then(standortService).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("Typ der Institution aktualisieren")
	void typ_der_Institution_aktualisieren() {
		
		val userInstitution = UserContextTestFixtures.beispielUserContextInstitution();

		val institutionMitNeuemTyp = UserContextTestFixtures.beispielUserContextInstitution();
		institutionMitNeuemTyp.setTyp(InstitutionTyp.KRANKENHAUS);
		val institutionEntityMitNeuemTyp = UserContextTestFixtures.beispielUserContextInstitutionEntity();
		institutionEntityMitNeuemTyp.setTyp(InstitutionTyp.KRANKENHAUS);
		
		given(userService.getContextInstitution()).willReturn(userInstitution);
		given(institutionRepository.save(institutionEntityMitNeuemTyp)).willReturn(institutionEntityMitNeuemTyp);

		assertEquals(institutionMitNeuemTyp, institutionService.userInstitutionAktualisieren(
				InstitutionUpdate.builder().neuesTyp(InstitutionTyp.KRANKENHAUS).build()));

		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(institutionRepository).should().save(institutionEntityMitNeuemTyp);
		then(institutionRepository).shouldHaveNoMoreInteractions();
		then(institutionStandortRepository).shouldHaveNoInteractions();
		then(standortService).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("Hauptstandort der Institution aktualisieren")
	void hauptstandort_der_Institution_aktualisieren() {

		val anderesStandortId = new InstitutionStandortId(UUID.randomUUID());
		val anderesStandort = InstitutionTestFixtures.beispielStandort2();
		anderesStandort.setId(anderesStandortId);
		val anderesStandortEntity = InstitutionTestFixtures.beispielStandort2Entity();
		anderesStandortEntity.setId(anderesStandortId.getValue());

		val userInstitution = UserContextTestFixtures.beispielUserContextInstitution();
		userInstitution.getStandorte().add(anderesStandort);

		val institutionMitNeuemHauptstandort = UserContextTestFixtures.beispielUserContextInstitution();
		institutionMitNeuemHauptstandort.setHauptstandort(anderesStandort);
		institutionMitNeuemHauptstandort.getStandorte().add(anderesStandort);
		val institutionEntityMitNeuemHauptstandort = UserContextTestFixtures.beispielUserContextInstitutionEntity();
		institutionEntityMitNeuemHauptstandort.setHauptstandort(anderesStandortEntity);
		institutionEntityMitNeuemHauptstandort.addStandort(anderesStandortEntity);

		given(userService.getContextInstitution()).willReturn(userInstitution);
		given(institutionRepository.save(institutionEntityMitNeuemHauptstandort)).willReturn(institutionEntityMitNeuemHauptstandort);

		assertEquals(institutionMitNeuemHauptstandort, institutionService.userInstitutionAktualisieren(
				InstitutionUpdate.builder().neuesHauptstandortId(anderesStandortId).build()));

		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(institutionRepository).should().save(institutionEntityMitNeuemHauptstandort);
		then(institutionRepository).shouldHaveNoMoreInteractions();
		then(institutionStandortRepository).shouldHaveNoInteractions();
		then(standortService).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("neues Hauptstandort in Institution hinzufuegen koennen")
	void neues_Hauptstandort_in_Institution_hinzufuegen_koennen() {

		val name = "Mein Hauptstandort";
		val plz = "85050";
		val ort = "Irgendwo in Bayern";
		val strasse = "Eine Strasse";
		val hausnummer = "10";
		val land = "Deutschland";

		val addresse = "Eine Strasse 10, 85050 Irgendwo in Bayern, Deutschland";

		val userInstitution = UserContextTestFixtures.beispielUserContextInstitution();

		val neuesStandort = NeuesInstitutionStandort.builder()//
				.name(name) //
				.plz(plz) //
				.ort(ort) //
				.strasse(strasse) //
				.hausnummer(hausnummer) //
				.land(land) //
				.build();
		val neuesHauptstandortId = new InstitutionStandortId(UUID.randomUUID());

		val longitude = 100.0;
		val latitude = 500.0;
		val neuesHauptstandortEntityOhneId = InstitutionStandortEntity.builder()//
				.name(name) //
				.plz(plz) //
				.ort(ort) //
				.strasse(strasse) //
				.hausnummer(hausnummer) //
				.land(land) //
				.longitude(BigDecimal.valueOf(longitude)) //
				.latitude(BigDecimal.valueOf(latitude)) //
				.build();
		val neuesHauptstandortEntity = InstitutionStandortEntity.builder()//
				.id(neuesHauptstandortId.getValue()) //
				.name(name) //
				.plz(plz) //
				.ort(ort) //
				.strasse(strasse) //
				.hausnummer(hausnummer) //
				.land(land) //
				.longitude(BigDecimal.valueOf(longitude)) //
				.latitude(BigDecimal.valueOf(latitude)) //
				.build();
		val neuesHauptstandort = InstitutionStandort.builder()//
				.id(neuesHauptstandortId) //
				.name(name) //
				.plz(plz) //
				.ort(ort) //
				.strasse(strasse) //
				.hausnummer(hausnummer) //
				.land(land) //
				.longitude(BigDecimal.valueOf(longitude)) //
				.latitude(BigDecimal.valueOf(latitude)) //
				.build();

		val institutionMitNeuemHauptstandort = UserContextTestFixtures.beispielUserContextInstitution();
		institutionMitNeuemHauptstandort.setHauptstandort(neuesHauptstandort);
		institutionMitNeuemHauptstandort.getStandorte().add(neuesHauptstandort);
		val institutionEntityMitNeuemHauptstandort = UserContextTestFixtures.beispielUserContextInstitutionEntity();
		institutionEntityMitNeuemHauptstandort.setHauptstandort(neuesHauptstandortEntity);
		institutionEntityMitNeuemHauptstandort.addStandort(neuesHauptstandortEntity);

		given(userService.getContextInstitution()).willReturn(userInstitution);
		given(institutionStandortRepository.save(neuesHauptstandortEntityOhneId)).willReturn(neuesHauptstandortEntity);
		given(institutionRepository.save(institutionEntityMitNeuemHauptstandort))
				.willReturn(institutionEntityMitNeuemHauptstandort);
		given(standortService.findePointsByAdressString(addresse))
				.willReturn(Arrays.asList(new Point(latitude, longitude)));

		assertEquals(institutionMitNeuemHauptstandort,
				institutionService.userInstitutionHauptstandortHinzufuegen(neuesStandort));

		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(institutionRepository).should().save(institutionEntityMitNeuemHauptstandort);
		then(institutionRepository).shouldHaveNoMoreInteractions();
		then(institutionStandortRepository).should().save(neuesHauptstandortEntityOhneId);
		then(institutionStandortRepository).shouldHaveNoMoreInteractions();
		then(standortService).should().findePointsByAdressString(addresse);
		then(standortService).shouldHaveNoMoreInteractions();
	}

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

		val userInstitution = UserContextTestFixtures.beispielUserContextInstitution();

		val neuesStandort = NeuesInstitutionStandort.builder()//
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
		val neuesStandortEntityOhneId = InstitutionStandortEntity.builder()//
				.name(name) //
				.plz(plz) //
				.ort(ort) //
				.strasse(strasse) //
				.hausnummer(hausnummer) //
				.land(land) //
				.longitude(BigDecimal.valueOf(longitude)) //
				.latitude(BigDecimal.valueOf(latitude)) //
				.build();
		val neuesStandortEntity = InstitutionStandortEntity.builder()//
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

		given(userService.getContextInstitution()).willReturn(userInstitution);
		given(institutionStandortRepository.save(neuesStandortEntityOhneId)).willReturn(neuesStandortEntity);
		given(institutionRepository.save(institutionEntityMitNeuemStandort))
				.willReturn(institutionEntityMitNeuemStandort);
		given(standortService.findePointsByAdressString(addresse))
				.willReturn(Arrays.asList(new Point(latitude, longitude)));

		assertEquals(institutionMitNeuemStandort, institutionService.userInstitutionStandortHinzufuegen(neuesStandort));

		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(institutionRepository).should().save(institutionEntityMitNeuemStandort);
		then(institutionRepository).shouldHaveNoMoreInteractions();
		then(institutionStandortRepository).should().save(neuesStandortEntityOhneId);
		then(institutionStandortRepository).shouldHaveNoMoreInteractions();
		then(standortService).should().findePointsByAdressString(addresse);
		then(standortService).shouldHaveNoMoreInteractions();
	}
}
