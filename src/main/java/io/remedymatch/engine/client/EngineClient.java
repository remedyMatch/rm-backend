package io.remedymatch.engine.client;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import com.sun.istack.NotNull;

import io.remedymatch.engine.TaskDTO;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.engine.domain.MessageKey;
import io.remedymatch.engine.domain.ProzessInstanzId;
import io.remedymatch.engine.domain.ProzessKey;
import io.remedymatch.engine.request.MessageKorrelierenRequest;
import io.remedymatch.engine.request.ProzessStartRequest;
import io.remedymatch.engine.request.TaskAbschliessenRequest;
import io.remedymatch.properties.EngineProperties;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
@Validated
public class EngineClient {

	private final EngineProperties properties;

	public ProzessInstanzId prozessStarten(//
			final @NotNull @Valid ProzessKey prozessKey, //
			final @NotNull @Valid BusinessKey businessKey, //
			final @NotNull @Valid Map<String, Object> variables) {

		val request = ProzessStartRequest.builder().prozessKey(prozessKey.getValue()).variables(variables)
				.businessKey(businessKey.getValue().toString()).build();

		val restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(properties.getRemedyRestApiUrl() + "/prozess/start/",
				request, String.class);

		if (response.getStatusCode().isError()) {
			throw new RuntimeException("Beim Starten des Prozesses ist etwas fehlgeschlagen");
		}

		return new ProzessInstanzId(response.getBody());
	}

	public TaskDTO ladeTask(String taskId, String institutionId) {
		val restTemplate = new RestTemplate();
		ResponseEntity<TaskDTO> taskResponse = restTemplate.getForEntity(
				properties.getRemedyRestApiUrl() + "/task/" + institutionId + "/" + taskId, TaskDTO.class);

		if (taskResponse.getStatusCode().isError()) {
			throw new RuntimeException("Beim laden des Task ist etwas fehlgeschlagen");
		}

		val task = taskResponse.getBody();
		return task;
	}

	public List<TaskDTO> ladeAlleTask(String institutionId) {
		val restTemplate = new RestTemplate();
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
		val restTemplate = new RestTemplate();
		ResponseEntity<Void> response = restTemplate.postForEntity(properties.getRemedyRestApiUrl() + "/task/" + task,
				request, Void.class);

		if (response.getStatusCode().isError()) {
			throw new RuntimeException("Beim abschliessen ist etwas fehlgeschlagen");
		}
	}

	public void messageKorrelieren(//
			final @NotNull @Valid ProzessKey prozessKey, //
			final @NotNull @Valid MessageKey messageKey, //
			final @NotNull @Valid Map<String, Object> variables) {
		val request = MessageKorrelierenRequest.builder()//
		// FIXME prozessKey
//				.prozessInstanzId(prozessInstanzId) //
				.messageKey(messageKey.getValue()) //
				.variables(variables)//
				.build();

		ResponseEntity<Void> response = new RestTemplate()
				.postForEntity(properties.getRemedyRestApiUrl() + "/message/korrelieren/", request, Void.class);

		if (response.getStatusCode().isError()) {
			throw new RuntimeException("Beim abschliessen ist etwas fehlgeschlagen");
		}
	}

	public void messageKorrelieren(ProzessInstanzId prozessInstanzId, String messageKey,
			Map<String, Object> variables) {

		val request = MessageKorrelierenRequest.builder().prozessInstanzId(prozessInstanzId.getValue())
				.messageKey(messageKey).variables(variables).build();

		val restTemplate = new RestTemplate();
		ResponseEntity<Void> response = restTemplate
				.postForEntity(properties.getRemedyRestApiUrl() + "/message/korrelieren/", request, Void.class);

		if (response.getStatusCode().isError()) {
			throw new RuntimeException("Beim abschliessen ist etwas fehlgeschlagen");
		}
	}

}
