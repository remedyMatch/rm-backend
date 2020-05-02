package io.remedymatch.engine.client;

import com.sun.istack.NotNull;
import io.remedymatch.engine.TaskDTO;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.engine.domain.MessageKey;
import io.remedymatch.engine.domain.ProzessInstanzId;
import io.remedymatch.engine.domain.ProzessKey;
import io.remedymatch.engine.request.MessageKorrelierenRequest;
import io.remedymatch.engine.request.ProzessStartRequest;
import io.remedymatch.engine.request.TaskAbschliessenRequest;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.domain.service.PersonSucheService;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.val;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
@Validated
public class EngineClient {

    static final String ENGINE_VARIABLE_BENACHRICHTIGUNG_AN_NAME = "benachrichtigungAnName";
    static final String ENGINE_VARIABLE_BENACHRICHTIGUNG_AN_EMAIL = "benachrichtigungAnEmail";

    private final EngineProperties properties;
    private final PersonSucheService personSucheService;
    private final RestTemplate restTemplate;

    public ProzessInstanzId prozessStarten(//
                                           final @NotNull @Valid ProzessKey prozessKey, //
                                           final @NotNull @Valid BusinessKey businessKey, //
                                           final @NotNull @Valid PersonId benachrichtingungAnPerson, //
                                           final @NotNull @Valid Map<String, Object> variables) {

        val person = personSucheService.getByPersonId(benachrichtingungAnPerson);

        VariableMap prozessVariables = Variables.createVariables();
        prozessVariables.putAll(variables);
        prozessVariables.putValue(ENGINE_VARIABLE_BENACHRICHTIGUNG_AN_EMAIL, person.getEmail());
        prozessVariables.putValue(ENGINE_VARIABLE_BENACHRICHTIGUNG_AN_NAME,
                person.getVorname() + " " + person.getNachname());

        return prozessStarten(ProzessStartRequest.builder() //
                .prozessKey(prozessKey.getValue()) //
                .businessKey(businessKey.getValue().toString()) //
                .variables(prozessVariables) //
                .build());
    }

    public ProzessInstanzId prozessStarten(//
                                           final @NotNull @Valid ProzessKey prozessKey, //
                                           final @NotNull @Valid BusinessKey businessKey, //
                                           final @NotNull @Valid Map<String, Object> variables) {

        return prozessStarten(ProzessStartRequest.builder() //
                .prozessKey(prozessKey.getValue()) //
                .businessKey(businessKey.getValue().toString()) //
                .variables(variables) //
                .build());
    }

    private ProzessInstanzId prozessStarten(final @NotNull @Valid ProzessStartRequest request) {

        ResponseEntity<String> response = restTemplate
                .postForEntity(properties.getRemedyRestApiUrl() + "/prozess/start", request, String.class);

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Beim Starten des Prozesses ist etwas fehlgeschlagen");
        }

        return new ProzessInstanzId(response.getBody());
    }

    public TaskDTO ladeTask(String taskId, String institutionId) {
        ResponseEntity<TaskDTO> taskResponse = restTemplate.getForEntity(
                properties.getRemedyRestApiUrl() + "/task/" + institutionId + "/" + taskId, TaskDTO.class);

        if (taskResponse.getStatusCode().isError()) {
            throw new RuntimeException("Beim laden des Task ist etwas fehlgeschlagen");
        }

        val task = taskResponse.getBody();
        return task;
    }

    public List<TaskDTO> ladeAlleTask(String institutionId) {
        ResponseEntity<TaskDTO[]> taskResponse = restTemplate
                .getForEntity(properties.getRemedyRestApiUrl() + "/task/institution/" + institutionId, TaskDTO[].class);
        if (taskResponse.getStatusCode().isError()) {
            throw new RuntimeException("Beim laden der Task ist etwas fehlgeschlagen");
        }
        val task = taskResponse.getBody();
        return Arrays.asList(task);
    }

    public void taskAbschliessen(String task, Map<String, Object> variables) {

        val request = TaskAbschliessenRequest.builder().variables(variables).build();
        ResponseEntity<Void> response = restTemplate.postForEntity(properties.getRemedyRestApiUrl() + "/task/" + task,
                request, Void.class);

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Beim abschliessen ist etwas fehlgeschlagen");
        }
    }

    //Message

    public void messageKorrelieren(
            final @NotNull @Valid ProzessKey prozessKey, //
            final @NotNull @Valid BusinessKey businessKey, //
            final @NotNull @Valid Map<String, Object> variablesEqual, //
            final @NotNull @Valid MessageKey messageKey) {

        messageKorrelieren(MessageKorrelierenRequest.builder() //
                .prozessKey(prozessKey.getValue()) //
                .messageKey(messageKey.getValue()) //
                .variablesEqual(variablesEqual) //
                .build());
    }

    public void messageKorrelieren(
            final @NotNull @Valid ProzessKey prozessKey, //
            final @NotNull @Valid BusinessKey businessKey, //
            final @NotNull @Valid MessageKey messageKey) {

        messageKorrelieren(MessageKorrelierenRequest.builder() //
                .prozessKey(prozessKey.getValue()) //
                .messageKey(messageKey.getValue()) //
                .businesskey(businessKey.getValue().toString()) //
                .build());
    }

    public void messageKorrelieren(
            final @NotNull @Valid ProzessKey prozessKey, //
            final @NotNull @Valid BusinessKey businessKey, //
            final @NotNull @Valid MessageKey messageKey, //
            final @NotNull @Valid Map<String, Object> variables) {

        messageKorrelieren(MessageKorrelierenRequest.builder() //
                .prozessKey(prozessKey.getValue()) //
                .messageKey(messageKey.getValue()) //
                .businesskey(businessKey.getValue().toString()) //
                .variables(variables) //
                .build());
    }

    private void messageKorrelieren(final @NotNull @Valid MessageKorrelierenRequest request) {

        ResponseEntity<Void> response = restTemplate.postForEntity(//
                UriComponentsBuilder.fromHttpUrl(properties.getRemedyRestApiUrl()) //
                        .path("/message/korrelieren") //
                        .build().toUri(), //
                request, //
                Void.class);

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Beim korrelieren ist etwas fehlgeschlagen: " + response.getStatusCodeValue());
        }
    }
}
