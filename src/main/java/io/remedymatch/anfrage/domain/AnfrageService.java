package io.remedymatch.anfrage.domain;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Component
public class AnfrageService {

    private final AnfrageRepository anfrageRepository;

    @Transactional
    public void anfrageStornieren(String anfrageId) {
        val anfrage = anfrageRepository.findById(UUID.fromString(anfrageId));
        if (anfrage.isEmpty()) {
            throw new IllegalArgumentException("Anfrage nicht vorhanden");
        }
        anfrage.get().setStorniert(true);
        anfrageRepository.save(anfrage.get());
    }

    @Transactional
    public void anfrageAnnehmen(String anfrageId) {
        val anfrage = anfrageRepository.findById(UUID.fromString(anfrageId));
        if (anfrage.isEmpty()) {
            throw new IllegalArgumentException("Anfrage nicht vorhanden");
        }
        anfrage.get().setAngenommen(true);
        anfrageRepository.save(anfrage.get());
    }
}
