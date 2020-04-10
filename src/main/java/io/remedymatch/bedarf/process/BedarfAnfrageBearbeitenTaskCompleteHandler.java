package io.remedymatch.bedarf.process;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import io.remedymatch.aufgabe.domain.handler.TaskCompleteHandler;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.service.BedarfService;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
class BedarfAnfrageBearbeitenTaskCompleteHandler implements TaskCompleteHandler {

	private final BedarfService bedarfService;

	@Override
	public String taskKey() {
		return BedarfAnfrageBearbeitenTaskContstants.TASK_KEY;
	}

	@Override
	public void taskPruefen(TaskDTO taskDTO, Map<String, Object> variables) {

		if (!Boolean.parseBoolean(variables.get(BedarfAnfrageBearbeitenTaskContstants.ANGENOMMEN).toString())) {
			return;
		}

		bedarfService.anfrageAnnehmen(new BedarfAnfrageId(UUID.fromString(taskDTO.getObjektId())));
	}
}
