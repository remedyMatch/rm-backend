package io.remedymatch.institution.process;

import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.institution.domain.model.InstitutionAntragId;
import io.remedymatch.institution.domain.service.InstitutionService;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.UUID;

import static io.remedymatch.institution.process.InstitutionAntragProzessConstants.VAR_INSTITUTION_ID;

@AllArgsConstructor
@Component
@Slf4j
@Profile("!disableexternaltasks")
public class InstitutionExternalTaskClient {
    private final EngineProperties properties;
    private final InstitutionService institutionService;
    private final EngineClient engineClient;

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create().baseUrl(properties.getExternalTaskUrl())
                .backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000)).build();

        client.subscribe("institution_antrag_prozess_antrag_ablehen").lockDuration(2000).handler((externalTask, externalTaskService) -> {

            try {
                institutionService.antragAblehnen(new InstitutionAntragId(UUID.fromString(externalTask.getBusinessKey())));
                externalTaskService.complete(externalTask);
            } catch (Exception e) {
                log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
            }
        }).open();


        client.subscribe("institution_antrag_prozess_antrag_genehmigen").lockDuration(2000).handler((externalTask, externalTaskService) -> {

            try {
                institutionService.antragGenehmigen(new InstitutionAntragId(UUID.fromString(externalTask.getBusinessKey())));
                externalTaskService.complete(externalTask);
            } catch (Exception e) {
                log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
            }

        }).open();

        client.subscribe("institution_antrag_prozess_inst_anlegen").lockDuration(2000).handler((externalTask, externalTaskService) -> {

            try {
                val institution = institutionService.institutionAusAntragAnlegen(new InstitutionAntragId(UUID.fromString(externalTask.getBusinessKey())));
                val variables = new HashMap<String, Object>();
                variables.put(VAR_INSTITUTION_ID, institution.getId().getValue());
                externalTaskService.complete(externalTask, variables);
            } catch (Exception e) {
                log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
            }

        }).open();

    }


}
