package io.remedymatch.match.api;

import io.remedymatch.anfrage.domain.AnfrageRepository;
import io.remedymatch.angebot.domain.AngebotRepository;
import io.remedymatch.match.domain.MatchRepository;
import io.remedymatch.match.domain.MatchService;
import io.remedymatch.match.domain.MatchStatus;
import io.remedymatch.properties.RmBackendProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@AllArgsConstructor
@Component
public class MatchExternalTaskClient {
    private final RmBackendProperties properties;
    private final MatchRepository matchRepository;
    private final MatchService matchService;
    private final AnfrageRepository anfrageRepository;

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(properties.getEngineUrl() + "/rest")
                .backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000))
                .build();

        client.subscribe("auslieferungBestaetigung")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {
                    val matchId = externalTask.getVariable("matchId").toString();
                    val match = matchRepository.findById(UUID.fromString(matchId));
                    match.get().setStatus(MatchStatus.Ausgeliefert);
                    externalTaskService.complete(externalTask);
                }).open();


        client.subscribe("matchErstellen")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = externalTask.getVariable("anfrageId").toString();
                    val match = matchService.matcheErstellen(anfrageRepository.findById(UUID.fromString(anfrageId)).get());

                    val variables = Variables.createVariables();
                    variables.putValue("lieferant", match.getInstitutionVon().getId().toString());
                    variables.putValue("matchId", match.getId().toString());
                    variables.putValue("empfaenger", match.getInstitutionAn().getId().toString());

                    externalTaskService.complete(externalTask, variables);

                }).open();

        client.subscribe("stornierungVerarbeiten")
                .lockDuration(2000)
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
}
