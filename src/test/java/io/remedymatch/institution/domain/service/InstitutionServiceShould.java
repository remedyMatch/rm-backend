package io.remedymatch.institution.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import io.remedymatch.geodaten.domain.StandortService;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.institution.domain.UserContextTestFixtures;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
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

//		institutionService.userInstitutionStandortHinzufuegen(neuesStandort);
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
