package io.remedymatch.match.domain;

import io.remedymatch.anfrage.domain.AnfrageEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchEntity matcheErstellen(AnfrageEntity anfrage) {
        val match = new MatchEntity();

        if (anfrage.getBedarf() != null) {
            match.setInstitutionAn(anfrage.getInstitutionAn());
            match.setInstitutionVon(anfrage.getInstitutionVon());
            match.setStandortAn(anfrage.getStandortAn());
            match.setStandortVon(anfrage.getStandortVon());
        } else {
            match.setInstitutionAn(anfrage.getInstitutionVon());
            match.setInstitutionVon(anfrage.getInstitutionAn());
            match.setStandortAn(anfrage.getStandortVon());
            match.setStandortVon(anfrage.getStandortAn());
        }

        match.setStatus(MatchStatus.Offen);
        return matchRepository.save(match);
    }

}
