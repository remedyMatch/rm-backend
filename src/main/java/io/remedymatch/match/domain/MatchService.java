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

        val match = MatchEntity.builder()
                .adresseVon(anfrage.getInstitutionVon().getStandort())
                .adresseZu(anfrage.getInstitutionAn().getStandort())
                .institutionAn(anfrage.getInstitutionAn())
                .institutionVon(anfrage.getInstitutionVon())
                .build();
        matchRepository.save(match);

    }

}
