package io.remedymatch.bedarf.domain.service;

import static io.remedymatch.bedarf.domain.service.BedarfAnfrageTestFixtures.beispielBedarfAnfrage;
import static io.remedymatch.bedarf.domain.service.BedarfAnfrageTestFixtures.beispielBedarfAnfrageEntity;
import static io.remedymatch.bedarf.domain.service.BedarfAnfrageTestFixtures.beispielBedarfAnfrageId;
import static io.remedymatch.bedarf.domain.service.BedarfTestFixtures.beispielBedarf;
import static io.remedymatch.bedarf.domain.service.BedarfTestFixtures.beispielBedarfEntity;
import static io.remedymatch.bedarf.domain.service.BedarfTestFixtures.beispielBedarfId;
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

import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageJpaRepository;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.engine.domain.ProzessInstanzId;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.service.InstitutionTestFixtures;
import io.remedymatch.usercontext.UserContextService;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		BedarfService.class, //
		BedarfJpaRepository.class, //
		BedarfAnfrageJpaRepository.class, //
		UserContextService.class, //
		BedarfAnfrageProzessService.class //
})
@Tag("Spring")
@DisplayName("BedarfService soll")
class BedarfServiceShould {
	@Autowired
	private BedarfService bedarfService;

	@MockBean
	private BedarfJpaRepository bedarfRepository;

	@MockBean
	private BedarfAnfrageJpaRepository anfrageRepository;

	@MockBean
	private UserContextService userService;

	@MockBean
	private BedarfAnfrageProzessService anfrageProzessService;

	@Test
	@DisplayName("Fehler werfen bei Bearbeitung von nicht existierender Angebpt")
	void fehler_werfen_bei_Bearbeitung_von_nicht_existierender_Bedarf() {
		val unbekannteBedarfId = beispielBedarfId();

		assertThrows(ObjectNotFoundException.class, //
				() -> bedarfService.getNichtBedienteBedarf(unbekannteBedarfId));
	}

	@Test
	@DisplayName("Fehler werfen bei Bearbeitung von bediente Angebpt")
	void fehler_werfen_bei_Bearbeitung_von_bediente_Bedarf() {
		val bedienteBedarfId = beispielBedarfId();
		val bedienteBedarfEntity = beispielBedarfEntity();
		bedienteBedarfEntity.setBedient(true);

		given(bedarfRepository.findById(bedienteBedarfId.getValue())).willReturn(Optional.of(bedienteBedarfEntity));

		assertThrows(OperationNotAlloudException.class, //
				() -> bedarfService.getNichtBedienteBedarf(bedienteBedarfId));
	}

	@Test
	@DisplayName("Fehler werfen bei Bearbeitung von Angebpt, die nicht UserContext Institution gehoert")
	void fehler_werfen_bei_Bearbeitung_von_Bedarf_die_nicht_UserContext_Institution_gehoert() {
		val bedarfId = beispielBedarfId();
		val bedarf = beispielBedarf();
		val bedarfEntity = beispielBedarfEntity();

		given(bedarfRepository.findById(bedarfId.getValue())).willReturn(Optional.of(bedarfEntity));
		given(userService.isUserContextInstitution(bedarf.getInstitution().getId())).willReturn(false);

		assertThrows(NotUserInstitutionObjectException.class, //
				() -> bedarfService.getNichtBedienteBedarfDerUserInstitution(bedarfId));
	}

	@Test
	@DisplayName("Fehler werfen bei Bearbeitung von nicht existierender Anfrage")
	void fehler_werfen_bei_Bearbeitung_von_nicht_existierender_Anfrage() {
		val unbekannteAnfrageId = beispielBedarfAnfrageId();

		assertThrows(ObjectNotFoundException.class, //
				() -> bedarfService.getOffeneAnfrage(unbekannteAnfrageId));
	}

	@Test
	@DisplayName("Fehler werfen bei Bearbeitung von Anfragea aus anderer Bedarf")
	void fehler_werfen_bei_Bearbeitung_von_Anfrage_aus_anderer_Bedarf() {
		val andereBedarfId = new BedarfId(UUID.randomUUID());

		val anfrageId = beispielBedarfAnfrageId();
		val anfrageEntity = beispielBedarfAnfrageEntity();

		given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

		assertThrows(OperationNotAlloudException.class, //
				() -> bedarfService.getOffeneAnfrage(andereBedarfId, anfrageId));
	}

