package io.remedymatch.angebot.domain.aufgabe;

import io.remedymatch.anfrage.domain.AnfrageRepository;
import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.aufgabe.domain.handler.TaskBeschreibungHandler;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class AnfrageBearbeitenTaskNameHandler implements TaskBeschreibungHandler {

    private final AnfrageRepository anfrageRepository;

    @Override
    public String beschreibung(TaskDTO taskDTO) {

        String beschreibung = "";

        val anfrage = anfrageRepository.findById(UUID.fromString(taskDTO.getObjektId()));

        String prefix;
        ArtikelEntity artikel;
        double anzahl;

        if (anfrage.get().getBedarf() != null) {
            prefix = "Anfrage zu Bedarf von ";
            artikel = anfrage.get().getBedarf().getArtikel();
            anzahl = anfrage.get().getBedarf().getAnzahl();
        } else {
            prefix = "Anfrage zu Angebot von ";
            artikel = anfrage.get().getAngebot().getArtikel();
            anzahl = anfrage.get().getAngebot().getAnzahl();
        }

        beschreibung += anfrage.get().getInstitutionVon().getName() + ": " + prefix;
        beschreibung += (int) anzahl + " " + artikel.getName();

        return beschreibung;
    }

    @Override
    public String taskKey() {
        return AnfrageBearbeitenTaskContstants.TASK_KEY;
    }
}
