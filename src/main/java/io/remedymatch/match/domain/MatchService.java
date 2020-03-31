package io.remedymatch.match.domain;

import io.remedymatch.angebot.domain.AngebotAnfrage;
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
    private final MatchStandortRepository matchStandortRepository;

    @Transactional
    public MatchEntity matchAusBedarfErstellen(BedarfAnfrageEntity anfrage) {
        val match = new MatchEntity();

        match.setInstitutionAn(anfrage.getInstitutionAn());
        match.setInstitutionVon(anfrage.getInstitutionVon());
        match.setStandortAn(MatchStandortMapper.mapToMatchStandort(anfrage.getStandortAn()));
        match.setStandortVon(MatchStandortMapper.mapToMatchStandort(anfrage.getStandortVon()));

        matchStandortRepository.save(match.getStandortVon());
        matchStandortRepository.save(match.getStandortAn());

        match.setStatus(MatchStatus.Offen);
        return matchRepository.save(match);
    }

    @Transactional
    public MatchEntity matchAusAngebotErstellen(AngebotAnfrage angebotAnfrage) {
        val match = new MatchEntity();

        match.setInstitutionAn(angebotAnfrage.getInstitutionVon());
        match.setInstitutionVon(angebotAnfrage.getInstitutionAn());
        match.setStandortAn(MatchStandortMapper.mapToMatchStandort(angebotAnfrage.getStandortVon()));
        match.setStandortVon(MatchStandortMapper.mapToMatchStandort(angebotAnfrage.getStandortAn()));

        matchStandortRepository.save(match.getStandortVon());
        matchStandortRepository.save(match.getStandortAn());

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
