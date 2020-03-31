package io.remedymatch.match.domain;

import io.remedymatch.angebot.domain.AngebotAnfrageEntity;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Component
public class MatchService {

    private final MatchRepository matchRepository;

    @Transactional
    public MatchEntity matchAusBedarfErstellen(BedarfAnfrageEntity anfrage) {
        val match = new MatchEntity();

        match.setInstitutionAn(anfrage.getInstitutionAn());
        match.setInstitutionVon(anfrage.getInstitutionVon());
        match.setStandortAn(MatchStandortMapper.mapToMatchStandort(anfrage.getStandortAn()));
        match.setStandortVon(MatchStandortMapper.mapToMatchStandort(anfrage.getStandortVon()));

        match.setStatus(MatchStatus.Offen);
        return matchRepository.save(match);
    }

    @Transactional
    public MatchEntity matchAusAngebotErstellen(AngebotAnfrageEntity anfrage) {
        val match = new MatchEntity();

        match.setInstitutionAn(anfrage.getInstitutionVon());
        match.setInstitutionVon(anfrage.getInstitutionAn());
        match.setStandortAn(MatchStandortMapper.mapToMatchStandort(anfrage.getStandortVon()));
        match.setStandortVon(MatchStandortMapper.mapToMatchStandort(anfrage.getStandortAn()));

        match.setStatus(MatchStatus.Offen);
        return matchRepository.save(match);
    }

    @Transactional
    public List<MatchEntity> beteiligteMatches(InstitutionEntity institution) {
        val matches = matchRepository.findAllByInstitutionAn(institution);
        matches.addAll(matchRepository.findAllByInstitutionVon(institution));
        return matches;
    }

}
