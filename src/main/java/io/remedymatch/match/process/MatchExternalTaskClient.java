package io.remedymatch.match.process;

import io.remedymatch.angebot.domain.AngebotAnfrageId;
import io.remedymatch.angebot.domain.AngebotAnfrageRepository;
import io.remedymatch.bedarf.domain.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.BedarfAnfrageRepository;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.match.api.MatchProzessConstants;
import io.remedymatch.match.domain.*;
import io.remedymatch.properties.RmBackendProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.camunda.bpm.client.task.ExternalTask;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

                    //set retries for the external task in the engine
                    Integer retries = externalTask.getRetries();
                    if (retries == null) {
                        retries = 3;
                    } else {
                        retries--;
                    }

                    val matchId = externalTask.getVariable("objektId").toString();
                    val match = matchRepository.get(new MatchId(UUID.fromString(matchId)));

                    try {
                        match.get().setStatus(MatchStatus.Ausgeliefert);
                        matchRepository.save(match.get());
                    } catch (Exception e) {
                        externalTaskService.handleFailure(externalTask, "Hello I am an error", "more space for details", retries, 30000);
                    }

                    externalTaskService.complete(externalTask);
                }).open();

        client.subscribe("matchErstellen")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {


                    val variables = matchErstellen(externalTask);

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

    @Transactional
    public HashMap<String, Object> matchErstellen(ExternalTask externalTask) {
        val anfrageId = externalTask.getVariable("anfrageId").toString();
        val anfrageTyp = externalTask.getVariable("anfrageTyp").toString();

        Match match;

        if (anfrageTyp.equals(MatchProzessConstants.ANFRAGE_TYP_BEDARF)) {
            match = matchService.matchAusBedarfErstellen(bedarfAnfrageRepository.get(new BedarfAnfrageId(UUID.fromString(anfrageId))).get());

        } else {
            match = matchService.matchAusAngebotErstellen(angebotAnfrageRepository.get(new AngebotAnfrageId(UUID.fromString(anfrageId))).get());
        }

        val variables = new HashMap<String, Object>();
        variables.put("lieferant", match.getInstitutionVon().getId().getValue().toString());
        variables.put("objektId", match.getId().getValue().toString());
        variables.put("empfaenger", match.getInstitutionAn().getId().getValue().toString());

        return variables;
    }
}
