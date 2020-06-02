package io.remedymatch.angebot.domain.service;

import static io.remedymatch.angebot.domain.service.AngebotTestFixtures.beispielAngebotId;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikel;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelEntity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVariante;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVarianteEntity;
import static io.remedymatch.artikel.domain.service.ArtikelTestFixtures.beispielArtikelVarianteId;
import static io.remedymatch.usercontext.UserContextTestFixtures.beispielUserContextPerson;
import static io.remedymatch.usercontext.UserContextTestFixtures.beispielUserContextPersonEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.geodaten.domain.GeocodingService;
import io.remedymatch.usercontext.UserContextService;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		AngebotAnlageService.class, //
		AngebotJpaRepository.class, //
		UserContextService.class, //
		ArtikelSucheService.class, //
		AngebotProzessService.class, //
		GeocodingService.class, //
})
@Tag("Spring")
@DisplayName("AngebotAnlageService soll")
class AngebotAnlageServiceShould {

	@Autowired
	private AngebotAnlageService angebotAnlageService;

	@MockBean
	private AngebotJpaRepository angebotRepository;

	@MockBean
	private UserContextService userService;

	@MockBean
	private ArtikelSucheService artikelSucheService;

	@MockBean
	private GeocodingService geoCalcService;

	@MockBean
	private AngebotProzessService angebotProzessService;

	@Test
	@DisplayName("Angebot anlegen koennen")
	void angebot_anlegen_koennen() {

		val artikelVarianteId = beispielArtikelVarianteId();
		val artikelVariante = beispielArtikelVariante();
		val artikelVarianteEntity = beispielArtikelVarianteEntity();
		val beispielArtikel = beispielArtikel();
		val beispielArtikelEntity = beispielArtikelEntity();

		val anzahl = BigDecimal.valueOf(100);
		val haltbarkeit = LocalDateTime.of(2100, 6, 6, 12, 00);
		val steril = true;
		val originalverpackt = false;
		val medizinisch = true;
		val kommentar = "Neues Angebot";

		val userStandort = beispielUserContextPerson().getAktuellesStandort();
		val userStandortEntity = beispielUserContextPersonEntity().getAktuellesStandort();

		val institution = userStandort.getInstitution();
		val institutionEntity = userStandortEntity.getInstitution();
		val standort = userStandort.getStandort();
		val standortEntity = userStandortEntity.getStandort();

		AngebotEntity angebotEntityOhneId = AngebotEntity.builder() //
				.artikelVariante(artikelVarianteEntity) //
				.anzahl(anzahl) //
				.artikel(beispielArtikelEntity) //
				.rest(anzahl) //
				.institution(institutionEntity) //
				.standort(standortEntity) //
				.haltbarkeit(haltbarkeit) //
				.steril(steril) //
				.originalverpackt(originalverpackt) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val angebotId = beispielAngebotId();

		AngebotEntity angebotEntityMitId = AngebotEntity.builder() //
				.id(angebotId.getValue()) //
				.artikelVariante(artikelVarianteEntity) //
				.anzahl(anzahl) //
				.artikel(beispielArtikelEntity) //
				.rest(anzahl) //
				.institution(institutionEntity) //
				.standort(standortEntity) //
				.haltbarkeit(haltbarkeit) //
				.steril(steril) //
				.originalverpackt(originalverpackt) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val neueAngebot = NeuesAngebot.builder() //
				.artikelVarianteId(artikelVarianteId) //
				.anzahl(anzahl) //
				.haltbarkeit(haltbarkeit) //
				.steril(steril) //
				.originalverpackt(originalverpackt) //
				.medizinisch(medizinisch) //
				.kommentar(kommentar) //
				.build();

		val entfernung = BigDecimal.valueOf(123664);

		given(artikelSucheService.getArtikelOrElseThrow(artikelVariante.getArtikelId())).willReturn(beispielArtikel);
		given(userService.getContextStandort()).willReturn(userStandort);
		given(geoCalcService.berechneUserDistanzInKilometer(standort)).willReturn(entfernung);
		given(artikelSucheService.getArtikelVarianteOrElseThrow(artikelVarianteId)).willReturn(artikelVariante);
		given(angebotRepository.save(angebotEntityOhneId)).willReturn(angebotEntityMitId);
		given(userService.getContextUser()).willReturn(beispielUserContextPerson());

		val expectedAngebot = Angebot.builder() //
				.id(angebotId) //
				.artikelVariante(artikelVariante) //
				.artikel(beispielArtikel) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.institution(institution) //
				.standort(standort) //
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
		then(userService).should(times(2)).getContextStandort();
		then(userService).should().getContextUserId();
		then(artikelSucheService).should().getArtikelVarianteOrElseThrow(artikelVarianteId);
		then(artikelSucheService).should().getArtikelOrElseThrow(artikelVariante.getArtikelId());
		then(artikelSucheService).shouldHaveNoMoreInteractions();
		then(geoCalcService).should().berechneUserDistanzInKilometer(standort);
		then(geoCalcService).shouldHaveNoMoreInteractions();
	}
}
