package io.remedymatch.aufgabe.domain;

import io.remedymatch.engine.TaskDTO;

public interface TaskBeschreibungHandler {

    String beschreibung(TaskDTO taskDTO);

    String taskKey();
}
