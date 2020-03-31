package io.remedymatch.institution.domain;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionStandortRepository institutionStandortRepository;


    public InstitutionEntity updateInstitution(InstitutionEntity institution) {
        val savedInstitution = institutionRepository.findById(institution.getId()).get();
        savedInstitution.setName(institution.getName());
        savedInstitution.setTyp(institution.getTyp());
        return institutionRepository.save(savedInstitution);
    }

    public InstitutionEntity updateHauptstandort(InstitutionEntity institution, InstitutionStandortEntity standort) {

        //TODO longitude / latitude berechnen
        standort = institutionStandortRepository.save(standort);
        institution.setHauptstandort(standort);
        return institutionRepository.save(institution);
    }

    public InstitutionEntity standortHinzufuegen(InstitutionEntity institution, InstitutionStandortEntity standort) {

        //TODO longitude / latitude berechnen
        standort = institutionStandortRepository.save(standort);
        institution.getStandorte().add(standort);
        return institutionRepository.save(institution);
    }

    public InstitutionEntity standortEntfernen(InstitutionEntity institution, UUID standortId) {
        var standort = institution.getStandorte().stream().filter(s -> s.getId().equals(standortId)).findFirst();
        if (standort.isEmpty()) {
            throw new IllegalArgumentException("Standort kann nicht gelöscht werden, da dieser nicht vorhanden ist.");
        }

        //TODO prüfen ob dieser Standort in Anfrage / Angebot / Bedarf verwendet wird. Wenn ja -> kann er nicht gelöscht werden

        institution.getStandorte().remove(standort.get());
        val inst = institutionRepository.save(institution);
        institutionStandortRepository.delete(standort.get());
        return inst;
    }


}
