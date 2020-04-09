package io.remedymatch.bedarf.domain.service;

import static io.remedymatch.bedarf.process.BedarfAnfrageProzessConstants.PROZESS_KEY;

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

import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.institution.domain.InstitutionStandortRepository;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Validated
@Service
public class BedarfService {
	private static final String EXCEPTION_MSG_BEDARF_NICHT_GEFUNDEN = "Bedarf fuer diese Id nicht gefunden: %s";
	private static final String EXCEPTION_MSG_BEDARF_NICHT_VON_USER_INSTITUTION = "Bedarf gehoert nicht der Institution des angemeldetes Benutzers.";

	private static final String EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_GEFUNDEN = "BedarfAnfrage fuer diese Id nicht gefunden: %s";
	private static final String EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_VON_USER_INSTITUTION = "BedarfAnfrage gehoert nicht der Institution des angemeldetes Benutzers.";

	private final UserService userService;
	private final ArtikelSucheService artikelSucheService;
	private final InstitutionStandortRepository institutionStandortRepository;

	private final BedarfRepository bedarfRepository;
	private final BedarfAnfrageRepository bedarfAnfrageRepository;
	private final GeoCalcService geoCalcService;
	private final EngineClient engineClient;

//	public List<Bedarf> getAlleNichtBedienteBedarfe() {
//		return mitEntfernung(//
//				bedarfRepository.getAlleNichtBedienteBedarfe(), //
//				userService.getContextInstitution().getHauptstandort());
//	}
//
//	public List<Bedarf> getBedarfeDerUserInstitution() {
//		val userInstitution = userService.getContextInstitution();
//		return mitEntfernung(//
//				bedarfRepository.getBedarfeVonInstitution(userInstitution.getId()), //
//				userInstitution.getHauptstandort());
//	}

	@Transactional
	public void bedarfDerUserInstitutionLoeschen(final @NotNull @Valid BedarfId bedarfId)
			throws ObjectNotFoundException, NotUserInstitutionObjectException {
		Optional<Bedarf> bedarf = bedarfRepository.get(bedarfId);
		if (!bedarf.isPresent()) {
			throw new ObjectNotFoundException(String.format(EXCEPTION_MSG_BEDARF_NICHT_GEFUNDEN, bedarfId));
		}

		if (!userService.isUserContextInstitution(bedarf.get().getInstitution().getId())) {
			throw new NotUserInstitutionObjectException(EXCEPTION_MSG_BEDARF_NICHT_VON_USER_INSTITUTION);
		}

		// Alle laufende Anfragen stornieren
		bedarfAnfrageRepository.storniereAlleOffeneAnfragen(bedarfId);
		// TODO Auch Prozesse beenden

		bedarfRepository.delete(bedarfId);
	}

	@Transactional
	public BedarfAnfrage bedarfAnfrageErstellen(//
			final @NotNull @Valid BedarfId bedarfId, //
			final @NotNull @Valid InstitutionStandortId standortId, //
			final @NotBlank String kommentar, //
			final @NotNull BigDecimal anzahl) {
		val bedarf = bedarfRepository.get(bedarfId);

		if (bedarf.isEmpty()) {
			throw new IllegalArgumentException("Bedarf ist nicht vorhanden");
		}

		InstitutionStandort standort = null;

		val userInstitution = userService.getContextInstitution();
		if (userInstitution.getHauptstandort().getId().equals(standortId)) {
			standort = userInstitution.getHauptstandort();
		} else {
			var foundStandort = userInstitution.getStandorte().stream().filter(s -> s.getId().equals(standortId))
					.findFirst();

			if (foundStandort.isPresent()) {
				standort = foundStandort.get();
			}
		}

		if (standort == null) {
			throw new IllegalArgumentException("Der ausgewählte Standort konnte nicht geunden werden");
		}

		var anfrage = BedarfAnfrage.builder() //
				.bedarf(bedarf.get()) //
				.institutionVon(userInstitution) //
				.standortVon(standort) //
				.anzahl(anzahl) //
				.kommentar(kommentar) //
				.status(BedarfAnfrageStatus.Offen) //
				.build();

		anfrage = bedarfAnfrageRepository.add(anfrage);

		var variables = new HashMap<String, Object>();
		variables.put("institution", bedarf.get().getInstitution().getId().getValue().toString());
		variables.put("objektId", anfrage.getId().getValue().toString());

		val prozessInstanzId = engineClient.prozessStarten(PROZESS_KEY, new BusinessKey(anfrage.getId().getValue()),
				variables);
		anfrage.setProzessInstanzId(prozessInstanzId.getValue());
		return bedarfAnfrageRepository.update(anfrage);
	}

