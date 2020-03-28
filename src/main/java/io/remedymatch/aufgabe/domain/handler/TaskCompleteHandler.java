package io.remedymatch.aufgabe.domain.handler;

import io.remedymatch.engine.TaskDTO;

import java.util.Map;

public interface TaskCompleteHandler {

    void taskPruefen(TaskDTO taskDTO, Map<String, Object> variables);

    String taskKey();
}
