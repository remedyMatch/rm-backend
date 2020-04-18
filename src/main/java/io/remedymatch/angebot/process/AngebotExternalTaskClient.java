package io.remedymatch.angebot.process;

import java.util.HashMap;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.service.AngebotService;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.match.api.MatchProzessConstants;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
@Profile("!disableexternaltasks")
class AngebotExternalTaskClient {
    private final EngineProperties properties;
    private final AngebotService angebotService;
    private final EngineClient engineClient;

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(properties.getExternalTaskUrl())
                .backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000))
                .build();

        client.subscribe("angebotAnfrageAblehnen")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {
                    val anfrageId = externalTask.getVariable("objektId").toString();
                    angebotService.anfrageStornieren(new AngebotAnfrageId(UUID.fromString(anfrageId)));

                    //hier eventuell E-Mail versand?

                    externalTaskService.complete(externalTask);
                }).open();

        client.subscribe("angebotMatchProzessStarten")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = externalTask.getVariable("objektId").toString();

                    val variables = new HashMap<String, Object>();
                    variables.put("anfrageTyp", MatchProzessConstants.ANFRAGE_TYP_ANGEBOT);
                    variables.put("anfrageId", anfrageId);

                    engineClient.prozessStarten(MatchProzessConstants.PROZESS_KEY, new BusinessKey(UUID.fromString(anfrageId)), variables);
                    externalTaskService.complete(externalTask, variables);

                }).open();
    }
}
