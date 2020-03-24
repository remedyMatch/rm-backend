package io.remedymatch.angebot.domain;

import io.remedymatch.aufgabe.domain.TaskBeschreibungHandler;
import io.remedymatch.engine.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class AnfrageBearbeitenTaskNameHandler implements TaskBeschreibungHandler {
    @Override
    public String beschreibung(TaskDTO taskDTO) {
        return "Das ist eine Beschreibung für den Anfrage beantworten Task";
    }

    @Override
    public String taskKey() {
        return "anfrage_prozess_beantworten";
    }
}
