package io.remedymatch.angebot.domain;

import io.remedymatch.institution.domain.InstitutionEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AngebotService {

    private final AngebotRepository angebotRepository;

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
}
