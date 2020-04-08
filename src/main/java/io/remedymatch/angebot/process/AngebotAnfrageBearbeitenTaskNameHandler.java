package io.remedymatch.angebot.process;

import java.util.UUID;

import org.springframework.stereotype.Component;

import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.service.AngebotAnfrageRepository;
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
        var artikelVariante = anfrage.get().getAngebot().getArtikelVariante();
        var anzahl = anfrage.get().getAngebot().getAnzahl();

        beschreibung += anfrage.get().getInstitutionVon().getName() + ": " + prefix;
        beschreibung += (int) anzahl.intValue() + " " + artikelVariante.getVariante();

        return beschreibung;
    }

    @Override
    public String taskKey() {
        return AnfrageBearbeitenTaskContstants.TASK_KEY;
    }
}
