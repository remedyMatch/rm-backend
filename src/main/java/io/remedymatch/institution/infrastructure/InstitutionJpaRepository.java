package io.remedymatch.institution.infrastructure;

import java.util.UUID;

import io.remedymatch.shared.infrastructure.ReadOnlyRepository;

public interface InstitutionJpaRepository extends ReadOnlyRepository<InstitutionEntity, UUID> {

	InstitutionEntity save(final InstitutionEntity entity);
}
