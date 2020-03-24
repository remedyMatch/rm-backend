package io.remedymatch.angebot.domain;

import io.remedymatch.anfrage.domain.AnfrageEntity;
import io.remedymatch.anfrage.domain.AnfrageRepository;
import io.remedymatch.engine.AnfrageProzessConstants;
import io.remedymatch.engine.EngineClient;
import io.remedymatch.institution.domain.InstitutionEntity;
import lombok.AllArgsConstructor;
import lombok.val;
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
    private final EngineClient engineClient;

    @Transactional
    public void angebotMelden(AngebotEntity angebot, InstitutionEntity institutionEntity) {
        //TODO Prozess starten?
        angebot.setInstitution(institutionEntity);
        angebotRepository.save(angebot);
    }

    @Transactional
    public Iterable<AngebotEntity> alleAngeboteLaden() {
        return angebotRepository.findAll();
    }

    @Transactional
    public void angebotLoeschen(UUID id) {
        val angebot = angebotRepository.findById(id);
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
    public void starteAnfrage(UUID bedarfId, InstitutionEntity anfrager, String kommentar) {

        val angebot = angebotRepository.findById(bedarfId);

        if (angebot.isEmpty()) {
            throw new IllegalArgumentException("Angebot ist nicht vorhanden");
        }

        val anfrage = AnfrageEntity.builder()
                .anfrager(anfrager)
                .kommentar(kommentar)
                .angebot(angebot.get())
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
        anfrage.get().setStorniert(true);
        anfrageRepository.save(anfrage.get());
    }

    @Transactional
    public void anfrageAnnehmen(String anfrageId) {
        val anfrage = anfrageRepository.findById(UUID.fromString(anfrageId));
        if (anfrage.isEmpty()) {
            throw new IllegalArgumentException("Anfrage nicht vorhanden");
        }
        anfrage.get().setAngenommen(true);

        //Angebot als bedient markieren
        val angebot = anfrage.get().getAngebot();
        angebot.setBedient(true);
        angebotRepository.save(angebot);

        anfrageRepository.save(anfrage.get());
    }
}
