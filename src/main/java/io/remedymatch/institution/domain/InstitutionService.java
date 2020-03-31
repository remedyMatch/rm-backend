package io.remedymatch.institution.domain;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;


    public InstitutionEntity updateInstitution(InstitutionEntity institution) {

        val savedInstitution = institutionRepository.findById(institution.getId()).get();
        savedInstitution.setName(institution.getName());
        savedInstitution.setHauptstandort(institution.getHauptstandort());
        savedInstitution.setStandorte(institution.getStandorte());
        savedInstitution.setTyp(institution.getTyp());

        return institutionRepository.save(savedInstitution);
    }

}
