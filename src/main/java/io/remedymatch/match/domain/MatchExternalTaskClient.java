package io.remedymatch.match.domain;

import io.remedymatch.properties.RmBackendProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@AllArgsConstructor
@Component
public class MatchExternalTaskClient {
    private final RmBackendProperties properties;
    private final MatchRepository matchRepository;

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(properties.getEngineUrl() + "/rest")
                .build();


        client.subscribe("auslieferungBestaetigung")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {

                    val matchId = externalTask.getVariable("matchId").toString();
                    val match = matchRepository.findById(UUID.fromString(matchId));
                    match.get().setStatus(MatchStatus.Ausgeliefert);
                    externalTaskService.complete(externalTask);
                }).open();

        client.subscribe("logistikartVerarbeiten")
                .lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {
                    externalTaskService.complete(externalTask);
                }).open();
    }
}
