package io.remedymatch.aufgabe.domain;

import io.remedymatch.aufgabe.domain.handler.TaskBeschreibungHandler;
import io.remedymatch.aufgabe.domain.handler.TaskCompleteHandler;
import io.remedymatch.engine.TaskDTO;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class AufgabeService {

    private final EngineClient engineClient;
    private final UserContextService userService;

    @Qualifier("TaskBeschreibungHandlerMap")
    private Map<String, TaskBeschreibungHandler> beschreibungHandlerMap;

    @Qualifier("TaskCompleteHandlerMap")
    private Map<String, TaskCompleteHandler> taskCompleteHandlerMap;

    @Transactional
    public List<TaskDTO> aufgabenLaden(Person person) {
        val aufgaben = engineClient.ladeAlleTask(person.getAktuelleInstitution().getInstitution().getId().getValue().toString());

        aufgaben.forEach(aufgabe -> {
                    if (beschreibungHandlerMap.containsKey(aufgabe.getTaskKey())) {
                        aufgabe.setDisplayName(beschreibungHandlerMap.get(aufgabe.getTaskKey()).beschreibung(aufgabe));
                    } else {
                        aufgabe.setDisplayName("Keine Beschreibung vorhanden");
                    }
                }
        );

        return aufgaben;
    }


    @Transactional
    public void aufgabeAbschließen(String taskId, Map<String, Object> variables) {
        val person = userService.getContextUser();
        val task = engineClient.ladeTask(taskId, person.getAktuelleInstitution().getInstitution().getId().getValue().toString());
        if (taskCompleteHandlerMap.containsKey(task.getTaskKey())) {
            taskCompleteHandlerMap.get(task.getTaskKey()).taskPruefen(task, variables);
        }
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
