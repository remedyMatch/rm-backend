package io.remedymatch.bedarf.process;

import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.domain.service.BedarfService;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@AllArgsConstructor
@Component
@Log4j2
@Profile("!disableexternaltasks")
class BedarfExternalTaskClient {
    private final EngineProperties properties;
    private final BedarfService bedarfService;
    final static String VAR_ANFRAGE_ID = "anfrage_id";
    final static String VAR_BEDARF_SCHLIESSEN = "bedarf_geschlossen";

    @PostConstruct
    public void doSubscribe() {

        ExternalTaskClient client = ExternalTaskClient.create().baseUrl(properties.getExternalTaskUrl())
                .backoffStrategy(new ExponentialBackoffStrategy(3000, 2, 3000)).build();

        client.subscribe("bedarf_anfrage_ablehnen_topic").lockDuration(2000) //
                .handler((externalTask, externalTaskService) -> {
                    try {
                        // TODO Benachrichtigung einstellen?

                        externalTaskService.complete(externalTask);
                    } catch (Exception e) {
                        log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                        externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
                    }
                }).open();

        client.subscribe("bedarf_anfrage_stornieren_topic").lockDuration(2000) //
                .handler((externalTask, externalTaskService) -> {

                    // TODO Benachrichtigung einstellen, Bedarf anlegen?

                    externalTaskService.complete(externalTask);
                }).open();

        client.subscribe("bedarf_anfrage_schliessen_topic").lockDuration(2000) //
                .handler((externalTask, externalTaskService) -> {
                    try {
                        // TODO Benachrichtigung einstellen?
                        val anfrageId = new BedarfAnfrageId(UUID.fromString(externalTask.getVariable(VAR_ANFRAGE_ID).toString()));
                        val angebotId = new BedarfId(UUID.fromString(externalTask.getBusinessKey()));
                        bedarfService.bedarfAnfrageSchliessen(angebotId, anfrageId);
                        // TODO Benachrichtigung senden?
                        externalTaskService.complete(externalTask, Variables.createVariables().putValue(VAR_BEDARF_SCHLIESSEN, true));
                    } catch (Exception e) {
                        log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                        externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
                    }
                }).open();


        client.subscribe("bedarf_anfrage_match_anlegen_topic").lockDuration(2000) //
                .handler((externalTask, externalTaskService) -> {
                    try {
                        val anfrageId = new BedarfAnfrageId(UUID.fromString(externalTask.getVariable(VAR_ANFRAGE_ID).toString()));
                        val bedarfId = new BedarfId(UUID.fromString(externalTask.getBusinessKey()));
                        bedarfService.bedarfAnfrageStatusSetzen(anfrageId, bedarfId, BedarfAnfrageStatus.MATCHED);
                        //TODO Benachrichtigung senden?
                        externalTaskService.complete(externalTask);
                    } catch (Exception e) {
                        log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                        externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
                    }
                }).open();

        client.subscribe("angebot_schliessen_topic").lockDuration(2000) //
                .handler((externalTask, externalTaskService) -> {
                    try {
                        val bedarfId = new BedarfId(UUID.fromString(externalTask.getBusinessKey()));
                        bedarfService.bedarfAlsGeschlossenMarkieren(bedarfId);

                        //TODO Benachrichtigung senden?
                        externalTaskService.complete(externalTask);
                    } catch (Exception e) {
                        log.error("Der External Task konnte nicht abgeschlossen werden.", e);
                        externalTaskService.handleFailure(externalTask, e.getMessage(), null, 0, 10000);
                    }
                }).open();
    }
}
