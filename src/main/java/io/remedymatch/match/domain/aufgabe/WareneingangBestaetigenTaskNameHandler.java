package io.remedymatch.match.domain.aufgabe;

import io.remedymatch.aufgabe.domain.handler.TaskBeschreibungHandler;
import io.remedymatch.engine.TaskDTO;
import io.remedymatch.match.domain.MatchRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class WareneingangBestaetigenTaskNameHandler implements TaskBeschreibungHandler {

    private final MatchRepository matchRepository;

    @Override
    public String beschreibung(TaskDTO taskDTO) {
        val match = matchRepository.findById(UUID.fromString(taskDTO.getObjektId()));
        return "Match zwischen " + match.get().getInstitutionVon().getName() + " und " + match.get().getInstitutionAn().getName();
    }

    @Override
    public String taskKey() {
        return WareneingangBestaetigenTaskContstants.TASK_KEY;
    }
}
