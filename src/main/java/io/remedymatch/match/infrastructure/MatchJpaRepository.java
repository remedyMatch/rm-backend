package io.remedymatch.match.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.remedymatch.match.domain.MatchStatus;

@Repository
public interface MatchJpaRepository extends CrudRepository<MatchEntity, UUID> {

	List<MatchEntity> findAllByInstitutionVon_Id(UUID id);

	List<MatchEntity> findAllByInstitutionAn_Id(UUID id);

	List<MatchEntity> findAllByStatus(final MatchStatus status);
}
