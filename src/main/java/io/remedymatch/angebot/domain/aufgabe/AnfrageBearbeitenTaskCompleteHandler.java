package io.remedymatch.angebot.domain.aufgabe;

import io.remedymatch.anfrage.domain.AnfrageRepository;
import io.remedymatch.aufgabe.domain.handler.TaskCompleteHandler;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@AllArgsConstructor
@Component
public class AnfrageBearbeitenTaskCompleteHandler implements TaskCompleteHandler {

    private final AnfrageRepository anfrageRepository;

    @Override
    public void taskPruefen(TaskDTO taskDTO, Map<String, Object> variables) {
//        if (!Boolean.parseBoolean(variables.get(ANGENOMMEN).toString())) {
//            return;
//        }
//        val anfrage = anfrageRepository.findById(UUID.fromString(taskDTO.getObjektId()));
//        if (anfrage.get().getBedarf() != null) {
//            return;
//        }
//        val angebot = anfrage.get().getAngebot();
//        val anzahl = Double.parseDouble(variables.get(ANZAHL).toString());
//        if (angebot.getAnzahl() < anzahl) {
//            throw new IllegalArgumentException("Die Anfrage übersteigt das momentane Angebot");
//        }

    }

    @Override
    public String taskKey() {
        return null;
    }
}
