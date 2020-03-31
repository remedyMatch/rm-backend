package io.remedymatch.angebot.domain;

import static io.remedymatch.angebot.api.AngebotAnfrageProzessConstants.PROZESS_KEY;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Service
public class AngebotService {

	private final AngebotRepository angebotRepository;
	private final AngebotAnfrageRepository anfrageRepository;
	private final AngebotAnfrageService anfrageService;
	private final EngineClient engineClient;

	@Transactional
	public void angebotMelden(AngebotEntity angebot, InstitutionEntity institutionEntity) {
		angebot.setInstitution(institutionEntity);
		angebot.setRest(angebot.getAnzahl());
		angebotRepository.add(angebot);
	}

	@Transactional
	public void angebotLoeschen(UUID id) {
		val angebot = angebotRepository.get(id);

		if (angebot.isEmpty()) {
			throw new IllegalIdentifierException("Angebot ist nicht vorhanden");
		}

		if (angebot.get().getAnfragen() != null) {
			angebot.get().getAnfragen().stream()
					.filter(anfrage -> anfrage.getStatus().equals(AngebotAnfrageStatus.Offen))
					.forEach(anfrage -> anfrageService.anfrageStornieren(anfrage.getId().toString()));
		}

		angebotRepository.delete(angebot.get().getId());
	}

	@Transactional
	public Optional<AngebotEntity> angebotLaden(UUID id) {
		return angebotRepository.get(id);
	}

	@Transactional
	public void angebotUpdaten(AngebotEntity angebot) {
		val oldAngebot = angebotRepository.get(angebot.getId()).get();
		oldAngebot.setAnzahl(angebot.getAnzahl());
		oldAngebot.setArtikel(angebot.getArtikel());
		oldAngebot.setMedizinisch(angebot.isMedizinisch());
		oldAngebot.setOriginalverpackt(angebot.isOriginalverpackt());
		oldAngebot.setSteril(angebot.isSteril());
		oldAngebot.setStandort(angebot.getStandort());
		oldAngebot.setHaltbarkeit(angebot.getHaltbarkeit());
		oldAngebot.setKommentar(angebot.getKommentar());
		angebotRepository.update(oldAngebot);
	}

	@Transactional
	public void starteAnfrage(UUID angebotId, InstitutionEntity anfrager, String kommentar, UUID standortId,
			double anzahl) {

		val angebot = angebotRepository.get(angebotId);

		if (angebot.isEmpty()) {
			throw new IllegalArgumentException("Angebot ist nicht vorhanden");
		}

		InstitutionStandortEntity standort = null;

		if (anfrager.getHauptstandort().getId().equals(standortId)) {
			standort = anfrager.getHauptstandort();
		} else {
			var foundStandort = anfrager.getStandorte().stream().filter(s -> s.getId().equals(standortId)).findFirst();

			if (foundStandort.isPresent()) {
				standort = foundStandort.get();
			}
		}

		if (standort == null) {
			throw new IllegalArgumentException("Der ausgewählte Standort konnte nicht geunden werden");
		}

		val anfrage = AngebotAnfrage.builder() //
				.institutionVon(anfrager) //
				.institutionAn(angebot.get().getInstitution()) //
				.kommentar(kommentar) //
				.standortAn(angebot.get().getStandort()) //
				.standortVon(standort) //
				.angebot(angebot.get()) //
				.anzahl(BigDecimal.valueOf(anzahl)) //
				.status(AngebotAnfrageStatus.Offen) //
				.build();

		anfrageRepository.update(anfrage);

		var variables = new HashMap<String, Object>();
		variables.put("institution", angebot.get().getInstitution().getId().toString());
		variables.put("objektId", anfrage.getId().toString());

		val prozessInstanzId = engineClient.prozessStarten(PROZESS_KEY, anfrage.getId().toString(), variables);
		anfrage.setProzessInstanzId(prozessInstanzId);
		anfrageRepository.update(anfrage);
	}

	@Transactional
	public void anfrageStornieren(String anfrageId) {
		val anfrage = anfrageRepository.get(UUID.fromString(anfrageId));
		if (anfrage.isEmpty()) {
			throw new IllegalArgumentException("Anfrage nicht vorhanden");
		}
		anfrage.get().setStatus(AngebotAnfrageStatus.Storniert);
		anfrageRepository.update(anfrage.get());
	}

	@Transactional
	public void anfrageAnnehmen(String anfrageId) {
		val anfrage = anfrageRepository.get(UUID.fromString(anfrageId));
		if (anfrage.isEmpty()) {
			throw new IllegalArgumentException("Anfrage nicht vorhanden");
		}
		anfrage.get().setStatus(AngebotAnfrageStatus.Angenommen);

		// Angebot als bedient markieren
		val angebot = anfrage.get().getAngebot();

		// Restbestand des Angebots herabsetzen oder Exception werfen,
		// wenn die Anfrage größer als das Angebot ist
		if (anfrage.get().getAnzahl().doubleValue() > angebot.getAnzahl()) {
			anfrage.get().setStatus(AngebotAnfrageStatus.Storniert);
			anfrageRepository.update(anfrage.get());
			throw new IllegalArgumentException("Nicht genügend Ware auf Lager");
		} else {
			if (anfrage.get().getAnzahl().doubleValue() == angebot.getAnzahl()) {
				angebot.setBedient(true);
				angebot.setRest(0);
			} else {
				angebot.setRest(angebot.getRest() - anfrage.get().getAnzahl().doubleValue());
			}
		}

		angebotRepository.update(angebot);
		anfrageRepository.update(anfrage.get());
	}
}
