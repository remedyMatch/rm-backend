package io.remedymatch.match.domain;

import io.remedymatch.match.infrastructure.MatchStandortJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static io.remedymatch.match.domain.MatchStandortEntityConverter.convert;

@Repository
@AllArgsConstructor
@Transactional
public class MatchStandortRepository {

    private final MatchStandortJpaRepository jpaRepository;

    public MatchStandort add(final MatchStandort matchStandort) {
        Assert.notNull(matchStandort, "MatchStandort ist null");

        return convert(jpaRepository.save(convert(matchStandort)));
    }
}