	@Transactional
	public void bedarfAnfrageDerUserInstitutionLoeschen(//
			final @NotNull @Valid BedarfId bedarfId, //
			final @NotNull @Valid BedarfAnfrageId anfrageId)
			throws ObjectNotFoundException, NotUserInstitutionObjectException {
		Optional<BedarfAnfrage> bedarfAnfrage = bedarfAnfrageRepository.get(anfrageId);
		if (!bedarfAnfrage.isPresent()) {
			throw new ObjectNotFoundException(String.format(EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_GEFUNDEN, anfrageId));
		}

		if (!userService.isUserContextInstitution(bedarfAnfrage.get().getInstitutionVon().getId())) {
			throw new NotUserInstitutionObjectException(EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_VON_USER_INSTITUTION);
		}

		anfrageStornieren(anfrageId);
	}

	@Transactional
	@Deprecated
	public void bedarfMelden(Bedarf bedarf) {
		// sollte geloescht werden

		bedarf.setInstitution(userService.getContextInstitution());
		bedarf.setRest(bedarf.getAnzahl());
		bedarfRepository.add(bedarf);
	}

	@Transactional
	public void anfrageStornieren(final @NotNull @Valid BedarfAnfrageId anfrageId) {
		val anfrage = bedarfAnfrageRepository.get(anfrageId);
		if (anfrage.isEmpty()) {
			throw new IllegalArgumentException("Anfrage nicht vorhanden");
		}
		anfrage.get().setStatus(BedarfAnfrageStatus.Storniert);
		bedarfAnfrageRepository.update(anfrage.get());
	}

	@Transactional
	public void anfrageAnnehmen(final @NotNull @Valid BedarfAnfrageId anfrageId) {
		val anfrage = bedarfAnfrageRepository.get(anfrageId);
		if (anfrage.isEmpty()) {
			throw new IllegalArgumentException("Anfrage nicht vorhanden");
		}
		anfrage.get().setStatus(BedarfAnfrageStatus.Angenommen);

		// Bedarf als bedient markieren
		val bedarf = anfrage.get().getBedarf();

		// Restbestand des Bedarfs herabsetzen oder Exception werfen,
		// wenn die Anfrage größer als das Bedarf ist
		if (anfrage.get().getAnzahl().doubleValue() > bedarf.getRest().doubleValue()) {
			anfrage.get().setStatus(BedarfAnfrageStatus.Storniert);
			bedarfAnfrageRepository.update(anfrage.get());
			throw new IllegalArgumentException("Nicht genügend Ware auf Lager");
		} else {
			if (anfrage.get().getAnzahl().doubleValue() == bedarf.getRest().doubleValue()) {
				bedarf.setBedient(true);
				bedarf.setRest(BigDecimal.ZERO);
			} else {
				bedarf.setRest(
						BigDecimal.valueOf(bedarf.getRest().doubleValue() - anfrage.get().getAnzahl().doubleValue()));
			}
		}

		bedarfRepository.update(bedarf);
		bedarfAnfrageRepository.update(anfrage.get());
	}

	/* help methods */

	private List<Bedarf> mitEntfernung(final List<Bedarf> bedarfe, final InstitutionStandort userHauptstandort) {
		bedarfe.forEach(bedarf -> bedarf.setEntfernung(berechneEntfernung(//
				userHauptstandort, //
				bedarf.getStandort())));

		return bedarfe;
	}

	private Bedarf mitEntfernung(final Bedarf bedarf) {
		bedarf.setEntfernung(berechneEntfernung(bedarf.getStandort()));
		return bedarf;
	}

	private BigDecimal berechneEntfernung(final InstitutionStandort bedarfStandort) {
		return berechneEntfernung(//
				userService.getContextInstitution().getHauptstandort(), //
				bedarfStandort);
	}

	private BigDecimal berechneEntfernung(//
			final InstitutionStandort userHauptstandort, //
			final InstitutionStandort bedarfStandort) {
		return geoCalcService.berechneDistanzInKilometer(userHauptstandort, bedarfStandort);
	}

//	private void anfrageStornierenX(final BedarfAnfrageId anfrageId) {
//		val anfrage = bedarfAnfrageRepository.get(anfrageId);
//
//		if (anfrage.isEmpty()) {
//			throw new IllegalArgumentException("Anfrage ist nicht vorhanden und kann deshalb nicht storniert werden");
//		}
//
//		BedarfAnfrage bedarfAnfrage = anfrage.get();
//		if (!BedarfAnfrageStatus.Offen.equals(bedarfAnfrage.getStatus())) {
//			throw new IllegalArgumentException(
//					"Eine Anfrage, die nicht im Status offen ist, kann nicht storniert werden");
//		}
//
//		bedarfAnfrage.setStatus(BedarfAnfrageStatus.Storniert);
//		bedarfAnfrageRepository.update(bedarfAnfrage);
//
//		engineClient.messageKorrelieren(bedarfAnfrage.getProzessInstanzId(), ANFRAGE_STORNIEREN_MESSAGE,
//				new HashMap<>());
//	}
}
