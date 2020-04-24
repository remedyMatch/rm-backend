package io.remedymatch.angebot.process;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.service.AngebotService;
import io.remedymatch.aufgabe.domain.handler.TaskCompleteHandler;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
class AngebotAnfrageBearbeitenTaskCompleteHandler implements TaskCompleteHandler {

	private final AngebotService angebotService;

	@Override
	public String taskKey() {
		return AngebotAnfrageBearbeitenTaskContstants.TASK_KEY;
	}

	@Override
	public void taskPruefen(TaskDTO taskDTO, Map<String, Object> variables) {

		if (!Boolean.parseBoolean(variables.get(AngebotAnfrageBearbeitenTaskContstants.ANGENOMMEN).toString())) {
			return;
		}

		angebotService.anfrageAngenommen(new AngebotAnfrageId(UUID.fromString(taskDTO.getObjektId())));
	}
}
