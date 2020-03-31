package io.remedymatch.bedarf.domain.aufgabe;

import io.remedymatch.aufgabe.domain.handler.TaskBeschreibungHandler;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageRepository;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class BedarfAnfrageBearbeitenTaskNameHandler implements TaskBeschreibungHandler {

    private final BedarfAnfrageRepository anfrageRepository;

    @Override
    public String beschreibung(TaskDTO taskDTO) {

        String beschreibung = "";

        val anfrage = anfrageRepository.findById(UUID.fromString(taskDTO.getObjektId()));

        var prefix = "Anfrage zu Bedarf von ";
        var artikel = anfrage.get().getBedarf().getArtikel();
        var anzahl = anfrage.get().getBedarf().getAnzahl();

        beschreibung += anfrage.get().getInstitutionVon().getName() + ": " + prefix;
        beschreibung += (int) anzahl + " " + artikel.getName();

        return beschreibung;
    }

    @Override
    public String taskKey() {
        return BedarfAnfrageBearbeitenTaskContstants.TASK_KEY;
    }
}
