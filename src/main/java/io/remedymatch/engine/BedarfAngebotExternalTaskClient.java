package io.remedymatch.engine;

import io.remedymatch.anfrage.domain.AnfrageService;
import io.remedymatch.properties.RmBackendProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@Component
public class BedarfAngebotExternalTaskClient {
    private final RmBackendProperties properties;
    private final AnfrageService anfrageService;

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(properties.getEngineUrl() + "/rest")
                .build();


        client.subscribe("anfrageAblehnung")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = externalTask.getVariable("anfrageId").toString();

                    anfrageService.anfrageStornieren(anfrageId);

                    externalTaskService.complete(externalTask);

                }).open();

        client.subscribe("anfrageBestaetigung")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = externalTask.getVariable("anfrageId").toString();

                    anfrageService.anfrageAnnehmen(anfrageId);

                    externalTaskService.complete(externalTask);

                }).open();
    }
}
