package io.remedymatch.match.infrastructure;

import io.remedymatch.match.domain.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchJpaRepository extends JpaRepository<MatchEntity, UUID> {

	List<MatchEntity> findAllByInstitutionVon_Id(UUID id);

	List<MatchEntity> findAllByInstitutionAn_Id(UUID id);

	List<MatchEntity> findAllByStatus(final MatchStatus status);
}
