package io.remedymatch.bedarf.domain.service;

import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.*;
import static io.remedymatch.bedarf.domain.service.BedarfTestFixtures.beispielBedarfId;
import static io.remedymatch.institution.domain.UserContextTestFixtures.beispielUserContextAnderesStandort;
import static io.remedymatch.institution.domain.UserContextTestFixtures.beispielUserContextAnderesStandortEntity;
import static io.remedymatch.institution.domain.UserContextTestFixtures.beispielUserContextInstitution;
import static io.remedymatch.institution.domain.UserContextTestFixtures.beispielUserContextInstitutionEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.math.BigDecimal;
import java.util.Optional;
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

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.NeuesBedarf;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.service.InstitutionTestFixtures;
import io.remedymatch.user.domain.UserService;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		BedarfAnlageService.class, //
		BedarfJpaRepository.class, //
		UserService.class, //
		ArtikelSucheService.class, //
		GeoCalcService.class, //
})
@Tag("Spring")
@DisplayName("BedarfAnlageService soll")
class BedarfAnlageServiceShould {

	@Autowired
	private BedarfAnlageService bedarfAnlageService;

	@MockBean
	private BedarfJpaRepository bedarfRepository;

	@MockBean
	private UserService userService;

	@MockBean
	private ArtikelSucheService artikelSucheService;

	@MockBean
	private GeoCalcService geoCalcService;

	@Test
	@DisplayName("Fehler werfen wenn beide Artikel und ArtikelVariante leer sind")
	void fehler_werfen_wenn_beide_Artikel_und_Artikel_Variante_leer_sind() {
		assertThrows(OperationNotAlloudException.class, //
				() -> bedarfAnlageService.neuesBedarfEinstellen(NeuesBedarf.builder().build()));
	}
	
	@Test
	@DisplayName("Fehler werfen bei nicht existierentem Artikel")
	void fehler_werfen_bei_Bearbeitung_von_nicht_existierendem_Artikel() {
		assertThrows(ObjectNotFoundException.class, //
				() -> bedarfAnlageService.getArtikel(new ArtikelId(UUID.randomUUID())));
	}

	@Test
	@DisplayName("Fehler werfen bei nicht existierende ArtikelVariante")
	void fehler_werfen_bei_Bearbeitung_von_nicht_existierende_ArtikelVariante() {
		assertThrows(ObjectNotFoundException.class, //
				() -> bedarfAnlageService.getArtikelVariante(new ArtikelVarianteId(UUID.randomUUID())));
	}

	@Test
	@DisplayName("Fehler werfen wenn der Standort nicht in UserContext Institution gefunden wird")
	void fehler_werfen_wenn_der_Standort_nicht_in_UserContext_Institution_gefunden_wird() {

		assertThrows(NotUserInstitutionObjectException.class, //
				() -> bedarfAnlageService.getUserInstitutionStandort(
						InstitutionTestFixtures.beispielInstitutionEntity(),
						new InstitutionStandortId(UUID.randomUUID())));
	}

	@Test
	@DisplayName("neues Bedarf fuer Artikel anlegen koennen")
	void neues_Bedarf_fuer_Artikel_anlegen_koennen() {

		val artikelId = beispielArtikelId();
		val artikel= beispielArtikel();
		val artikelEntity = beispielArtikelEntity();

		val anzahl = BigDecimal.valueOf(100);
		val steril = true;
		val medizinisch = true;
		val kommentar = "Neues Bedarf";

		val userInstitution = beispielUserContextInstitution();
		val userInstitutionEntity = beispielUserContextInstitutionEntity();
		val userStandort = beispielUserContextAnderesStandort();
		val userStandortEntity = beispielUserContextAnderesStandortEntity();

		val bedarfEntityOhneId = BedarfEntity.builder() //
				.artikel(artikelEntity) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.institution(userInstitutionEntity) //
				.standort(userStandortEntity) //
				.steril(steril) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val bedarfId = beispielBedarfId();

		val bedarfEntityMitId = BedarfEntity.builder() //
				.id(bedarfId.getValue()) //
				.artikel(artikelEntity) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.institution(userInstitutionEntity) //
				.standort(userStandortEntity) //
				.steril(steril) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val neueBedarf = NeuesBedarf.builder() //
				.artikelId(artikelId) //
				.anzahl(anzahl) //
				.standortId(userStandort.getId()) //
				.steril(steril) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val entfernung = BigDecimal.valueOf(123664);

		given(userService.getContextInstitution()).willReturn(userInstitution);
		given(geoCalcService.berechneUserDistanzInKilometer(userStandort)).willReturn(entfernung);
		given(artikelSucheService.findArtikel(artikelId)).willReturn(Optional.of(artikel));
		given(bedarfRepository.save(bedarfEntityOhneId)).willReturn(bedarfEntityMitId);

		val expectedBedarf = Bedarf.builder() //
				.id(bedarfId) //
				.artikel(artikel) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.institution(userInstitution) //
				.standort(userStandort) //
				.steril(steril) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.entfernung(entfernung) //
				.build();

		assertEquals(expectedBedarf, bedarfAnlageService.neuesBedarfEinstellen(neueBedarf));

		then(bedarfRepository).should().save(bedarfEntityOhneId);
		then(bedarfRepository).shouldHaveNoMoreInteractions();
		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(artikelSucheService).should().findArtikel(artikelId);
		then(artikelSucheService).shouldHaveNoMoreInteractions();
		then(geoCalcService).should().berechneUserDistanzInKilometer(userStandort);
		then(geoCalcService).shouldHaveNoMoreInteractions();
	}
	
