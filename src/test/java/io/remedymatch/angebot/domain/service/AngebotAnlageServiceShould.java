package io.remedymatch.angebot.domain.service;

import static io.remedymatch.angebot.domain.service.AngebotTestFixtures.beispielAngebotId;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVariante;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVarianteEntity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVarianteId;
import static io.remedymatch.institution.domain.UserContextTestFixtures.beispielUserContextAnderesStandort;
import static io.remedymatch.institution.domain.UserContextTestFixtures.beispielUserContextAnderesStandortEntity;
import static io.remedymatch.institution.domain.UserContextTestFixtures.beispielUserContextInstitution;
import static io.remedymatch.institution.domain.UserContextTestFixtures.beispielUserContextInstitutionEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.NeuesAngebot;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.service.InstitutionTestFixtures;
import io.remedymatch.user.domain.UserService;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		AngebotAnlageService.class, //
		AngebotJpaRepository.class, //
		UserService.class, //
		ArtikelSucheService.class, //
		GeoCalcService.class, //
})
@Tag("Spring")
@DisplayName("AngebotAnlageService soll")
class AngebotAnlageServiceShould {

	@Autowired
	private AngebotAnlageService angebotAnlageService;

	@MockBean
	private AngebotJpaRepository angebotRepository;

	@MockBean
	private UserService userService;

	@MockBean
	private ArtikelSucheService artikelSucheService;

	@MockBean
	private GeoCalcService geoCalcService;

	@Test
	@DisplayName("Fehler werfen bei nicht existierende ArtikelVariante")
	void fehler_werfen_bei_Bearbeitung_von_nicht_existierende_ArtikelVariante() {
		assertThrows(ObjectNotFoundException.class, //
				() -> angebotAnlageService.getArtikelVariante(new ArtikelVarianteId(UUID.randomUUID())));
	}
	
	@Test
	@DisplayName("Fehler werfen wenn der Standort nicht in UserContext Institution gefunden wird")
	void fehler_werfen_wenn_der_Standort_nicht_in_UserContext_Institution_gefunden_wird() {

		assertThrows(NotUserInstitutionObjectException.class, //
				() -> angebotAnlageService.getUserInstitutionStandort(InstitutionTestFixtures.beispielInstitutionEntity(),
						new InstitutionStandortId(UUID.randomUUID())));
	}
	
	@Test
	@DisplayName("Angebot anlegen koennen")
	void angebot_anlegen_koennen() {

		val artikelVarianteId = beispielArtikelVarianteId();
		val artikelVariante = beispielArtikelVariante();
		val artikelVarianteEntity = beispielArtikelVarianteEntity();

		val anzahl = BigDecimal.valueOf(100);
		val haltbarkeit = LocalDateTime.of(2100, 6, 6, 12, 00);
		val steril = true;
		val originalverpackt = false;
		val medizinisch = true;
		val kommentar = "Neues Angebot";

		val userInstitution = beispielUserContextInstitution();
		val userInstitutionEntity = beispielUserContextInstitutionEntity();
		val userStandort = beispielUserContextAnderesStandort();
		val userStandortEntity = beispielUserContextAnderesStandortEntity();

		val angebotEntityOhneId = AngebotEntity.builder() //
				.artikelVariante(artikelVarianteEntity) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.institution(userInstitutionEntity) //
				.standort(userStandortEntity) //
				.haltbarkeit(haltbarkeit) //
				.steril(steril) //
				.originalverpackt(originalverpackt) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val angebotId = beispielAngebotId();

		val angebotEntityMitId = AngebotEntity.builder() //
				.id(angebotId.getValue()) //
				.artikelVariante(artikelVarianteEntity) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.institution(userInstitutionEntity) //
				.standort(userStandortEntity) //
				.haltbarkeit(haltbarkeit) //
				.steril(steril) //
				.originalverpackt(originalverpackt) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val neueAngebot = NeuesAngebot.builder() //
				.artikelVarianteId(artikelVarianteId) //
				.anzahl(anzahl) //
				.standortId(userStandort.getId()) //
				.haltbarkeit(haltbarkeit) //
				.steril(steril) //
				.originalverpackt(originalverpackt) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val entfernung = BigDecimal.valueOf(123664);

		given(userService.getContextInstitution()).willReturn(userInstitution);
		given(geoCalcService.berechneUserDistanzInKilometer(userStandort)).willReturn(entfernung);
		given(artikelSucheService.findArtikelVariante(artikelVarianteId)).willReturn(Optional.of(artikelVariante));
		given(angebotRepository.save(angebotEntityOhneId)).willReturn(angebotEntityMitId);

		val expectedAngebot = Angebot.builder() //
				.id(angebotId) //
				.artikelVariante(artikelVariante) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.institution(userInstitution) //
				.standort(userStandort) //
				.haltbarkeit(haltbarkeit) //
				.steril(steril) //
				.originalverpackt(originalverpackt) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.entfernung(entfernung) //
				.build();

		assertEquals(expectedAngebot, angebotAnlageService.neueAngebotEinstellen(neueAngebot));

		then(angebotRepository).should().save(angebotEntityOhneId);
		then(angebotRepository).shouldHaveNoMoreInteractions();
		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(artikelSucheService).should().findArtikelVariante(artikelVarianteId);
		then(artikelSucheService).shouldHaveNoMoreInteractions();
		then(geoCalcService).should().berechneUserDistanzInKilometer(userStandort);
		then(geoCalcService).shouldHaveNoMoreInteractions();
	}
}
