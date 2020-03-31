package io.remedymatch.bedarf.domain;

import io.remedymatch.anfrage.domain.AnfrageEntity;
import io.remedymatch.anfrage.domain.AnfrageRepository;
import io.remedymatch.anfrage.domain.AnfrageStatus;
import io.remedymatch.engine.AnfrageProzessConstants;
import io.remedymatch.engine.client.EngineClient;
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
public class BedarfService {

    private final BedarfRepository bedarfRepository;
    private final EngineClient engineClient;
    private final AnfrageRepository anfrageRepository;

    @Transactional
    public void bedarfMelden(BedarfEntity bedarf, InstitutionEntity institutionEntity) {
        bedarf.setInstitution(institutionEntity);
        bedarf.setRest(bedarf.getAnzahl());
        bedarfRepository.save(bedarf);
    }

    @Transactional
    public Optional<BedarfEntity> bedarfLaden(String bedarfId) {
        return bedarfRepository.findById(UUID.fromString(bedarfId));
    }

    @Transactional
    public Iterable<BedarfEntity> alleBedarfeLaden() {
        return bedarfRepository.findAll();
    }

    @Transactional
    public void bedarfLoeschen(UUID id) {
        val bedarf = bedarfRepository.findById(id);
        bedarfRepository.delete(bedarf.get());
    }

    @Transactional
    public void starteAnfrage(UUID bedarfId, InstitutionEntity anfrager, String kommentar, String standort, double anzahl) {

        val bedarf = bedarfRepository.findById(bedarfId);

        if (bedarf.isEmpty()) {
            throw new IllegalArgumentException("Bedarf ist nicht vorhanden");
        }

        val anfrage = AnfrageEntity.builder()
                .institutionVon(anfrager)
                .institutionAn(bedarf.get().getInstitution())
                .kommentar(kommentar)
                .standortAn(bedarf.get().getStandort())
                .standortVon(standort)
                .bedarf(bedarf.get())
                .anzahl(anzahl)
                .status(AnfrageStatus.Offen)
                .build();
        anfrageRepository.save(anfrage);

        val prozessInstanzId = engineClient.prozessStarten(
                PROZESS_KEY,
                AnfrageProzessConstants.PROZESS_TYP_BEDARF,
                anfrage.getId().toString(),
                bedarf.get().getInstitution().getId().toString());
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


        //Bedarf als bedient markieren
        val bedarf = anfrage.get().getBedarf();

        if (anfrage.get().getAnzahl() > bedarf.getAnzahl()) {
            bedarf.setBedient(true);
            bedarf.setRest(0);
        } else {
            if (anfrage.get().getAnzahl() == bedarf.getAnzahl()) {
                bedarf.setBedient(true);
                bedarf.setRest(0);
            } else {
                bedarf.setRest(bedarf.getRest() - anfrage.get().getAnzahl());
            }
        }

        bedarfRepository.save(bedarf);
        anfrageRepository.save(anfrage.get());
    }

}
