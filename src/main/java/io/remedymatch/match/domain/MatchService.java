package io.remedymatch.match.domain;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.angebot.domain.AngebotAnfrage;
import io.remedymatch.bedarf.domain.BedarfAnfrage;
import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchStandortRepository matchStandortRepository;

    @Transactional
    public MatchEntity matchAusBedarfErstellen(BedarfAnfrage bedarfAnfrage) {
        val match = new MatchEntity();

        match.setInstitutionAn(InstitutionEntityConverter.convert(bedarfAnfrage.getInstitutionVon()));
        match.setInstitutionVon(InstitutionEntityConverter.convert(bedarfAnfrage.getBedarf().getInstitution()));
        match.setStandortAn(MatchStandortMapper.mapToMatchStandort(bedarfAnfrage.getStandortVon()));
        match.setStandortVon(MatchStandortMapper.mapToMatchStandort(bedarfAnfrage.getBedarf().getStandort()));

        matchStandortRepository.save(match.getStandortVon());
        matchStandortRepository.save(match.getStandortAn());

        match.setStatus(MatchStatus.Offen);
        return matchRepository.save(match);
    }

    @Transactional
    public MatchEntity matchAusAngebotErstellen(AngebotAnfrage angebotAnfrage) {
        val match = new MatchEntity();

        match.setInstitutionAn(InstitutionEntityConverter.convert(angebotAnfrage.getInstitutionVon()));
        match.setInstitutionVon(InstitutionEntityConverter.convert(angebotAnfrage.getAngebot().getInstitution()));
        match.setStandortAn(MatchStandortMapper.mapToMatchStandort(angebotAnfrage.getStandortVon()));
        match.setStandortVon(MatchStandortMapper.mapToMatchStandort(angebotAnfrage.getAngebot().getStandort()));

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
