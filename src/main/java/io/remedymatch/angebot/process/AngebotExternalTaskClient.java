package io.remedymatch.angebot.process;

import io.remedymatch.angebot.domain.service.AngebotService;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@Component
@Slf4j
@Profile("!disableexternaltasks")
class AngebotExternalTaskClient {
    private final EngineProperties properties;
    private final AngebotService angebotService;
    private final EngineClient engineClient;
    final static String VAR_ANFRAGE_ID = "angebot_anfrage_id";


    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create().baseUrl(properties.getExternalTaskUrl())
                .backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000)).build();

        client.subscribe("angebot_anfrage_ablehnen_topic").lockDuration(2000) //
                .handler((externalTask, externalTaskService) -> {
                    try {
                        //TODO Benachrichtigung einstellen?

                        externalTaskService.complete(externalTask);
                    } catch (Exception e) {
                        log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                        externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
                    }
                }).open();

        client.subscribe("angebot_anfrage_stornieren_topic").lockDuration(2000) //
                .handler((externalTask, externalTaskService) -> {

                    //TODO Benachrichtigung einstellen, Bedarf anlegen?

                    externalTaskService.complete(externalTask);
                }).open();
    }
}
