package io.remedymatch.institution.domain;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;


    public InstitutionEntity updateInstitution(InstitutionEntity institution) {
        val savedInstitution = institutionRepository.findById(institution.getId()).get();
        savedInstitution.setName(institution.getName());
        savedInstitution.setTyp(institution.getTyp());
        return institutionRepository.save(savedInstitution);
    }

    public InstitutionEntity updateHauptstandort(InstitutionEntity institution, InstitutionStandortEntity standort) {
        institution.setHauptstandort(standort);
        return institutionRepository.save(institution);
    }

    public InstitutionEntity standortHinzufuegen(InstitutionEntity institution, InstitutionStandortEntity standort) {
        institution.getStandorte().add(standort);
        return institutionRepository.save(institution);
    }

    public InstitutionEntity standortEntfernen(InstitutionEntity institution, UUID standortId) {
        var standort = institution.getStandorte().stream().filter(s -> s.getId().equals(standortId)).findFirst();
        if (standort.isEmpty()) {
            throw new IllegalArgumentException("Standort kann nicht gelöscht werden, da dieser nicht vorhanden ist.");
        }
        institution.getStandorte().remove(standort);
        return institutionRepository.save(institution);
    }


}
