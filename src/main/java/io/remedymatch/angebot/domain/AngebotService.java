package io.remedymatch.angebot.domain;

import static io.remedymatch.angebot.api.AngebotAnfrageProzessConstants.ANFRAGE_STORNIEREN_MESSAGE;
import static io.remedymatch.angebot.api.AngebotAnfrageProzessConstants.PROZESS_KEY;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Service
public class AngebotService {
	private final AngebotRepository angebotRepository;
	private final AngebotAnfrageRepository angebotAnfrageRepository;
	private final EngineClient engineClient;

	@Transactional
	public void angebotMelden(Angebot angebot, InstitutionEntity institutionEntity) {
		angebot.setInstitution(institutionEntity);
		angebot.setRest(angebot.getAnzahl());
		angebotRepository.add(angebot);
	}

	@Transactional
	public void angebotLoeschen(final AngebotId id) {
		val angebot = angebotRepository.get(id);

		if (angebot.isEmpty()) {
			throw new IllegalIdentifierException("Angebot ist nicht vorhanden");
		}

		if (angebot.get().getAnfragen() != null) {
			angebot.get().getAnfragen().stream()
					.filter(anfrage -> anfrage.getStatus().equals(AngebotAnfrageStatus.Offen))
					.forEach(anfrage -> anfrageStornierenX(anfrage.getId()));
		}

		angebotRepository.delete(angebot.get().getId());
	}

	@Transactional
	public Optional<AngebotEntity> angebotLaden(final AngebotId id) {
		return angebotRepository.get(id).map(AngebotEntityConverter::convert);
	}

	@Transactional
	public void starteAnfrage(//
			final AngebotId angebotId, //
			final InstitutionEntity anfrager, //
			final String kommentar, //
			final UUID standortId, //
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

		var anfrage = AngebotAnfrage.builder() //
				.institutionVon(anfrager) //
				.institutionAn(angebot.get().getInstitution()) //
				.kommentar(kommentar) //
				.standortAn(angebot.get().getStandort()) //
				.standortVon(standort) //
				.angebot(angebot.get()) //
				.anzahl(BigDecimal.valueOf(anzahl)) //
				.status(AngebotAnfrageStatus.Offen) //
				.build();

		anfrage = angebotAnfrageRepository.update(anfrage);

		var variables = new HashMap<String, Object>();
		variables.put("institution", angebot.get().getInstitution().getId().toString());
		variables.put("objektId", anfrage.getId().toString());

		val prozessInstanzId = engineClient.prozessStarten(PROZESS_KEY, anfrage.getId().toString(), variables);
		anfrage.setProzessInstanzId(prozessInstanzId);
		angebotAnfrageRepository.update(anfrage);
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
	
	private void anfrageStornierenX(final AngebotAnfrageId anfrageId) {
		val anfrage = angebotAnfrageRepository.get(anfrageId);

		if (anfrage.isEmpty()) {
			throw new IllegalArgumentException("Anfrage ist nicht vorhanden und kann deshalb nicht storniert werden");
		}

		AngebotAnfrage angebotAnfrage = anfrage.get();
		if (!AngebotAnfrageStatus.Offen.equals(angebotAnfrage.getStatus())) {
			throw new IllegalArgumentException(
					"Eine Anfrage, die nicht im Status offen ist, kann nicht storniert werden");
		}

		angebotAnfrage.setStatus(AngebotAnfrageStatus.Storniert);
		angebotAnfrageRepository.update(angebotAnfrage);

		engineClient.messageKorrelieren(angebotAnfrage.getProzessInstanzId(), ANFRAGE_STORNIEREN_MESSAGE, new HashMap<>());
	}
}
