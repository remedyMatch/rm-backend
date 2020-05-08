package io.remedymatch.angebot.process;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.domain.service.AngebotService;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Component
@Log4j2
@Profile("!disableexternaltasks")
class AngebotExternalTaskClient {
    private final EngineProperties properties;
    private final AngebotService angebotService;
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

        client.subscribe("angebot_anfrage_schliessen_topic").lockDuration(2000) //
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = new AngebotAnfrageId(UUID.fromString(externalTask.getVariable(VAR_ANFRAGE_ID).toString()));
                    val angebotId = new AngebotId(UUID.fromString(externalTask.getBusinessKey()));
                    angebotService.angebotAnfrageSchliessen(angebotId, anfrageId);
                    //TODO Benachrichtigung senden?
                    externalTaskService.complete(externalTask);
                }).open();
    }
}
