package io.remedymatch.match.domain.aufgabe;

import io.remedymatch.aufgabe.domain.handler.TaskCompleteHandler;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@AllArgsConstructor
@Component
public class WareneingangBestaetigenTaskCompleteHandler implements TaskCompleteHandler {

    @Override
    public void taskPruefen(TaskDTO taskDTO, Map<String, Object> variables) {

    }

    @Override
    public String taskKey() {
        return WareneingangBestaetigenTaskContstants.TASK_KEY;
    }
}
