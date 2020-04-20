package io.remedymatch.institution.infrastructure;

import java.util.UUID;

import io.remedymatch.shared.infrastructure.ReadOnlyRepository;

public interface InstitutionStandortJpaRepository extends ReadOnlyRepository<InstitutionStandortEntity, UUID> {

	InstitutionStandortEntity save(final InstitutionStandortEntity entity);
}
