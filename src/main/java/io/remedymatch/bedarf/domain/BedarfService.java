package io.remedymatch.bedarf.domain;

import io.remedymatch.institution.domain.InstitutionEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class BedarfService {

    private final BedarfRepository bedarfRepository;

    public void bedarfMelden(BedarfEntity bedarf, InstitutionEntity institutionEntity) {
        //TODO Prozess starten?
        bedarf.setInstitution(institutionEntity);
        bedarfRepository.save(bedarf);
    }

    public Iterable<BedarfEntity> alleBedarfeLaden() {
        return bedarfRepository.findAll();
    }

    public void bedarfLoeschen(UUID id) {
        val bedarf = bedarfRepository.findById(id);
        bedarfRepository.delete(bedarf.get());
    }

    public void bedarfUpdaten(BedarfEntity bedarf) {
        val oldBedarf = bedarfRepository.findById(bedarf.getId()).get();
        oldBedarf.setAnzahl(bedarf.getAnzahl());
        oldBedarf.setArtikel(bedarf.getArtikel());
        bedarfRepository.save(oldBedarf);
    }
}