	@Test
	@DisplayName("neues Bedarf fuer ArtikelVariante anlegen koennen")
	void neues_Bedarf_fuer_Artikel_Variante_anlegen_koennen() {

		val artikelVarianteId = beispielArtikelVarianteId();
		val artikelVariante = beispielArtikelVariante();
		val artikelVarianteEntity = beispielArtikelVarianteEntity();

		val artikelId = artikelVariante.getArtikelId();
		val artikel = beispielArtikel();
		artikel.setId(artikelId);
		val artikelEntity = beispielArtikelEntity();
		artikelEntity.setId(artikelId.getValue());
		
		val anzahl = BigDecimal.valueOf(100);
		val steril = true;
		val medizinisch = true;
		val kommentar = "Neues Bedarf";

		val userInstitution = beispielUserContextInstitution();
		val userInstitutionEntity = beispielUserContextInstitutionEntity();
		val userStandort = beispielUserContextAnderesStandort();
		val userStandortEntity = beispielUserContextAnderesStandortEntity();

		val bedarfEntityOhneId = BedarfEntity.builder() //
				.artikel(artikelEntity) //
				.artikelVariante(artikelVarianteEntity) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.institution(userInstitutionEntity) //
				.standort(userStandortEntity) //
				.steril(steril) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val bedarfId = beispielBedarfId();

		val bedarfEntityMitId = BedarfEntity.builder() //
				.id(bedarfId.getValue()) //
				.artikel(artikelEntity) //
				.artikelVariante(artikelVarianteEntity) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.institution(userInstitutionEntity) //
				.standort(userStandortEntity) //
				.steril(steril) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val neueBedarf = NeuesBedarf.builder() //
				.artikelVarianteId(artikelVarianteId) //
				.anzahl(anzahl) //
				.standortId(userStandort.getId()) //
				.steril(steril) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val entfernung = BigDecimal.valueOf(123664);

		given(userService.getContextInstitution()).willReturn(userInstitution);
		given(geoCalcService.berechneUserDistanzInKilometer(userStandort)).willReturn(entfernung);
		given(artikelSucheService.findArtikelVariante(artikelVarianteId)).willReturn(Optional.of(artikelVariante));
		given(artikelSucheService.findArtikel(artikelId)).willReturn(Optional.of(artikel));
		given(bedarfRepository.save(bedarfEntityOhneId)).willReturn(bedarfEntityMitId);

		val expectedBedarf = Bedarf.builder() //
				.id(bedarfId) //
				.artikel(artikel) //
				.artikelVariante(artikelVariante) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.institution(userInstitution) //
				.standort(userStandort) //
				.steril(steril) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.entfernung(entfernung) //
				.build();

		assertEquals(expectedBedarf, bedarfAnlageService.neuesBedarfEinstellen(neueBedarf));

		then(bedarfRepository).should().save(bedarfEntityOhneId);
		then(bedarfRepository).shouldHaveNoMoreInteractions();
		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(artikelSucheService).should().findArtikelVariante(artikelVarianteId);
		then(artikelSucheService).should().findArtikel(artikelId);
		then(artikelSucheService).shouldHaveNoMoreInteractions();
		then(geoCalcService).should().berechneUserDistanzInKilometer(userStandort);
		then(geoCalcService).shouldHaveNoMoreInteractions();
	}
}
