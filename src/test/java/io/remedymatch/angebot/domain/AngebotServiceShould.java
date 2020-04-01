package io.remedymatch.angebot.domain;

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

import io.remedymatch.artikel.domain.ArtikelRepository;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.institution.domain.InstitutionStandortRepository;
import io.remedymatch.user.domain.UserService;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		AngebotService.class, //
		UserService.class, //
		ArtikelRepository.class, //
		InstitutionStandortRepository.class, //
		AngebotRepository.class, //
		AngebotAnfrageRepository.class, //
		GeoCalcService.class, //
		EngineClient.class //
})
@Tag("Spring")
@DisplayName("AngebotService soll")
public class AngebotServiceShould {
	@Autowired
	private AngebotService angebotService;

	@MockBean
	private UserService userService;

	@MockBean
	private ArtikelRepository artikelRepository;

	@MockBean
	private InstitutionStandortRepository institutionStandortRepository;

	@MockBean
	private AngebotRepository angebotRepository;

	@MockBean
	private AngebotAnfrageRepository angebotAnfrageRepository;

	@MockBean
	private EngineClient engineClient;

	@MockBean
	private GeoCalcService geoCalcService;

	@Test
	@DisplayName("alle nicht bediente Angebote mit Entfernung liefern")
	void alle_nicht_bediente_Angebote_mit_Entfernung_liefern() {
		val meinStandort = standort("Mein Standort");
		val angebotStandort = standort("Angebot Standort");
		BigDecimal entfernungDerStandorte = BigDecimal.valueOf(12);

		AngebotId angebotId = angebotId();
		given(angebotRepository.getAlleNichtBedienteAngebote())
				.willReturn(Arrays.asList(angebot(angebotId, angebotStandort)));
		given(userService.getContextInstitution()).willReturn(institution(meinStandort));
		given(geoCalcService.berechneDistanzInKilometer(meinStandort, angebotStandort))
				.willReturn(entfernungDerStandorte);

		val erwarteteAngebot = angebot(angebotId, angebotStandort);
		erwarteteAngebot.setEntfernung(entfernungDerStandorte);

		assertEquals(Arrays.asList(erwarteteAngebot), angebotService.getAlleNichtBedienteAngebote());

		then(angebotRepository).should().getAlleNichtBedienteAngebote();
		then(angebotRepository).shouldHaveNoMoreInteractions();
		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(geoCalcService).should().berechneDistanzInKilometer(meinStandort, angebotStandort);
		then(geoCalcService).shouldHaveNoMoreInteractions();

		then(artikelRepository).shouldHaveNoInteractions();
		then(institutionStandortRepository).shouldHaveNoInteractions();
		then(angebotAnfrageRepository).shouldHaveNoInteractions();
		then(engineClient).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("alle Angebote der User-Institution mit Entfernung liefern")
	void alle_Angebote_einer_Institution_mit_Entfernung_liefern() {
		val meinStandort = standort("Mein Standort");
		val meineInstitution = institution(meinStandort);
		val meineInstitutionId = meineInstitution.getId();
		val angebotStandort = standort("Angebot Standort");
		BigDecimal entfernungDerStandorte = BigDecimal.valueOf(12);

		AngebotId angebotId = angebotId();
		given(angebotRepository.getAngeboteVonInstitution(meineInstitutionId))
				.willReturn(Arrays.asList(angebot(angebotId, angebotStandort)));
		given(userService.getContextInstitution()).willReturn(meineInstitution);
		given(geoCalcService.berechneDistanzInKilometer(meinStandort, angebotStandort))
				.willReturn(entfernungDerStandorte);

		val erwarteteAngebot = angebot(angebotId, angebotStandort);
		erwarteteAngebot.setEntfernung(entfernungDerStandorte);

		assertEquals(Arrays.asList(erwarteteAngebot), angebotService.getAngeboteDerUserInstitution());

		then(angebotRepository).should().getAngeboteVonInstitution(meineInstitutionId);
		then(angebotRepository).shouldHaveNoMoreInteractions();
		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(geoCalcService).should().berechneDistanzInKilometer(meinStandort, angebotStandort);
		then(geoCalcService).shouldHaveNoMoreInteractions();

		then(artikelRepository).shouldHaveNoInteractions();
		then(institutionStandortRepository).shouldHaveNoInteractions();
		then(angebotAnfrageRepository).shouldHaveNoInteractions();
		then(engineClient).shouldHaveNoInteractions();
	}

//	then(userService).shouldHaveNoInteractions();
//	then(artikelRepository).shouldHaveNoInteractions();
//	then(institutionStandortRepository).shouldHaveNoInteractions();
//	then(angebotRepository).shouldHaveNoInteractions();
//	then(angebotAnfrageRepository).shouldHaveNoInteractions();
//	then(geoCalcService).shouldHaveNoInteractions();
//	then(engineClient).shouldHaveNoInteractions();

	/* help method */

	private Institution institution(final InstitutionStandort hauptstandort) {
		return Institution.builder() //
				.id(new InstitutionId(UUID.randomUUID())) //
				.hauptstandort(hauptstandort) //
				.build();
	}

	private InstitutionStandort standort(final String name) {
		return InstitutionStandort.builder() //
				.id(new InstitutionStandortId(UUID.randomUUID())) //
				.name(name)//
				.build();
	}

	private AngebotId angebotId() {
		return new AngebotId(UUID.randomUUID());
	}

	private Angebot angebot(//
			final AngebotId angebotId, //
			final InstitutionStandort standort) {
		return Angebot.builder() //
				.id(angebotId) //
				.standort(standort) //
				.build();
	}
}
