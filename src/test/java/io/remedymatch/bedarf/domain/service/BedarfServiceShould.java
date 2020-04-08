package io.remedymatch.bedarf.domain.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.BedarfId;
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
		BedarfService.class, //
		UserService.class, //
		ArtikelSucheService.class, //
		InstitutionStandortRepository.class, //
		BedarfRepository.class, //
		BedarfAnfrageRepository.class, //
		GeoCalcService.class, //
		EngineClient.class //
})
@Tag("Spring")
@DisplayName("BedarfService soll")
@Disabled
public class BedarfServiceShould {
	@Autowired
	private BedarfService bedarfService;

	@MockBean
	private UserService userService;

	@MockBean
	private ArtikelSucheService artikelSucheService;

	@MockBean
	private InstitutionStandortRepository institutionStandortRepository;

	@MockBean
	private BedarfRepository bedarfRepository;

	@MockBean
	private BedarfAnfrageRepository bedarfAnfrageRepository;

	@MockBean
	private EngineClient engineClient;

	@MockBean
	private GeoCalcService geoCalcService;

	@Test
	@DisplayName("alle nicht bediente Bedarfe mit Entfernung liefern")
	void alle_nicht_bediente_Bedarfe_mit_Entfernung_liefern() {
		val meinStandort = standort("Mein Standort");
		val bedarfStandort = standort("Bedarf Standort");
		BigDecimal entfernungDerStandorte = BigDecimal.valueOf(12);

		BedarfId bedarfId = bedarfId();
		given(bedarfRepository.getAlleNichtBedienteBedarfe())
				.willReturn(Arrays.asList(bedarf(bedarfId, bedarfStandort)));
		given(userService.getContextInstitution()).willReturn(institution(meinStandort));
		given(geoCalcService.berechneDistanzInKilometer(meinStandort, bedarfStandort))
				.willReturn(entfernungDerStandorte);

		val erwarteteBedarf = bedarf(bedarfId, bedarfStandort);
		erwarteteBedarf.setEntfernung(entfernungDerStandorte);

		// FIXME
//		assertEquals(Arrays.asList(erwarteteBedarf), bedarfService.AlleNichtBedienteBedarfe());

		then(bedarfRepository).should().getAlleNichtBedienteBedarfe();
		then(bedarfRepository).shouldHaveNoMoreInteractions();
		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(geoCalcService).should().berechneDistanzInKilometer(meinStandort, bedarfStandort);
		then(geoCalcService).shouldHaveNoMoreInteractions();

		then(artikelSucheService).shouldHaveNoInteractions();
		then(institutionStandortRepository).shouldHaveNoInteractions();
		then(bedarfAnfrageRepository).shouldHaveNoInteractions();
		then(engineClient).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("alle Bedarfe der User-Institution mit Entfernung liefern")
	void alle_Bedarfe_einer_Institution_mit_Entfernung_liefern() {
		val meinStandort = standort("Mein Standort");
		val meineInstitution = institution(meinStandort);
		val meineInstitutionId = meineInstitution.getId();
		val bedarfStandort = standort("Bedarf Standort");
		BigDecimal entfernungDerStandorte = BigDecimal.valueOf(12);

		BedarfId bedarfId = bedarfId();
		given(bedarfRepository.getBedarfeVonInstitution(meineInstitutionId))
				.willReturn(Arrays.asList(bedarf(bedarfId, bedarfStandort)));
		given(userService.getContextInstitution()).willReturn(meineInstitution);
		given(geoCalcService.berechneDistanzInKilometer(meinStandort, bedarfStandort))
				.willReturn(entfernungDerStandorte);

		val erwarteteBedarf = bedarf(bedarfId, bedarfStandort);
		erwarteteBedarf.setEntfernung(entfernungDerStandorte);

		// FIXME
//		assertEquals(Arrays.asList(erwarteteBedarf), bedarfService.getBedarfeDerUserInstitution());

		then(bedarfRepository).should().getBedarfeVonInstitution(meineInstitutionId);
		then(bedarfRepository).shouldHaveNoMoreInteractions();
		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(geoCalcService).should().berechneDistanzInKilometer(meinStandort, bedarfStandort);
		then(geoCalcService).shouldHaveNoMoreInteractions();

		then(artikelSucheService).shouldHaveNoInteractions();
		then(institutionStandortRepository).shouldHaveNoInteractions();
		then(bedarfAnfrageRepository).shouldHaveNoInteractions();
		then(engineClient).shouldHaveNoInteractions();
	}

//	then(userService).shouldHaveNoInteractions();
//	then(artikelRepository).shouldHaveNoInteractions();
//	then(institutionStandortRepository).shouldHaveNoInteractions();
//	then(bedarfRepository).shouldHaveNoInteractions();
//	then(bedarfAnfrageRepository).shouldHaveNoInteractions();
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

	private BedarfId bedarfId() {
		return new BedarfId(UUID.randomUUID());
	}

	private Bedarf bedarf(//
			final BedarfId bedarfId, //
			final InstitutionStandort standort) {
		return Bedarf.builder() //
				.id(bedarfId) //
				.standort(standort) //
				.build();
	}
}
