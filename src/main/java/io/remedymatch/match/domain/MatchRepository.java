package io.remedymatch.match.domain;

import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.match.infrastructure.MatchJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.remedymatch.match.domain.MatchEntityConverter.convert;

@Repository
@AllArgsConstructor
@Transactional
public class MatchRepository {

    private final MatchJpaRepository jpaRepository;

    public List<Match> getMatchesFuerInstitutionAn(final InstitutionId institutionId) {
        Assert.notNull(institutionId, "InstitutionId ist null");
        Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

        return jpaRepository.findAllByInstitutionAn_Id(institutionId.getValue()).stream()//
                .map(MatchEntityConverter::convert)//
                .collect(Collectors.toList());
    }

    public List<Match> getMatchesFuerInstitutionVon(final InstitutionId institutionId) {
        Assert.notNull(institutionId, "InstitutionId ist null");
        Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

        return jpaRepository.findAllByInstitutionVon_Id(institutionId.getValue()).stream()//
                .map(MatchEntityConverter::convert)//
                .collect(Collectors.toList());
    }


    public Optional<Match> get(final MatchId matchId) {
        Assert.notNull(matchId, "MatchId ist null");
        Assert.notNull(matchId.getValue(), "MatchId ist null");

        return jpaRepository.findById(matchId.getValue()).map(MatchEntityConverter::convert);
    }

    public Match add(final Match match) {
        Assert.notNull(match, "Match ist null");

        return convert(jpaRepository.save(convert(match)));
    }
}