	@Test
	@DisplayName("Fehler werfen bei Bearbeitung von nicht offene Anfrage")
	void fehler_werfen_bei_Bearbeitung_von_nicht_offene_Anfrage() {
		val stornierteAnfrageId = beispielBedarfAnfrageId();
		val stornierteAnfrageBedarfId = beispielBedarfAnfrage().getBedarf().getId();
		val stornierteAnfrageEntity = beispielBedarfAnfrageEntity();
		stornierteAnfrageEntity.setStatus(BedarfAnfrageStatus.Storniert);

		given(anfrageRepository.findById(stornierteAnfrageId.getValue()))
				.willReturn(Optional.of(stornierteAnfrageEntity));

		assertThrows(OperationNotAlloudException.class, //
				() -> bedarfService.getOffeneAnfrage(stornierteAnfrageBedarfId, stornierteAnfrageId));
	}

	@Test
	@DisplayName("Fehler werfen bei Bearbeitung von Anfrage, die nicht UserContext Institution gehoert")
	void fehler_werfen_bei_Bearbeitung_von_Anfrage_die_nicht_UserContext_Institution_gehoert() {
		val anfrageId = beispielBedarfAnfrageId();
		val anfrage = beispielBedarfAnfrage();
		val anfrageBedarfId = anfrage.getBedarf().getId();
		val anfrageEntity = beispielBedarfAnfrageEntity();

		given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));
		given(userService.isUserContextInstitution(anfrage.getInstitution().getId())).willReturn(false);

		assertThrows(NotUserInstitutionObjectException.class, //
				() -> bedarfService.getOffeneAnfrageDerUserInstitution(anfrageBedarfId, anfrageId));
	}

	@Test
	@DisplayName("Fehler werfen wenn der Standort nicht in UserContext Institution gefunden wird")
	void fehler_werfen_wenn_der_Standort_nicht_in_UserContext_Institution_gefunden_wird() {

		assertThrows(NotUserInstitutionObjectException.class, //
				() -> bedarfService.getUserInstitutionStandort(InstitutionTestFixtures.beispielInstitutionEntity(),
						new InstitutionStandortId(UUID.randomUUID())));
	}

	@Test
	@DisplayName("Bedarf der UserContext Institution loeschen koennen")
	void bedarf_der_UserContext_Institution_loeschen_koennen() {
		val bedarfId = beispielBedarfId();
		val bedarf = beispielBedarf();
		val bedarfInstitutionId = bedarf.getInstitution().getId();
		val bedarfEntity = beispielBedarfEntity();
		val bedarfEntityBedient = beispielBedarfEntity();
		bedarfEntityBedient.setDeleted(true);

		given(bedarfRepository.findById(bedarfId.getValue())).willReturn(Optional.of(bedarfEntity));
		given(userService.isUserContextInstitution(bedarfInstitutionId)).willReturn(true);
		given(bedarfRepository.save(bedarfEntityBedient)).willReturn(bedarfEntityBedient);

		bedarfService.bedarfDerUserInstitutionLoeschen(bedarfId);

		then(bedarfRepository).should().findById(bedarfId.getValue());
		then(bedarfRepository).should().save(bedarfEntityBedient);
		then(bedarfRepository).shouldHaveNoMoreInteractions();
		then(anfrageRepository).should().updateStatus(bedarfId.getValue(), BedarfAnfrageStatus.Offen,
				BedarfAnfrageStatus.Storniert);
		then(anfrageRepository).shouldHaveNoMoreInteractions();
		then(userService).should().isUserContextInstitution(bedarfInstitutionId);
		then(userService).shouldHaveNoMoreInteractions();
		then(anfrageProzessService).should().prozesseStornieren(bedarfId);
		then(anfrageProzessService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("neue Bedarf Anfrage einstellen koennen")
	void neue_anfrage_einstellen_koennen() {
		val kommentar = "Anfrage Kommentar";
		val anzahl = BigDecimal.valueOf(10000);

		val anfrageId = beispielBedarfAnfrageId();
		val anfrage = beispielBedarfAnfrage();
		anfrage.setAnzahl(anzahl);
		anfrage.setKommentar(kommentar);

		val anfrageEntity = beispielBedarfAnfrageEntity();
		anfrageEntity.setAnzahl(anzahl);
		anfrageEntity.setKommentar(kommentar);

		val institution = anfrage.getInstitution();
		val institutionEntity = anfrageEntity.getInstitution();
		val standortEntity = anfrageEntity.getStandort();
		val standortId = anfrage.getStandort().getId();

		val bedarf = anfrage.getBedarf();
		val bedarfEntity = anfrageEntity.getBedarf();
		val bedarfId = bedarf.getId();
		val bedarfInstitutionId = bedarf.getInstitution().getId();

		val prozessInstanzId = new ProzessInstanzId(anfrageEntity.getProzessInstanzId());

		BedarfAnfrageEntity neueAnfrageEntity = BedarfAnfrageEntity.builder()//
				.bedarf(bedarfEntity) //
				.institution(institutionEntity) //
				.standort(standortEntity) //
				.anzahl(anzahl) //
				.kommentar(kommentar) //
				.status(BedarfAnfrageStatus.Offen) //
				.build();

		given(bedarfRepository.findById(bedarfId.getValue())).willReturn(Optional.of(bedarfEntity));
		given(userService.getContextInstitution()).willReturn(institution);
		given(anfrageProzessService.prozessStarten(bedarfId, anfrageId, bedarfInstitutionId))
				.willReturn(prozessInstanzId);
		given(anfrageRepository.save(neueAnfrageEntity)).willReturn(anfrageEntity);
		given(anfrageRepository.save(anfrageEntity)).willReturn(anfrageEntity);

		assertEquals(anfrage, bedarfService.bedarfAnfrageErstellen(//
				bedarfId, //
				standortId, //
				kommentar, //
				anzahl));

		then(bedarfRepository).should().findById(bedarfId.getValue());
		then(bedarfRepository).shouldHaveNoMoreInteractions();
		then(anfrageRepository).should().save(neueAnfrageEntity);
		then(anfrageRepository).should().save(anfrageEntity);
		then(anfrageRepository).shouldHaveNoMoreInteractions();
		then(userService).should().getContextInstitution();
		then(userService).shouldHaveNoMoreInteractions();
		then(anfrageProzessService).should().prozessStarten(bedarfId, anfrageId, bedarfInstitutionId);
		then(anfrageProzessService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("Bedarf Anfrage der UserContext Institution loeschen koennen")
	void anfrage_der_UserContext_Institution_loeschen_koennen() {
		val anfrageId = beispielBedarfAnfrageId();
		val anfrage = beispielBedarfAnfrage();
		val anfrageInstitutionId = anfrage.getInstitution().getId();
		val anfrageBedarfId = anfrage.getBedarf().getId();
		val anfrageEntity = beispielBedarfAnfrageEntity();
		val anfrageEntityStorniert = beispielBedarfAnfrageEntity();
		anfrageEntityStorniert.setStatus(BedarfAnfrageStatus.Storniert);

		given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));
		given(userService.isUserContextInstitution(anfrageInstitutionId)).willReturn(true);
		given(anfrageRepository.save(anfrageEntityStorniert)).willReturn(anfrageEntityStorniert);

		bedarfService.bedarfAnfrageDerUserInstitutionLoeschen(anfrageBedarfId, anfrageId);

		then(bedarfRepository).shouldHaveNoInteractions();
		then(anfrageRepository).should().findById(anfrageId.getValue());
		then(anfrageRepository).should().save(anfrageEntityStorniert);
		then(anfrageRepository).shouldHaveNoMoreInteractions();
		then(userService).should().isUserContextInstitution(anfrageInstitutionId);
		then(userService).shouldHaveNoMoreInteractions();
		then(anfrageProzessService).should().prozessStornieren(anfrageId);
		then(anfrageProzessService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("Bedarf Anfrage stornieren koennen")
	void anfrage_stornieren_koennen() {
		val anfrageId = beispielBedarfAnfrageId();
		val anfrageEntity = beispielBedarfAnfrageEntity();
		val anfrageEntityStorniert = beispielBedarfAnfrageEntity();
		anfrageEntityStorniert.setStatus(BedarfAnfrageStatus.Storniert);

		given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

		bedarfService.anfrageStornieren(anfrageId);

		then(bedarfRepository).shouldHaveNoInteractions();
		then(anfrageRepository).should().findById(anfrageId.getValue());
		then(anfrageRepository).should().save(anfrageEntityStorniert);
		then(anfrageRepository).shouldHaveNoMoreInteractions();
		then(userService).shouldHaveNoInteractions();
		then(anfrageProzessService).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("eine OperationNotAlloudException werfen wenn bei Annahme der Anzahl in Anfrage hoeher ist als Rest in Bedarf")
	void eine_OperationNotAlloudException_werfen_wenn_bei_Annahme_der_Anzahl_in_Anfrage_hoeher_ist_als_Rest_in_Bedarf() {

		val bedarfRestDavor = BigDecimal.valueOf(800);
		val anfrageAnzahl = BigDecimal.valueOf(4000);

		val anfrageId = beispielBedarfAnfrageId();
		val anfrageEntity = beispielBedarfAnfrageEntity();
		anfrageEntity.setAnzahl(anfrageAnzahl);
		anfrageEntity.getBedarf().setRest(bedarfRestDavor);

		given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

		assertThrows(OperationNotAlloudException.class, //
				() -> bedarfService.anfrageAnnehmen(anfrageId));
	}

	@Test
	@DisplayName("Bedarf Anfrage annehmen und Bedarf-Rest reduzieren koennen, wenn die Anfrage die Bedarf dekt")
	void anfrage_annehmen_und_Bedarf_Rest_reduzieren_koennen_wenn_die_Anfrage_die_Bedarf_dekt() {

		val bedarfRestDavor = BigDecimal.valueOf(1000);
		val anfrageAnzahl = BigDecimal.valueOf(800);
		val bedarfRestDanach = BigDecimal.valueOf(200);

		val anfrageId = beispielBedarfAnfrageId();
		val anfrageEntity = beispielBedarfAnfrageEntity();
		anfrageEntity.setAnzahl(anfrageAnzahl);
		anfrageEntity.getBedarf().setRest(bedarfRestDavor);

		val anfrageEntityAngenommen = beispielBedarfAnfrageEntity();
		anfrageEntityAngenommen.setAnzahl(anfrageAnzahl);
		anfrageEntityAngenommen.setStatus(BedarfAnfrageStatus.Angenommen);

		val bedarfDanach = anfrageEntityAngenommen.getBedarf();
		bedarfDanach.setRest(bedarfRestDanach);

		given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

		bedarfService.anfrageAnnehmen(anfrageId);

		then(bedarfRepository).should().save(bedarfDanach);
		then(bedarfRepository).shouldHaveNoMoreInteractions();
		then(anfrageRepository).should().findById(anfrageId.getValue());
		then(anfrageRepository).should().save(anfrageEntityAngenommen);
		then(anfrageRepository).shouldHaveNoMoreInteractions();
		then(userService).shouldHaveNoInteractions();
		then(anfrageProzessService).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("Bedarf Anfrage annehmen und Bedarf als bedient schliessen")
	void anfrage_annehmen_und_Bedarf_als_bedient_schliessen() {

		val bedarfRestDavor = BigDecimal.valueOf(1000);
		val anfrageAnzahl = BigDecimal.valueOf(1000);
		val bedarfRestDanach = BigDecimal.ZERO;

		val anfrageId = beispielBedarfAnfrageId();
		val anfrageEntity = beispielBedarfAnfrageEntity();
		anfrageEntity.setAnzahl(anfrageAnzahl);
		anfrageEntity.getBedarf().setRest(bedarfRestDavor);

		val anfrageEntityAngenommen = beispielBedarfAnfrageEntity();
		anfrageEntityAngenommen.setAnzahl(anfrageAnzahl);
		anfrageEntityAngenommen.setStatus(BedarfAnfrageStatus.Angenommen);

		val bedarfDanach = anfrageEntityAngenommen.getBedarf();
		bedarfDanach.setRest(bedarfRestDanach);
		bedarfDanach.setBedient(true);

		given(anfrageRepository.findById(anfrageId.getValue())).willReturn(Optional.of(anfrageEntity));

		bedarfService.anfrageAnnehmen(anfrageId);

		then(bedarfRepository).should().save(bedarfDanach);
		then(bedarfRepository).shouldHaveNoMoreInteractions();
		then(anfrageRepository).should().findById(anfrageId.getValue());
		then(anfrageRepository).should().save(anfrageEntityAngenommen);
		then(anfrageRepository).shouldHaveNoMoreInteractions();
		then(userService).shouldHaveNoInteractions();
		then(anfrageProzessService).shouldHaveNoInteractions();
	}
}
