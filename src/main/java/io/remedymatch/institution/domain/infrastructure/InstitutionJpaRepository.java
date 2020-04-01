package io.remedymatch.institution.domain.infrastructure;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface InstitutionJpaRepository extends CrudRepository<InstitutionEntity, UUID> {
	InstitutionEntity findByInstitutionKey(final String institutionKey);
}
