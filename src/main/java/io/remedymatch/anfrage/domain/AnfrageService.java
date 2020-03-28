package io.remedymatch.anfrage.domain;

import io.remedymatch.engine.client.EngineClient;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
@Component
public class AnfrageService {

    private final AnfrageRepository anfrageRepository;
    private final EngineClient engineClient;

    @Transactional
    public void anfrageStornieren(String anfrageId) {
        val anfrage = anfrageRepository.findById(UUID.fromString(anfrageId));

        if (anfrage.isEmpty())
            throw new IllegalArgumentException("Anfrage ist nicht vorhanden und kann deshalb nicht storniert werden");

        anfrage.get().setStatus(AnfrageStatus.Storniert);
        anfrageRepository.save(anfrage.get());

        engineClient.anfrageProzessBeenden(anfrage.get().getProzessInstanzId(), new HashMap<>());
    }


}
