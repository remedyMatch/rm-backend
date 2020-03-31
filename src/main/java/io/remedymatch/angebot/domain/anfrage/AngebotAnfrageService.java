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
        val anfrage = anfrageRepository.findById(UUID.fromString(anfrageId));

        if (anfrage.isEmpty())
            throw new IllegalArgumentException("Anfrage ist nicht vorhanden und kann deshalb nicht storniert werden");

        if (!anfrage.get().getStatus().equals(AngebotAnfrageStatus.Offen)) {
            throw new IllegalArgumentException("Eine Anfrage, die nicht im Status offen ist, kann nicht storniert werden");
        }

        anfrage.get().setStatus(AngebotAnfrageStatus.Storniert);
        anfrageRepository.save(anfrage.get());

        engineClient.messageKorrelieren(anfrage.get().getProzessInstanzId(), ANFRAGE_STORNIEREN_MESSAGE, variables);
    }

}
