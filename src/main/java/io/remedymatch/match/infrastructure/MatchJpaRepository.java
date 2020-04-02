package io.remedymatch.match.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.match.domain.MatchStatus;

@Repository
public interface MatchJpaRepository extends CrudRepository<MatchEntity, UUID> {

	List<MatchEntity> findAllByInstitutionVon(InstitutionEntity institutionVon);

	List<MatchEntity> findAllByInstitutionAn(InstitutionEntity institutionAn);

	List<MatchEntity> findAllByStatus(final MatchStatus status);
}
