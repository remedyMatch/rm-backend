package io.remedymatch.bedarf.domain.anfrage;

import io.remedymatch.engine.client.EngineClient;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.remedymatch.bedarf.api.BedarfAnfrageProzessConstants.ANFRAGE_STORNIEREN_MESSAGE;

@AllArgsConstructor
@Component
public class BedarfAnfrageService {

    private final BedarfAnfrageRepository anfrageRepository;
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

        if (!anfrage.get().getStatus().equals(BedarfAnfrageStatus.Offen)) {
            throw new IllegalArgumentException("Eine Anfrage, die nicht im Status offen ist, kann nicht storniert werden");
        }

        anfrage.get().setStatus(BedarfAnfrageStatus.Storniert);
        anfrageRepository.save(anfrage.get());

        engineClient.messageKorrelieren(anfrage.get().getProzessInstanzId(), ANFRAGE_STORNIEREN_MESSAGE, variables);
    }

}
