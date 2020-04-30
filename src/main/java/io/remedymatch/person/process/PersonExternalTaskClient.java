package io.remedymatch.person.process;

import io.remedymatch.person.domain.service.PersonService;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@Component
@Slf4j
@Profile("!disableexternaltasks")
public class PersonExternalTaskClient {
    private final EngineProperties properties;
    private final PersonService personService;

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create().baseUrl(properties.getExternalTaskUrl())
                .backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000)).build();


        client.subscribe("institution_antrag_prozess_inst_zuweisen").lockDuration(2000).handler((externalTask, externalTaskService) -> {
            try {


            } catch (Exception e) {
                log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
            }

        }).open();

    }


}
