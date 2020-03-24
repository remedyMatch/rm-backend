package io.remedymatch.angebot.domain;

import io.remedymatch.anfrage.domain.AnfrageRepository;
import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.aufgabe.domain.TaskBeschreibungHandler;
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
            prefix = "Bedarf-Anfrage von ";
            artikel = anfrage.get().getBedarf().getArtikel();
            anzahl = anfrage.get().getBedarf().getAnzahl();
        } else {
            prefix = "Angebot-Anfrage von ";
            artikel = anfrage.get().getAngebot().getArtikel();
            anzahl = anfrage.get().getAngebot().getAnzahl();
        }

        beschreibung += prefix + anfrage.get().getInstitutionVon().getName();
        beschreibung += "Artikel: " + artikel.getName() + " Anzahl: " + anzahl;


        return beschreibung;
    }

    @Override
    public String taskKey() {
        return "anfrage_prozess_beantworten";
    }
}
