package io.remedymatch.angebot.domain;

import io.remedymatch.anfrage.domain.AnfrageEntity;
import io.remedymatch.anfrage.domain.AnfrageRepository;
import io.remedymatch.anfrage.domain.AnfrageService;
import io.remedymatch.anfrage.domain.AnfrageStatus;
import io.remedymatch.engine.AnfrageProzessConstants;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.institution.domain.InstitutionEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static io.remedymatch.engine.AnfrageProzessConstants.PROZESS_KEY;

@AllArgsConstructor
@Service
public class AngebotService {

    private final AngebotRepository angebotRepository;
    private final AnfrageRepository anfrageRepository;
    private final AnfrageService anfrageService;
    private final EngineClient engineClient;

    @Transactional
    public void angebotMelden(AngebotEntity angebot, InstitutionEntity institutionEntity) {
        angebot.setInstitution(institutionEntity);
        angebot.setRest(angebot.getAnzahl());
        angebotRepository.save(angebot);
    }

    @Transactional
    public Iterable<AngebotEntity> alleAngeboteLaden() {
        return angebotRepository.findAll();
    }

    @Transactional
    public void angebotLoeschen(UUID id) {
        val angebot = angebotRepository.findById(id);

        if (angebot.isEmpty()) {
            throw new IllegalIdentifierException("Angebot ist nicht vorhanden");
        }


        if (angebot.get().getAnfragen() != null) {
            angebot.get().getAnfragen().stream().filter(anfrage -> anfrage.getStatus().equals(AnfrageStatus.Offen)).forEach(anfrage ->
                    anfrageService.anfrageStornieren(anfrage.getId().toString()));
        }


        angebotRepository.delete(angebot.get());
    }

    @Transactional
    public Optional<AngebotEntity> angebotLaden(UUID id) {
        return angebotRepository.findById(id);
    }

    @Transactional
    public void angebotUpdaten(AngebotEntity angebot) {
        val oldAngebot = angebotRepository.findById(angebot.getId()).get();
        oldAngebot.setAnzahl(angebot.getAnzahl());
        oldAngebot.setArtikel(angebot.getArtikel());
        oldAngebot.setMedizinisch(angebot.isMedizinisch());
        oldAngebot.setOriginalverpackt(angebot.isOriginalverpackt());
        oldAngebot.setSteril(angebot.isSteril());
        oldAngebot.setStandort(angebot.getStandort());
        oldAngebot.setHaltbarkeit(angebot.getHaltbarkeit());
        oldAngebot.setKommentar(angebot.getKommentar());
        angebotRepository.save(oldAngebot);
    }

    @Transactional
    public void starteAnfrage(UUID angebotId, InstitutionEntity anfrager, String kommentar, String standort, double anzahl) {

        val angebot = angebotRepository.findById(angebotId);

        if (angebot.isEmpty()) {
            throw new IllegalArgumentException("Angebot ist nicht vorhanden");
        }

        val anfrage = AnfrageEntity.builder()
                .institutionVon(anfrager)
                .institutionAn(angebot.get().getInstitution())
                .kommentar(kommentar)
                .standortAn(angebot.get().getStandort())
                .standortVon(standort)
                .angebot(angebot.get())
                .anzahl(anzahl)
                .status(AnfrageStatus.Offen)
                .build();

        anfrageRepository.save(anfrage);

        val prozessInstanzId = engineClient.prozessStarten(
                PROZESS_KEY,
                AnfrageProzessConstants.PROZESS_TYP_ANGEBOT,
                anfrage.getId().toString(),
                angebot.get().getInstitution().getId().toString());
        anfrage.setProzessInstanzId(prozessInstanzId);
        anfrageRepository.save(anfrage);
    }

    @Transactional
    public void anfrageStornieren(String anfrageId) {
        val anfrage = anfrageRepository.findById(UUID.fromString(anfrageId));
        if (anfrage.isEmpty()) {
            throw new IllegalArgumentException("Anfrage nicht vorhanden");
        }
        anfrage.get().setStatus(AnfrageStatus.Storniert);
        anfrageRepository.save(anfrage.get());
    }

    @Transactional
    public void anfrageAnnehmen(String anfrageId) {
        val anfrage = anfrageRepository.findById(UUID.fromString(anfrageId));
        if (anfrage.isEmpty()) {
            throw new IllegalArgumentException("Anfrage nicht vorhanden");
        }
        anfrage.get().setStatus(AnfrageStatus.Angenommen);

        //Angebot als bedient markieren
        val angebot = anfrage.get().getAngebot();

        //Restbestand des Angebots herabsetzen oder Exception werfen,
        // wenn die Anfrage größer als das Angebot ist
        if (anfrage.get().getAnzahl() > angebot.getAnzahl()) {
            anfrage.get().setStatus(AnfrageStatus.Storniert);
            anfrageRepository.save(anfrage.get());
            throw new IllegalArgumentException("Nicht genügend Ware auf Lager");
        } else {
            if (anfrage.get().getAnzahl() == angebot.getAnzahl()) {
                angebot.setBedient(true);
                angebot.setRest(0);
            } else {
                angebot.setRest(angebot.getRest() - anfrage.get().getAnzahl());
            }
        }

        angebotRepository.save(angebot);
        anfrageRepository.save(anfrage.get());
    }
}
