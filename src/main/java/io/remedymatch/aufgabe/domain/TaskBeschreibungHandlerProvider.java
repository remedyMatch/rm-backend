package io.remedymatch.aufgabe.domain;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class TaskBeschreibungHandlerProvider {

    public final List<TaskBeschreibungHandler> taskBeschreibungHandler;

    @Bean("TaskBeschreibungHandlerMap")
    public Map<String, TaskBeschreibungHandler> taskBeschreibungHandlerMap() {
        return taskBeschreibungHandler.stream().collect(Collectors.toMap(TaskBeschreibungHandler::taskKey, t -> t));
    }

}
