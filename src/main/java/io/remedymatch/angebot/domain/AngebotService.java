package io.remedymatch.angebot.domain;

import io.remedymatch.institution.domain.InstitutionEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class AngebotService {

    private final AngebotRepository angebotRepository;

    public void angebotMelden(AngebotEntity angebot, InstitutionEntity institutionEntity) {
        //TODO Prozess starten?
        angebot.setInstitution(institutionEntity);
        angebotRepository.save(angebot);
    }

    public Iterable<AngebotEntity> alleAngeboteLaden() {
        return angebotRepository.findAll();
    }

    public void angebotLoeschen(UUID id) {
        val angebot = angebotRepository.findById(id);
        angebotRepository.delete(angebot.get());
    }

}
