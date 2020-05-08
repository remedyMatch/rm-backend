package io.remedymatch.person.process;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.remedymatch.institution.domain.model.InstitutionAntragId;
import io.remedymatch.institution.process.InstitutionAntragProzessConstants;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Component
@Log4j2
@Profile("!disableexternaltasks")
public class PersonExternalTaskClient {
	private final EngineProperties properties;

	@PostConstruct
	public void doSubscribe() {

		ExternalTaskClient client = ExternalTaskClient.create().baseUrl(properties.getExternalTaskUrl())
				.backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000)).build();

		client.subscribe("institution_antrag_prozess_inst_zuweisen").lockDuration(2000)
				.handler((externalTask, externalTaskService) -> {
					try {
						val institutionId = externalTask
								.getVariable(InstitutionAntragProzessConstants.VAR_INSTITUTION_ID);
						val antragId = new InstitutionAntragId(UUID.fromString(externalTask.getBusinessKey()));

					} catch (Exception e) {
						log.error("Der External Task konnte nicht abgeschlossen werden.", e);
						externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
					}

				}).open();

	}

}
