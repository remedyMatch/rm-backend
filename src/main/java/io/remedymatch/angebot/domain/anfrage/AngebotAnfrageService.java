package io.remedymatch.angebot.domain.anfrage;

import io.remedymatch.engine.client.EngineClient;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.remedymatch.angebot.api.AngebotAnfrageProzessConstants.ANFRAGE_STORNIEREN_MESSAGE;

@AllArgsConstructor
@Component
public class AngebotAnfrageService {

	private final AngebotAnfrageRepository anfrageRepository;
	private final EngineClient engineClient;

	@Transactional
	public void anfrageStornieren(String anfrageId) {
		this.anfrageStornieren(anfrageId, new HashMap<>());
	}

	@Transactional
	public void anfrageStornieren(String anfrageId, Map<String, Object> variables) {
		val anfrage = anfrageRepository.get(UUID.fromString(anfrageId));

		if (anfrage.isEmpty()) {
			throw new IllegalArgumentException("Anfrage ist nicht vorhanden und kann deshalb nicht storniert werden");
		}

		AngebotAnfrageEntity angebotAnfrage = anfrage.get();
		if (!AngebotAnfrageStatus.Offen.equals(angebotAnfrage.getStatus())) {
			throw new IllegalArgumentException(
					"Eine Anfrage, die nicht im Status offen ist, kann nicht storniert werden");
		}

		angebotAnfrage.setStatus(AngebotAnfrageStatus.Storniert);
		anfrageRepository.update(angebotAnfrage);

		engineClient.messageKorrelieren(angebotAnfrage.getProzessInstanzId(), ANFRAGE_STORNIEREN_MESSAGE, variables);
	}

}
