package io.remedymatch.bedarf.process;

import java.util.HashMap;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.service.BedarfService;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.match.api.MatchProzessConstants;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
@Profile("!disableexternaltasks")
class BedarfExternalTaskClient {
	private final EngineProperties properties;
	private final BedarfService bedarfService;
	private final EngineClient engineClient;

	@PostConstruct
	public void doSubscribe() {

		ExternalTaskClient client = ExternalTaskClient.create().baseUrl(properties.getExternalTaskUrl())
				.backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000)).build();

		client.subscribe("bedarf_anfrage_ablehnen_topic").lockDuration(2000) //
				.handler((externalTask, externalTaskService) -> {

					val anfrageId = externalTask.getVariable("anfrageId").toString();
					bedarfService.anfrageAbgelehnt(new BedarfAnfrageId(UUID.fromString(anfrageId)));

					externalTaskService.complete(externalTask);
				}).open();

		client.subscribe("bedarf_anfrage_stornieren_topic").lockDuration(2000) //
				.handler((externalTask, externalTaskService) -> {

					// Vorerst nichts - wurde bereits ueber Service storniert - vielleicht mal
					// umbauen

					externalTaskService.complete(externalTask);
				}).open();

		client.subscribe("bedarf_anfrage_match_prozess_starten_topic").lockDuration(2000) //
				.handler((externalTask, externalTaskService) -> {

					val anfrageId = externalTask.getVariable("anfrageId").toString();

					val variables = new HashMap<String, Object>();
					variables.put("anfrageTyp", MatchProzessConstants.ANFRAGE_TYP_BEDARF);
					variables.put("anfrageId", anfrageId);

					engineClient.prozessStarten( //
							MatchProzessConstants.PROZESS_KEY, //
							new BusinessKey(UUID.fromString(anfrageId)), //
							variables);
					
					externalTaskService.complete(externalTask, variables);

				}).open();
	}
}
