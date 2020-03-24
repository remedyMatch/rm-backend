package io.remedymatch.match.domain;

import io.remedymatch.anfrage.domain.AnfrageEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MatchService {

    private final MatchRepository matchRepository;

    public void matcheErstellen(AnfrageEntity anfrage) {
        val match = new MatchEntity();
//
//        if (anfrage.getBedarf() != null) {
//            match.setInstitutionAn(anfrage.getInstitutionAn());
//            match.setInstitutionVon(anfrage.getInstitutionVon());
//            match.set
//        } else {
//            match.setInstitutionAn(anfrage.getInstitutionVon());
//            match.setInstitutionVon(anfrage.getInstitutionAn());
//        }


        matchRepository.save(match);

    }

}
