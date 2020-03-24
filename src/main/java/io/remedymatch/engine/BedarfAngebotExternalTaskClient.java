package io.remedymatch.engine;

import io.remedymatch.anfrage.domain.AnfrageRepository;
import io.remedymatch.angebot.domain.AngebotService;
import io.remedymatch.bedarf.domain.BedarfService;
import io.remedymatch.match.domain.MatchService;
import io.remedymatch.properties.RmBackendProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@AllArgsConstructor
@Component
public class BedarfAngebotExternalTaskClient {
    private final RmBackendProperties properties;
    private final BedarfService bedarfService;
    private final AngebotService angebotService;
    private final AnfrageRepository anfrageRepository;
    private final MatchService matchService;

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(properties.getEngineUrl() + "/rest")
                .build();


        client.subscribe("anfrageAblehnung")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = externalTask.getVariable("anfrageId").toString();
                    val prozessTyp = externalTask.getVariable("prozessTyp").toString();

                    switch (prozessTyp) {
                        case AnfrageProzessConstants.PROZESS_TYP_ANGEBOT:
                            bedarfService.anfrageStornieren(anfrageId);
                            break;
                        case AnfrageProzessConstants.PROZESS_TYP_BEDARF:
                            angebotService.anfrageStornieren(anfrageId);
                            break;
                    }
                    externalTaskService.complete(externalTask);
                }).open();

        client.subscribe("anfrageBestaetigung")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val anfrageId = externalTask.getVariable("anfrageId").toString();
                    val prozessTyp = externalTask.getVariable("prozessTyp").toString();

                    switch (prozessTyp) {
                        case AnfrageProzessConstants.PROZESS_TYP_BEDARF:
                            bedarfService.anfrageAnnehmen(anfrageId);
                            break;
                        case AnfrageProzessConstants.PROZESS_TYP_ANGEBOT:
                            angebotService.anfrageAnnehmen(anfrageId);
                            break;
                    }

                    val match = matchService.matcheErstellen(anfrageRepository.findById(UUID.fromString(anfrageId)).get());

                    val variables = Variables.createVariables();
                    variables.putValue("lieferant", match.getInstitutionVon().getId());
                    externalTaskService.complete(externalTask, variables);

                }).open();
    }
}
