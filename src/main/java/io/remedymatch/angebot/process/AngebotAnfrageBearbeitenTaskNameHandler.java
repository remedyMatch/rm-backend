package io.remedymatch.angebot.process;

import java.util.UUID;

import org.springframework.stereotype.Component;

import io.remedymatch.angebot.domain.AngebotAnfrageId;
import io.remedymatch.angebot.domain.AngebotAnfrageRepository;
import io.remedymatch.aufgabe.domain.handler.TaskBeschreibungHandler;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
public class AngebotAnfrageBearbeitenTaskNameHandler implements TaskBeschreibungHandler {

    private final AngebotAnfrageRepository anfrageRepository;

    @Override
    public String beschreibung(TaskDTO taskDTO) {

        String beschreibung = "";

        val anfrage = anfrageRepository.get(new AngebotAnfrageId(UUID.fromString(taskDTO.getObjektId())));

        var prefix = "Anfrage zu Angebot von ";
        var artikel = anfrage.get().getAngebot().getArtikel();
        var anzahl = anfrage.get().getAngebot().getAnzahl();

        beschreibung += anfrage.get().getInstitutionVon().getName() + ": " + prefix;
        beschreibung += (int) anzahl.intValue() + " " + artikel.getName();

        return beschreibung;
    }

    @Override
    public String taskKey() {
        return AnfrageBearbeitenTaskContstants.TASK_KEY;
    }
}
