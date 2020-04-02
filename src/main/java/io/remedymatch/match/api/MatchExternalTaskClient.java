package io.remedymatch.match.api;

import java.util.HashMap;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.stereotype.Component;

import io.remedymatch.angebot.domain.AngebotAnfrageId;
import io.remedymatch.angebot.domain.AngebotAnfrageRepository;
import io.remedymatch.bedarf.domain.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.BedarfAnfrageRepository;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.match.domain.MatchEntity;
import io.remedymatch.match.domain.MatchRepository;
import io.remedymatch.match.domain.MatchService;
import io.remedymatch.match.domain.MatchStatus;
import io.remedymatch.properties.RmBackendProperties;
import lombok.AllArgsConstructor;
import lombok.val;

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

        client.subscribe("matchErstellen")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = externalTask.getVariable("anfrageId").toString();
                    val anfrageTyp = externalTask.getVariable("anfrageTyp").toString();

                    MatchEntity match;

                    if (anfrageTyp.equals(MatchProzessConstants.ANFRAGE_TYP_BEDARF)) {
                        match = matchService.matchAusBedarfErstellen(bedarfAnfrageRepository.get(new BedarfAnfrageId(UUID.fromString(anfrageId))).get());

                    } else {
                        match = matchService.matchAusAngebotErstellen(angebotAnfrageRepository.get(new AngebotAnfrageId(UUID.fromString(anfrageId))).get());
                    }

                    val variables = new HashMap<String, Object>();
                    variables.put("lieferant", match.getInstitutionVon().getId().toString());
                    variables.put("objektId", match.getId().toString());
                    variables.put("empfaenger", match.getInstitutionAn().getId().toString());

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
