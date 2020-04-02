package io.remedymatch.match.domain.aufgabe;

import java.util.UUID;

import org.springframework.stereotype.Component;

import io.remedymatch.aufgabe.domain.handler.TaskBeschreibungHandler;
import io.remedymatch.engine.TaskDTO;
import io.remedymatch.match.domain.MatchId;
import io.remedymatch.match.domain.MatchRepository;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
public class WareneingangBestaetigenTaskNameHandler implements TaskBeschreibungHandler {

    private final MatchRepository matchRepository;

    @Override
    public String beschreibung(TaskDTO taskDTO) {
        val match = matchRepository.get(new MatchId(UUID.fromString(taskDTO.getObjektId())));
        return " Wareneingang für Match zwischen " + match.get().getInstitutionVon().getName() + " und " + match.get().getInstitutionAn().getName()+" bestätigen.";
    }

    @Override
    public String taskKey() {
        return WareneingangBestaetigenTaskContstants.TASK_KEY;
    }
}
