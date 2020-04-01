package io.remedymatch.angebot.domain;

import static io.remedymatch.angebot.process.AngebotAnfrageProzessConstants.ANFRAGE_STORNIEREN_MESSAGE;
import static io.remedymatch.angebot.process.AngebotAnfrageProzessConstants.PROZESS_KEY;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.artikel.api.ArtikelMapper;
import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.artikel.domain.ArtikelRepository;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.institution.domain.InstitutionStandortRepository;
import io.remedymatch.shared.GeoCalc;
import io.remedymatch.user.domain.NotUserInstitutionObjectException;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Validated
@Service
public class AngebotService {
	private static final String EXCEPTION_MSG_ANGEBOT_NICHT_GEFUNDEN = "Angebot fuer diese Id nicht gefunden: %s";
	private static final String EXCEPTION_MSG_ANGEBOT_NICHT_VON_USER_INSTITUTION = "Angebot gehoert nicht der Institution des angemeldetes Benutzers.";

	private final UserService userService;
	private final ArtikelRepository artikelRepository;
	private final InstitutionStandortRepository institutionStandortRepository;

	private final AngebotRepository angebotRepository;
	private final AngebotAnfrageRepository angebotAnfrageRepository;
	private final EngineClient engineClient;

	public List<Angebot> getAlleNichtBedienteAngebote() {
		return mitEntfernung(//
				angebotRepository.getAlleNichtBedienteAngebote(), //
				userService.getContextInstitution().getHauptstandort());
	}

	public List<Angebot> getAngeboteDerUserInstitution() {
		val userInstitution = userService.getContextInstitution();
		return mitEntfernung(//
				angebotRepository.getAngeboteVonInstitution(new InstitutionId(userInstitution.getId())), //
				userInstitution.getHauptstandort());
	}

	@Transactional
	public Angebot neueAngebotEinstellen(final @NotNull @Valid NeueAngebot neueAngebot) {
		// TODO haeslich
		ArtikelEntity artikel = ArtikelMapper
				.getArticleEntity(artikelRepository.get(neueAngebot.getArtikelId().getValue()));
		Optional<InstitutionStandortEntity> institutionStandort = institutionStandortRepository
				.findById(neueAngebot.getStandortId().getValue());
		if (institutionStandort.isEmpty()) {
			// FIXME: Pruefung darauf, dass es der Stanrort meiner Institution ist
			throw new IllegalArgumentException("InstitutionStandort nicht gefunden");
		}

		return mitEntfernung(angebotRepository.add(Angebot.builder() //
				.anzahl(neueAngebot.getAnzahl()) //
				.rest(neueAngebot.getAnzahl()) //
				.artikel(artikel) //
				.institution(userService.getContextInstitution()) //
				.standort(institutionStandort.get()) //
				.haltbarkeit(neueAngebot.getHaltbarkeit()) //
				.steril(neueAngebot.isSteril()) //
				.originalverpackt(neueAngebot.isOriginalverpackt()) //
				.medizinisch(neueAngebot.isMedizinisch()) //
				.kommentar(neueAngebot.getKommentar()) //
				.bedient(false) //
				.build()));
	}

	@Transactional
	public void angebotDerUserInstitutionLoeschen(final @NotNull @Valid AngebotId angebotId)
			throws ObjectNotFoundException, NotUserInstitutionObjectException {
		Optional<Angebot> angebot = angebotRepository.get(angebotId);
		if (!angebot.isPresent()) {
			throw new ObjectNotFoundException(String.format(EXCEPTION_MSG_ANGEBOT_NICHT_GEFUNDEN, angebotId));
		}

		if (!userService.isUserContextInstitution(new InstitutionId(angebot.get().getInstitution().getId()))) {
			throw new NotUserInstitutionObjectException(EXCEPTION_MSG_ANGEBOT_NICHT_VON_USER_INSTITUTION);
		}
		
		// Alle laufende Anfragen stornieren
		angebotAnfrageRepository.storniereAlleOffeneAnfragen(angebotId);
		angebotRepository.delete(angebotId);
	}

	public void angebotAnfrageErstellen(//
			final @NotNull @Valid AngebotId angebotId, //
			final @NotNull @Valid InstitutionStandortId standortId, //
			final @NotBlank String kommentar, //
			final @NotNull BigDecimal anzahl)
	{
		val angebot = angebotRepository.get(angebotId);

		if (angebot.isEmpty()) {
			throw new IllegalArgumentException("Angebot ist nicht vorhanden");
		}

		InstitutionStandortEntity standort = null;

		val userInstitution = userService.getContextInstitution();
		if (userInstitution.getHauptstandort().getId().equals(standortId)) {
			standort = userInstitution.getHauptstandort();
		} else {
			var foundStandort = userInstitution.getStandorte().stream().filter(s -> s.getId().equals(standortId)).findFirst();

			if (foundStandort.isPresent()) {
				standort = foundStandort.get();
			}
		}

		if (standort == null) {
			throw new IllegalArgumentException("Der ausgewählte Standort konnte nicht geunden werden");
		}

		var anfrage = AngebotAnfrage.builder() //
				.institutionVon(userInstitution) //
				.institutionAn(angebot.get().getInstitution()) //
				.kommentar(kommentar) //
				.standortAn(angebot.get().getStandort()) //
				.standortVon(standort) //
				.angebot(angebot.get()) //
				.anzahl(anzahl) //
				.status(AngebotAnfrageStatus.Offen) //
				.build();

		anfrage = angebotAnfrageRepository.add(anfrage);

		var variables = new HashMap<String, Object>();
		variables.put("institution", angebot.get().getInstitution().getId().toString());
		variables.put("objektId", anfrage.getId().toString());

		val prozessInstanzId = engineClient.prozessStarten(PROZESS_KEY, anfrage.getId().toString(), variables);
		anfrage.setProzessInstanzId(prozessInstanzId);
		angebotAnfrageRepository.update(anfrage);
	}
	
