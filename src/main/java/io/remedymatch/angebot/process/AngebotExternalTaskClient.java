package io.remedymatch.angebot.process;

import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.service.AngebotService;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.match.api.MatchProzessConstants;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
@Component
@Slf4j
@Profile("!disableexternaltasks")
class AngebotExternalTaskClient {
    private final EngineProperties properties;
    private final AngebotService angebotService;
    private final EngineClient engineClient;

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create().baseUrl(properties.getExternalTaskUrl())
                .backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000)).build();

        client.subscribe("angebot_anfrage_ablehnen_topic").lockDuration(2000) //
                .handler((externalTask, externalTaskService) -> {
                    try {
                        val anfrageId = externalTask.getVariable("anfrageId").toString();
                        angebotService.anfrageAbgelehnt(new AngebotAnfrageId(UUID.fromString(anfrageId)));
                        externalTaskService.complete(externalTask);
                    } catch (Exception e) {
                        log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                        externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
                    }
                }).open();

        client.subscribe("angebot_anfrage_stornieren_topic").lockDuration(2000) //
                .handler((externalTask, externalTaskService) -> {

                    // Vorerst nichts - wurde bereits ueber Service storniert - vielleicht mal
                    // umbauen

                    externalTaskService.complete(externalTask);
                }).open();

        client.subscribe("angebot_anfrage_match_prozess_starten_topic").lockDuration(2000) //
                .handler((externalTask, externalTaskService) -> {
                    try {
                        val anfrageId = externalTask.getVariable("anfrageId").toString();
                        val variables = new HashMap<String, Object>();
                        variables.put("anfrageTyp", MatchProzessConstants.ANFRAGE_TYP_ANGEBOT);
                        variables.put("anfrageId", anfrageId);

                        engineClient.prozessStarten( //
                                MatchProzessConstants.PROZESS_KEY, //
                                new BusinessKey(UUID.fromString(anfrageId)), //
                                variables);
                        externalTaskService.complete(externalTask, variables);
                    } catch (Exception e) {
                        log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                        externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
                    }
                }).open();
    }
}
