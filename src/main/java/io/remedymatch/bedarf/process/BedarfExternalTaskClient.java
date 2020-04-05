package io.remedymatch.bedarf.process;

import io.remedymatch.bedarf.domain.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.BedarfService;
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
public class BedarfExternalTaskClient {
    private final RmBackendProperties properties;
    private final BedarfService bedarfService;
    private final EngineClient engineClient;

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(properties.getEngineUrl() + "/rest")
                .backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000))
                .build();

        client.subscribe("bedarfAnfrageAblehnen")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    //set retries for the external task in the engine
                    Integer retries = externalTask.getRetries();
                    if (retries == null) {
                        retries = 3;
                    } else {
                        retries--;
                    }

                    val anfrageId = externalTask.getVariable("objektId").toString();
                    try {
                        bedarfService.anfrageStornieren(new BedarfAnfrageId(UUID.fromString(anfrageId)));
                    } catch (Exception e) {
                        externalTaskService.handleFailure(externalTask, "Hello I am an error", "more space for details", retries, 30000);
                    }

                    //hier eventuell E-Mail versand?

                    externalTaskService.complete(externalTask);
                }).open();


        client.subscribe("bedarfMatchProzessStarten")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = externalTask.getVariable("objektId").toString();

                    val variables = new HashMap<String, Object>();
                    variables.put("anfrageTyp", MatchProzessConstants.ANFRAGE_TYP_BEDARF);
                    variables.put("anfrageId", anfrageId);

                    engineClient.prozessStarten(MatchProzessConstants.PROZESS_KEY, anfrageId, variables);

                    externalTaskService.complete(externalTask, variables);

                }).open();
    }
}
