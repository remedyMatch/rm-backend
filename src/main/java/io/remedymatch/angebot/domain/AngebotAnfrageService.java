package io.remedymatch.angebot.domain;

import static io.remedymatch.angebot.api.AngebotAnfrageProzessConstants.ANFRAGE_STORNIEREN_MESSAGE;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.engine.client.EngineClient;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
public class AngebotAnfrageService {

	private final AngebotAnfrageRepository anfrageRepository;
	private final EngineClient engineClient;

	@Transactional
	public void anfrageStornieren(final AngebotAnfrageId anfrageId) {
		anfrageStornieren(anfrageId, new HashMap<>());
	}

	private void anfrageStornieren(//
			final AngebotAnfrageId anfrageId, //
			final Map<String, Object> variables) {
		val anfrage = anfrageRepository.get(anfrageId);

		if (anfrage.isEmpty()) {
			throw new IllegalArgumentException("Anfrage ist nicht vorhanden und kann deshalb nicht storniert werden");
		}

		AngebotAnfrage angebotAnfrage = anfrage.get();
		if (!AngebotAnfrageStatus.Offen.equals(angebotAnfrage.getStatus())) {
			throw new IllegalArgumentException(
					"Eine Anfrage, die nicht im Status offen ist, kann nicht storniert werden");
		}

		angebotAnfrage.setStatus(AngebotAnfrageStatus.Storniert);
		anfrageRepository.update(angebotAnfrage);

		engineClient.messageKorrelieren(angebotAnfrage.getProzessInstanzId(), ANFRAGE_STORNIEREN_MESSAGE, variables);
	}
}
