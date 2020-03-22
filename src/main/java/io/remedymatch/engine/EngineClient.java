package io.remedymatch.engine;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Component
public class EngineClient {

    final String uri = "http://localhost:8085/rest/task";

    public TaskDTO ladeTask(String taskId, String institutionId) {
        val restTemplate = new RestTemplate();
        ResponseEntity<TaskDTO> taskResponse = restTemplate.getForEntity(uri + "/" + institutionId + "/" + taskId, TaskDTO.class);

        if (taskResponse.getStatusCode().isError()) {
            throw new RuntimeException("Beim laden des Task ist etwas fehlgeschlagen");
        }

        val task = taskResponse.getBody();
        return task;
    }

    public TaskDTO ladeAlleTask(String institutionId) {
        val restTemplate = new RestTemplate();
        ResponseEntity<TaskDTO> taskResponse = restTemplate.getForEntity(uri + "/institution/" + institutionId, TaskDTO.class);

        if (taskResponse.getStatusCode().isError()) {
            throw new RuntimeException("Beim laden der Task ist etwas fehlgeschlagen");
        }

        val task = taskResponse.getBody();
        return task;
    }

    public void taskAbschliessen(String task, boolean angenommen) {

        val request = TaskAbschliessenRequest.builder().angenommen(angenommen).build();
        val restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.postForEntity(uri + "/" + task, request, Void.class);

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Beim abschliessen ist etwas fehlgeschlagen");
        }
    }
}
