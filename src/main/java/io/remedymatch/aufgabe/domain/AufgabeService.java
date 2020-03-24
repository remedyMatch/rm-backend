package io.remedymatch.aufgabe.domain;

import io.remedymatch.engine.EngineClient;
import io.remedymatch.engine.TaskDTO;
import io.remedymatch.person.domain.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class AufgabeService {

    private final EngineClient engineClient;


    @Transactional
    public List<TaskDTO> aufgabenLaden(PersonEntity person) {
        val aufgaben = engineClient.ladeAlleTask(person.getInstitution().getId().toString());


        return aufgaben;
    }

    @Transactional
    public void aufgabeBeantworten(String taskId, Map<String, Object> variables) {
        engineClient.taskAbschliessen(taskId, variables);
    }

    public boolean isBearbeiter(String taskId, String institutionId) {
        TaskDTO task = null;
        try {
            task = engineClient.ladeTask(taskId, institutionId);
        } catch (Error error) {
            return false;
        }

        return task != null;
    }

}
