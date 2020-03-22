package io.remedymatch.engine;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Component
public class EngineClient {

//    public static final String PROCESS_KEY = "anfrage_prozess";
//
//    @Qualifier("remote")
//    public final RuntimeService runtimeService;
//
//    @Qualifier("remote")
//    public final TaskService taskService;

    private final RestTemplate restTemplate;

}
