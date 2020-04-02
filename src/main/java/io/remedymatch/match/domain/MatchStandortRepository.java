package io.remedymatch.match.domain;

import static io.remedymatch.match.domain.MatchStandortEntityConverter.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import io.remedymatch.match.infrastructure.MatchStandortJpaRepository;

@Repository
public class MatchStandortRepository {
	@Autowired
	private MatchStandortJpaRepository jpaRepository;

	public MatchStandort add(final MatchStandort matchStandort) {
		Assert.notNull(matchStandort, "MatchStandort ist null");

		return convert(jpaRepository.save(convert(matchStandort)));
	}
}
