package io.remedymatch.angebot.domain.aufgabe;

import io.remedymatch.angebot.domain.anfrage.AngebotAnfrageRepository;
import io.remedymatch.aufgabe.domain.handler.TaskBeschreibungHandler;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class AngebotAnfrageBearbeitenTaskNameHandler implements TaskBeschreibungHandler {

    private final AngebotAnfrageRepository anfrageRepository;

    @Override
    public String beschreibung(TaskDTO taskDTO) {

        String beschreibung = "";

        val anfrage = anfrageRepository.get(UUID.fromString(taskDTO.getObjektId()));

        var prefix = "Anfrage zu Angebot von ";
        var artikel = anfrage.get().getAngebot().getArtikel();
        var anzahl = anfrage.get().getAngebot().getAnzahl();

        beschreibung += anfrage.get().getInstitutionVon().getName() + ": " + prefix;
        beschreibung += (int) anzahl + " " + artikel.getName();

        return beschreibung;
    }

    @Override
    public String taskKey() {
        return AnfrageBearbeitenTaskContstants.TASK_KEY;
    }
}
