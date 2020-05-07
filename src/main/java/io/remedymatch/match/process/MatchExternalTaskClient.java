package io.remedymatch.match.process;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.camunda.bpm.client.task.ExternalTask;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.service.AngebotAnfrageSucheService;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageSucheService;
import io.remedymatch.match.domain.MatchService;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Component
@Log4j2
@Profile("!disableexternaltasks")
public class MatchExternalTaskClient {
	private final EngineProperties properties;
	private final MatchService matchService;
	private final AngebotAnfrageSucheService angebotAnfrageSucheService;
	private final BedarfAnfrageSucheService bedarfAnfrageSucheService;
	final static String VAR_ANFRAGE_ID = "angebot_anfrage_id";
	final static String VAR_MATCH_ID = "angebot_match_id";

	@PostConstruct
	public void doSubscribe() {

		ExternalTaskClient client = ExternalTaskClient.create().baseUrl(properties.getExternalTaskUrl())
				.backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000)).build();

		client.subscribe("angebot_anfrage_match_anlegen_topic").lockDuration(2000)
				.handler((externalTask, externalTaskService) -> {
					try {

						val variables = matchAusAngebotErstellen(externalTask);
						externalTaskService.complete(externalTask, variables);
					} catch (Exception e) {
						log.error("Der External Task konnte nicht abgeschlossen werden.", e);
						externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
					}

				}).open();

		client.subscribe("bedarf_anfrage_match_anlegen_topic").lockDuration(2000)
				.handler((externalTask, externalTaskService) -> {
					try {

						val variables = matchAusBedarfErstellen(externalTask);
						externalTaskService.complete(externalTask, variables);
					} catch (Exception e) {
						log.error("Der External Task konnte nicht abgeschlossen werden.", e);
						externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
					}

				}).open();

		client.subscribe("matchStornierungVerarbeiten").lockDuration(2000)
				.handler((externalTask, externalTaskService) -> {

					System.out.println("Stornierung erhalten");

//                    val anfrageId = externalTask.getVariable("anfrageId").toString();
//                    val prozessTyp = externalTask.getVariable("prozessTyp").toString();
//
//                    switch (prozessTyp) {
//                        case AnfrageProzessConstants.PROZESS_TYP_ANGEBOT:
//                            bedarfService.anfrageStornieren(anfrageId);
//                            break;
//                        case AnfrageProzessConstants.PROZESS_TYP_BEDARF:
//                            angebotService.anfrageStornieren(anfrageId);
//                            break;
//                    }

					externalTaskService.complete(externalTask);
				}).open();

	}

	@Transactional
	public Map<String, Object> matchAusBedarfErstellen(ExternalTask externalTask) {
		val anfrageId = externalTask.getVariable(VAR_ANFRAGE_ID).toString();
		val match = matchService.matchAusBedarfErstellen(
				bedarfAnfrageSucheService.findAnfrage(new BedarfAnfrageId(UUID.fromString(anfrageId))).get());

		Map<String, Object> variables = new HashMap<>();
		variables.put(VAR_MATCH_ID, match.getId().getValue().toString());
		return variables;
	}

	@Transactional
	public Map<String, Object> matchAusAngebotErstellen(ExternalTask externalTask) {
		val anfrageId = externalTask.getVariable(VAR_ANFRAGE_ID).toString();
		val match = matchService.matchAusAngebotErstellen(
				angebotAnfrageSucheService.findAnfrage(new AngebotAnfrageId(UUID.fromString(anfrageId))).get());

		Map<String, Object> variables = new HashMap<>();
		variables.put(VAR_MATCH_ID, match.getId().getValue().toString());
		return variables;
	}
}
