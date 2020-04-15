package io.remedymatch.institution.domain.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.service.AngebotAnfrageSucheService;
import io.remedymatch.angebot.domain.service.AngebotAnfrageTestFixtures;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageSucheService;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageTestFixtures;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.usercontext.UserContextService;
import io.remedymatch.usercontext.UserContextTestFixtures;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		InstitutionAnfragenSucheService.class, //
		UserContextService.class, //
		AngebotAnfrageSucheService.class, //
		BedarfAnfrageSucheService.class, //
		GeoCalcService.class //
})
@Tag("Spring")
@DisplayName("InstitutionAnfragenSucheService soll")
class InstitutionAnfragenSucheServiceShould {

	@Autowired
	private InstitutionAnfragenSucheService anfragenSucheService;

	@MockBean
	private UserContextService userService;

	@MockBean
	private AngebotAnfrageSucheService angebotAnfrageSucheService;

	@MockBean
	private BedarfAnfrageSucheService bedarfAnfrageSucheService;

	@MockBean
	private GeoCalcService geoCalcService;

	@Test
	@DisplayName("alle gestellte Anfragen der User Institution zurueckliefern")
	void alle_gestellte_Anfragen_der_Institution_zurueckliefern() {
		val userInstitutionId = UserContextTestFixtures.beispielUserContextInstitutionId();

		val angebotAnfrage = AngebotAnfrageTestFixtures.beispielAngebotAnfrage();
		val angebotAnfrageEntfernung = BigDecimal.valueOf(123);
		val kovnertierteAngebotAnfrage = InstitutionAnfrageConverter.convertAngebotAnfrage(angebotAnfrage);
		kovnertierteAngebotAnfrage.setEntfernung(angebotAnfrageEntfernung);

		val bedarfAnfrage = BedarfAnfrageTestFixtures.beispielBedarfAnfrage();
		val bedarfAnfrageEntfernung = BigDecimal.valueOf(555);
		val kovnertierteBedarfAnfrage = InstitutionAnfrageConverter.convertBedarfAnfrage(bedarfAnfrage);
		kovnertierteBedarfAnfrage.setEntfernung(bedarfAnfrageEntfernung);

		given(userService.getContextInstitutionId()).willReturn(userInstitutionId);
		given(angebotAnfrageSucheService.findAlleAnfragenDerInstitution(userInstitutionId))
				.willReturn(Arrays.asList(angebotAnfrage));
		given(bedarfAnfrageSucheService.findAlleAnfragenDerInstitution(userInstitutionId))
				.willReturn(Arrays.asList(BedarfAnfrageTestFixtures.beispielBedarfAnfrage()));
		given(geoCalcService.berechneDistanzInKilometer(angebotAnfrage.getStandort(),
				angebotAnfrage.getAngebot().getStandort())).willReturn(angebotAnfrageEntfernung);
		given(geoCalcService.berechneDistanzInKilometer(bedarfAnfrage.getStandort(),
				bedarfAnfrage.getBedarf().getStandort())).willReturn(bedarfAnfrageEntfernung);

		assertThat(//
				anfragenSucheService.findAlleGestellteUserInstitutionAnfragen(), //
				containsInAnyOrder(kovnertierteAngebotAnfrage, kovnertierteBedarfAnfrage));

		then(userService).should().getContextInstitutionId();
		then(userService).shouldHaveNoMoreInteractions();
		then(angebotAnfrageSucheService).should().findAlleAnfragenDerInstitution(userInstitutionId);
		then(angebotAnfrageSucheService).shouldHaveNoMoreInteractions();
		then(bedarfAnfrageSucheService).should().findAlleAnfragenDerInstitution(userInstitutionId);
		then(bedarfAnfrageSucheService).shouldHaveNoMoreInteractions();
		then(geoCalcService).should(times(2)).berechneDistanzInKilometer(any(InstitutionStandort.class),
				any(InstitutionStandort.class));
		then(geoCalcService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("alle erhaltene Anfragen der User Institution zurueckliefern")
	void alle_erhaltene_Anfragen_der_Institution_zurueckliefern() {

		val userInstitutionId = UserContextTestFixtures.beispielUserContextInstitutionId();

		val angebotAnfrage = AngebotAnfrageTestFixtures.beispielAngebotAnfrage();
		val angebotAnfrageEntfernung = BigDecimal.valueOf(123);
		val kovnertierteAngebotAnfrage = InstitutionAnfrageConverter.convertAngebotAnfrage(angebotAnfrage);
		kovnertierteAngebotAnfrage.setEntfernung(angebotAnfrageEntfernung);

		val bedarfAnfrage = BedarfAnfrageTestFixtures.beispielBedarfAnfrage();
		val bedarfAnfrageEntfernung = BigDecimal.valueOf(555);
		val kovnertierteBedarfAnfrage = InstitutionAnfrageConverter.convertBedarfAnfrage(bedarfAnfrage);
		kovnertierteBedarfAnfrage.setEntfernung(bedarfAnfrageEntfernung);

		given(userService.getContextInstitutionId()).willReturn(userInstitutionId);
		given(angebotAnfrageSucheService.findAlleAnfragenDerAngebotInstitution(userInstitutionId))
				.willReturn(Arrays.asList(angebotAnfrage));
		given(bedarfAnfrageSucheService.findAlleAnfragenDerBedarfInstitution(userInstitutionId))
				.willReturn(Arrays.asList(BedarfAnfrageTestFixtures.beispielBedarfAnfrage()));
		given(geoCalcService.berechneDistanzInKilometer(angebotAnfrage.getStandort(),
				angebotAnfrage.getAngebot().getStandort())).willReturn(angebotAnfrageEntfernung);
		given(geoCalcService.berechneDistanzInKilometer(bedarfAnfrage.getStandort(),
				bedarfAnfrage.getBedarf().getStandort())).willReturn(bedarfAnfrageEntfernung);

		assertThat(//
				anfragenSucheService.findAlleErhalteneUserInstitutionAnfragen(), //
				containsInAnyOrder(kovnertierteAngebotAnfrage, kovnertierteBedarfAnfrage));

		then(userService).should().getContextInstitutionId();
		then(userService).shouldHaveNoMoreInteractions();
		then(angebotAnfrageSucheService).should().findAlleAnfragenDerAngebotInstitution(userInstitutionId);
		then(angebotAnfrageSucheService).shouldHaveNoMoreInteractions();
		then(bedarfAnfrageSucheService).should().findAlleAnfragenDerBedarfInstitution(userInstitutionId);
		then(bedarfAnfrageSucheService).shouldHaveNoMoreInteractions();
		then(geoCalcService).should(times(2)).berechneDistanzInKilometer(any(InstitutionStandort.class),
				any(InstitutionStandort.class));
		then(geoCalcService).shouldHaveNoMoreInteractions();
	}
}
