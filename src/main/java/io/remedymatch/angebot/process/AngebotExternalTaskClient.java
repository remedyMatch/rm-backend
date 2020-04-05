package io.remedymatch.angebot.process;

import io.remedymatch.angebot.domain.AngebotAnfrageId;
import io.remedymatch.angebot.domain.AngebotService;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.match.api.MatchProzessConstants;
import io.remedymatch.properties.RmBackendProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
@Component
public class AngebotExternalTaskClient {
    private final RmBackendProperties properties;
    private final AngebotService angebotService;
    private final EngineClient engineClient;

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(properties.getEngineUrl() + "/rest")
                .backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000))
                .build();

        client.subscribe("angebotAnfrageAblehnen")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    Integer retries = externalTask.getRetries();
                    if (retries == null) {
                        retries = 3;
                    } else {
                        retries--;
                    }


                    val anfrageId = externalTask.getVariable("objektId").toString();
                    try {

                        angebotService.anfrageStornieren(new AngebotAnfrageId(UUID.fromString(anfrageId)));

                        //hier eventuell E-Mail versand?
                    } catch (Exception e) {
                        externalTaskService.handleFailure(externalTask, "Hello I am an error", "more space for details", retries, 30000);
                    }

                    externalTaskService.complete(externalTask);


                }).open();


        //externalTaskService.handleFailure(externalTask.getId(), "I am a message", "I have more details", 1, 600000);
        //externalTask.getRetries()


        client.subscribe("angebotMatchProzessStarten")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = externalTask.getVariable("objektId").toString();

                    val variables = new HashMap<String, Object>();
                    variables.put("anfrageTyp", MatchProzessConstants.ANFRAGE_TYP_ANGEBOT);
                    variables.put("anfrageId", anfrageId);

                    engineClient.prozessStarten(MatchProzessConstants.PROZESS_KEY, anfrageId, variables);
                    externalTaskService.complete(externalTask, variables);

                }).open();
    }
}
