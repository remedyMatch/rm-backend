package io.remedymatch.bedarf.process;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import io.remedymatch.aufgabe.domain.handler.TaskCompleteHandler;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageRepository;
import io.remedymatch.bedarf.domain.service.BedarfService;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
public class BedarfAnfrageBearbeitenTaskCompleteHandler implements TaskCompleteHandler {

    private final BedarfAnfrageRepository bedarfAnfrageRepository;
    private final BedarfService  bedarfService;

    @Override
    public void taskPruefen(TaskDTO taskDTO, Map<String, Object> variables) {

        if (!Boolean.parseBoolean(variables.get(BedarfAnfrageBearbeitenTaskContstants.ANGENOMMEN).toString())) {
            return;
        }

        val anfrage = bedarfAnfrageRepository.get(new BedarfAnfrageId(UUID.fromString(taskDTO.getObjektId())));
        if (anfrage.isEmpty()) {
            throw new IllegalArgumentException("Anfrage mit der Id: " + taskDTO.getObjektId() + " nicht vorhanden");
        }
        bedarfService.anfrageAnnehmen(new BedarfAnfrageId(UUID.fromString(taskDTO.getObjektId())));

    }

    @Override
    public String taskKey() {
        return BedarfAnfrageBearbeitenTaskContstants.TASK_KEY;
    }
}
