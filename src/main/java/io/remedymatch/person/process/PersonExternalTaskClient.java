package io.remedymatch.person.process;

import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.domain.service.PersonService;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static io.remedymatch.institution.domain.service.InstitutionProzessService.VAR_ANTRAGSTELLER;
import static io.remedymatch.institution.domain.service.InstitutionProzessService.VAR_INSTITUTION_ID;

@AllArgsConstructor
@Component
@Log4j2
@Profile("!disableexternaltasks")
public class PersonExternalTaskClient {
    private final EngineProperties properties;
    private final PersonService personService;


    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create().baseUrl(properties.getExternalTaskUrl())
                .backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000)).build();

        client.subscribe("institution_antrag_prozess_inst_zuweisen").lockDuration(2000)
                .handler((externalTask, externalTaskService) -> {
                    try {
                        val institutionId = new InstitutionId(UUID.fromString(externalTask.getVariable(VAR_INSTITUTION_ID).toString()));
                        val antragsteller = new PersonId(UUID.fromString(externalTask.getVariable(VAR_ANTRAGSTELLER).toString()));
                        personService.neueInstitutionZuweisen(antragsteller, institutionId, true);

                    } catch (Exception e) {
                        log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                        externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
                    }

                }).open();

    }

}