	@Transactional
	@Deprecated
	public void angebotMelden(Angebot angebot) {
		// sollte geloescht werden

		angebot.setInstitution(userService.getContextInstitution());
		angebot.setRest(angebot.getAnzahl());
		angebotRepository.add(angebot);
	}

	@Transactional
	public void anfrageStornieren(final AngebotAnfrageId anfrageId) {
		val anfrage = angebotAnfrageRepository.get(anfrageId);
		if (anfrage.isEmpty()) {
			throw new IllegalArgumentException("Anfrage nicht vorhanden");
		}
		anfrage.get().setStatus(AngebotAnfrageStatus.Storniert);
		angebotAnfrageRepository.update(anfrage.get());
	}

	@Transactional
	public void anfrageAnnehmen(final AngebotAnfrageId anfrageId) {
		val anfrage = angebotAnfrageRepository.get(anfrageId);
		if (anfrage.isEmpty()) {
			throw new IllegalArgumentException("Anfrage nicht vorhanden");
		}
		anfrage.get().setStatus(AngebotAnfrageStatus.Angenommen);

		// Angebot als bedient markieren
		val angebot = anfrage.get().getAngebot();

		// Restbestand des Angebots herabsetzen oder Exception werfen,
		// wenn die Anfrage größer als das Angebot ist
		if (anfrage.get().getAnzahl().doubleValue() > angebot.getRest().doubleValue()) {
			anfrage.get().setStatus(AngebotAnfrageStatus.Storniert);
			angebotAnfrageRepository.update(anfrage.get());
			throw new IllegalArgumentException("Nicht genügend Ware auf Lager");
		} else {
			if (anfrage.get().getAnzahl().doubleValue() == angebot.getRest().doubleValue()) {
				angebot.setBedient(true);
				angebot.setRest(BigDecimal.ZERO);
			} else {
				angebot.setRest(
						BigDecimal.valueOf(angebot.getRest().doubleValue() - anfrage.get().getAnzahl().doubleValue()));
			}
		}

		angebotRepository.update(angebot);
		angebotAnfrageRepository.update(anfrage.get());
	}

	/* help methods */

	private List<Angebot> mitEntfernung(final List<Angebot> angebote, InstitutionStandortEntity userHauptstandort) {
		angebote.forEach(
				angebot -> angebot.setEntfernung(berechneEntfernung(userHauptstandort, angebot.getStandort())));

		return angebote;
	}

	private Angebot mitEntfernung(final Angebot angebot) {
		angebot.setEntfernung(berechneEntfernung(angebot.getStandort()));
		return angebot;
	}

	private BigDecimal berechneEntfernung(final InstitutionStandortEntity angebotStandort) {
		return berechneEntfernung(userService.getContextInstitution().getHauptstandort(), angebotStandort);
	}

	private BigDecimal berechneEntfernung(//
			final InstitutionStandortEntity userHauptstandort, //
			final InstitutionStandortEntity angebotStandort) {
		return BigDecimal.valueOf(GeoCalc.kilometerBerechnen(userHauptstandort, angebotStandort));
	}

//	private void anfrageStornierenX(final AngebotAnfrageId anfrageId) {
//		val anfrage = angebotAnfrageRepository.get(anfrageId);
//
//		if (anfrage.isEmpty()) {
//			throw new IllegalArgumentException("Anfrage ist nicht vorhanden und kann deshalb nicht storniert werden");
//		}
//
//		AngebotAnfrage angebotAnfrage = anfrage.get();
//		if (!AngebotAnfrageStatus.Offen.equals(angebotAnfrage.getStatus())) {
//			throw new IllegalArgumentException(
//					"Eine Anfrage, die nicht im Status offen ist, kann nicht storniert werden");
//		}
//
//		angebotAnfrage.setStatus(AngebotAnfrageStatus.Storniert);
//		angebotAnfrageRepository.update(angebotAnfrage);
//
//		engineClient.messageKorrelieren(angebotAnfrage.getProzessInstanzId(), ANFRAGE_STORNIEREN_MESSAGE,
//				new HashMap<>());
//	}
}
