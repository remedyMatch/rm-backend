package io.remedymatch.bedarf.domain;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.bedarf.api.BedarfAnfrageProzessConstants;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageEntity;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageRepository;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageStatus;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntityConverter;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Service
public class BedarfService {

    private final BedarfRepository bedarfRepository;
    private final EngineClient engineClient;
    private final BedarfAnfrageRepository bedarfAnfrageRepository;

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
    public void starteAnfrage(UUID bedarfId, InstitutionEntity anfrager, String kommentar, UUID standortId, double anzahl) {

        val bedarf = bedarfRepository.findById(bedarfId);

        InstitutionStandortEntity standort = null;

        if (anfrager.getHauptstandort().getId().equals(standortId)) {
            standort = anfrager.getHauptstandort();
        } else {
            var foundStandort = anfrager.getStandorte().stream().filter(s -> s.getId().equals(standortId)).findFirst();

            if (foundStandort.isPresent()) {
                standort = foundStandort.get();
            }
        }

        if (standort == null) {
            throw new IllegalArgumentException("Der ausgewählte Standort konnte nicht geunden werden");
        }

        if (bedarf.isEmpty()) {
            throw new IllegalArgumentException("Bedarf ist nicht vorhanden");
        }

        val anfrage = BedarfAnfrageEntity.builder()
                .institutionVon(anfrager)
                .institutionAn(bedarf.get().getInstitution())
                .kommentar(kommentar)
                .standortAn(bedarf.get().getStandort())
                .standortVon(standort)
                .bedarf(bedarf.get())
                .anzahl(anzahl)
                .status(BedarfAnfrageStatus.Offen)
                .build();

        bedarfAnfrageRepository.save(anfrage);

        var variables = new HashMap<String, Object>();
        variables.put("institution", bedarf.get().getInstitution().getId().toString());
        variables.put("objektId", anfrage.getId().toString());

        val prozessInstanzId = engineClient.prozessStarten(
                BedarfAnfrageProzessConstants.PROZESS_KEY,
                anfrage.getId().toString(),
                variables);
        anfrage.setProzessInstanzId(prozessInstanzId);
        bedarfAnfrageRepository.save(anfrage);
    }

    @Transactional
    public void anfrageStornieren(String anfrageId) {
        val anfrage = bedarfAnfrageRepository.findById(UUID.fromString(anfrageId));
        if (anfrage.isEmpty()) {
            throw new IllegalArgumentException("Anfrage nicht vorhanden");
        }
        anfrage.get().setStatus(BedarfAnfrageStatus.Storniert);
        bedarfAnfrageRepository.save(anfrage.get());
    }

    @Transactional
    public void anfrageAnnehmen(String anfrageId) {
        val anfrage = bedarfAnfrageRepository.findById(UUID.fromString(anfrageId));
        if (anfrage.isEmpty()) {
            throw new IllegalArgumentException("Anfrage nicht vorhanden");
        }
        anfrage.get().setStatus(BedarfAnfrageStatus.Angenommen);

        //Bedarf als bedient markieren
        val bedarf = anfrage.get().getBedarf();

        if (anfrage.get().getAnzahl() >= bedarf.getRest()) {
            bedarf.setBedient(true);
            bedarf.setRest(0);
        } else {
            bedarf.setRest(bedarf.getRest() - anfrage.get().getAnzahl());
        }

        bedarfRepository.save(bedarf);
        bedarfAnfrageRepository.save(anfrage.get());
    }

}
