package io.remedymatch.bedarf.domain.aufgabe;

import io.remedymatch.aufgabe.domain.handler.TaskCompleteHandler;
import io.remedymatch.bedarf.domain.BedarfService;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageRepository;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Component
public class BedarfAnfrageBearbeitenTaskCompleteHandler implements TaskCompleteHandler {

    private final BedarfAnfrageRepository bedarfAnfrageRepository;
    private final BedarfService bedarfService;

    @Override
    public void taskPruefen(TaskDTO taskDTO, Map<String, Object> variables) {

        if (!Boolean.parseBoolean(variables.get(BedarfAnfrageBearbeitenTaskContstants.ANGENOMMEN).toString())) {
            return;
        }

        val anfrage = bedarfAnfrageRepository.findById(UUID.fromString(taskDTO.getObjektId()));
        if (anfrage.isEmpty()) {
            throw new IllegalArgumentException("Anfrage mit der Id: " + taskDTO.getObjektId() + " nicht vorhanden");
        }
        bedarfService.anfrageAnnehmen(taskDTO.getObjektId());

    }

    @Override
    public String taskKey() {
        return BedarfAnfrageBearbeitenTaskContstants.TASK_KEY;
    }
}
