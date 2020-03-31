package io.remedymatch.match.api;

import io.remedymatch.angebot.domain.anfrage.AngebotAnfrageRepository;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageRepository;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.match.domain.MatchRepository;
import io.remedymatch.match.domain.MatchService;
import io.remedymatch.match.domain.MatchStatus;
import io.remedymatch.properties.RmBackendProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
@Component
public class MatchExternalTaskClient {
    private final RmBackendProperties properties;
    private final MatchRepository matchRepository;
    private final MatchService matchService;
    private final BedarfAnfrageRepository bedarfAnfrageRepository;
    private final AngebotAnfrageRepository angebotAnfrageRepository;
    private final EngineClient engineClient;

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(properties.getEngineUrl() + "/rest")
                .backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000))
                .build();

        client.subscribe("auslieferungBestaetigung")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {
                    val matchId = externalTask.getVariable("objektId").toString();
                    val match = matchRepository.findById(UUID.fromString(matchId));
                    match.get().setStatus(MatchStatus.Ausgeliefert);
                    externalTaskService.complete(externalTask);
                }).open();


        client.subscribe("angebotMatchProzessStarten")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = externalTask.getVariable("objektId").toString();

                    val match = matchService.matchAusAngebotErstellen(angebotAnfrageRepository.findById(UUID.fromString(anfrageId)).get());

                    val variables = new HashMap<String, Object>();
                    variables.put("lieferant", match.getInstitutionVon().getId().toString());
                    variables.put("objektId", match.getId().toString());
                    variables.put("empfaenger", match.getInstitutionAn().getId().toString());

                    engineClient.prozessStarten(MatchProzessConstants.PROZESS_KEY, match.getId().toString(), variables);

                    externalTaskService.complete(externalTask, variables);

                }).open();

        client.subscribe("bedarfMatchProzessStarten")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = externalTask.getVariable("objektId").toString();

                    val match = matchService.matchAusBedarfErstellen(bedarfAnfrageRepository.findById(UUID.fromString(anfrageId)).get());

                    val variables = new HashMap<String, Object>();
                    variables.put("lieferant", match.getInstitutionVon().getId().toString());
                    variables.put("objektId", match.getId().toString());
                    variables.put("empfaenger", match.getInstitutionAn().getId().toString());

                    engineClient.prozessStarten(MatchProzessConstants.PROZESS_KEY, match.getId().toString(), variables);

                    externalTaskService.complete(externalTask, variables);

                }).open();

        client.subscribe("matchStornierungVerarbeiten")
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
