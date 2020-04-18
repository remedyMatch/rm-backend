package io.remedymatch.match.process;

import java.util.HashMap;
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
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.match.api.MatchProzessConstants;
import io.remedymatch.match.domain.Match;
import io.remedymatch.match.domain.MatchId;
import io.remedymatch.match.domain.MatchRepository;
import io.remedymatch.match.domain.MatchService;
import io.remedymatch.match.domain.MatchStatus;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
@Profile("!disableexternaltasks")
public class MatchExternalTaskClient {
	private final EngineProperties properties;
	private final MatchRepository matchRepository;
	private final MatchService matchService;
	private final AngebotAnfrageSucheService angebotAnfrageSucheService;
	private final BedarfAnfrageSucheService bedarfAnfrageSucheService;
	private final EngineClient engineClient;

	@PostConstruct
	public void doSubscribe() {

		ExternalTaskClient client = ExternalTaskClient.create().baseUrl(properties.getExternalTaskUrl())
				.backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000)).build();

		client.subscribe("auslieferungBestaetigung").lockDuration(2000).handler((externalTask, externalTaskService) -> {
			val matchId = externalTask.getVariable("objektId").toString();
			val match = matchRepository.get(new MatchId(UUID.fromString(matchId)));
			match.get().setStatus(MatchStatus.Ausgeliefert);
			matchRepository.save(match.get());

			externalTaskService.complete(externalTask);
		}).open();

		client.subscribe("matchErstellen").lockDuration(2000).handler((externalTask, externalTaskService) -> {

			val variables = matchErstellen(externalTask);

			externalTaskService.complete(externalTask, variables);

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
	public HashMap<String, Object> matchErstellen(ExternalTask externalTask) {
		val anfrageId = externalTask.getVariable("anfrageId").toString();
		val anfrageTyp = externalTask.getVariable("anfrageTyp").toString();

		Match match;

		if (anfrageTyp.equals(MatchProzessConstants.ANFRAGE_TYP_BEDARF)) {
			match = matchService.matchAusBedarfErstellen(
					bedarfAnfrageSucheService.findAnfrage(new BedarfAnfrageId(UUID.fromString(anfrageId))).get());

		} else {
			match = matchService.matchAusAngebotErstellen(
					angebotAnfrageSucheService.findAnfrage(new AngebotAnfrageId(UUID.fromString(anfrageId))).get());
		}

		val variables = new HashMap<String, Object>();
		variables.put("lieferant", match.getInstitutionVon().getId().getValue().toString());
		variables.put("objektId", match.getId().getValue().toString());
		variables.put("empfaenger", match.getInstitutionAn().getId().getValue().toString());

		return variables;
	}
}
