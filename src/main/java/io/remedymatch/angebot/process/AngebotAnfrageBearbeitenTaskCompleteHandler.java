package io.remedymatch.angebot.process;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import io.remedymatch.angebot.domain.AngebotAnfrageId;
import io.remedymatch.angebot.domain.AngebotAnfrageRepository;
import io.remedymatch.angebot.domain.AngebotService;
import io.remedymatch.aufgabe.domain.handler.TaskCompleteHandler;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
public class AngebotAnfrageBearbeitenTaskCompleteHandler implements TaskCompleteHandler {

    private final AngebotAnfrageRepository anfrageRepository;
    private final AngebotService angebotService;

    @Override
    public void taskPruefen(TaskDTO taskDTO, Map<String, Object> variables) {

        if (!Boolean.parseBoolean(variables.get(AnfrageBearbeitenTaskContstants.ANGENOMMEN).toString())) {
            return;
        }

        val anfrage = anfrageRepository.get(new AngebotAnfrageId(UUID.fromString(taskDTO.getObjektId())));
        if (anfrage.isEmpty()) {
            throw new IllegalArgumentException("Anfrage mit der Id: " + taskDTO.getObjektId() + " nicht vorhanden");
        }
        //Anfrage annehmen
        angebotService.anfrageAnnehmen(new AngebotAnfrageId(UUID.fromString(taskDTO.getObjektId())));
    }

    @Override
    public String taskKey() {
        return AnfrageBearbeitenTaskContstants.TASK_KEY;
    }
}
