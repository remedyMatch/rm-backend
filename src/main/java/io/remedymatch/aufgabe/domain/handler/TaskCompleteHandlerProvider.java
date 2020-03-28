package io.remedymatch.aufgabe.domain.handler;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class TaskCompleteHandlerProvider {

    public final List<TaskCompleteHandler> taskCompleteHandler;

    @Bean("TaskCompleteHandlerMap")
    public Map<String, TaskCompleteHandler> taskCompleteHandlerMap() {
        return taskCompleteHandler.stream().collect(Collectors.toMap(TaskCompleteHandler::taskKey, t -> t));
    }

}
