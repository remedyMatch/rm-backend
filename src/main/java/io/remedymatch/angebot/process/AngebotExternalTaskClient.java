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
	private static final int RETRY_TIMEOUT = 30000;
	private static final int LOCK_DURATION = 2000;
	private final EngineProperties properties;
	private final AngebotService angebotService;
	private final EngineClient engineClient;

    @PostConstruct
    public void doSubscribe() {

		ExternalTaskClient client = ExternalTaskClient.create().baseUrl(properties.getExternalTaskUrl())
				.backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000)).build();


		client.subscribe("angebot_anfrage_ablehnen_topic").lockDuration(LOCK_DURATION) //
				.handler((externalTask, externalTaskService) -> {

					val anfrageId = externalTask.getVariable("anfrageId").toString();
					angebotService.anfrageAbgelehnt(new AngebotAnfrageId(UUID.fromString(anfrageId)));

					externalTaskService.complete(externalTask);
				}).open();

		client.subscribe("angebot_anfrage_stornieren_topic").lockDuration(LOCK_DURATION) //
				.handler((externalTask, externalTaskService) -> {

                    Integer retries = externalTask.getRetries();
                    if (retries == null) {
                        retries = 3;
                    } else {
                        retries--;
                    }


                    val anfrageId = externalTask.getVariable("objektId").toString();
                    try {

                        angebotService.anfrageAbgelehnt(new AngebotAnfrageId(UUID.fromString(anfrageId)));

                        //hier eventuell E-Mail versand?
                    } catch (Exception e) {
						externalTaskService.handleFailure(externalTask, e.getMessage(), null, retries, RETRY_TIMEOUT);
                    }

                    externalTaskService.complete(externalTask);


                }).open();

        client.subscribe("angebotMatchProzessStarten")
                .lockDuration(LOCK_DURATION)
                .handler((externalTask, externalTaskService) -> {

					// Vorerst nichts - wurde bereits ueber Service storniert - vielleicht mal
					// umbauen

					externalTaskService.complete(externalTask);
				}).open();

		client.subscribe("angebot_anfrage_match_prozess_starten_topic").lockDuration(LOCK_DURATION) //
				.handler((externalTask, externalTaskService) -> {

					val anfrageId = externalTask.getVariable("anfrageId").toString();

                    val variables = new HashMap<String, Object>();
                    variables.put("anfrageTyp", MatchProzessConstants.ANFRAGE_TYP_ANGEBOT);
                    variables.put("anfrageId", anfrageId);

					engineClient.prozessStarten( //
							MatchProzessConstants.PROZESS_KEY, //
							new BusinessKey(UUID.fromString(anfrageId)), //
							variables);

					externalTaskService.complete(externalTask, variables);

                }).open();
    }
}
