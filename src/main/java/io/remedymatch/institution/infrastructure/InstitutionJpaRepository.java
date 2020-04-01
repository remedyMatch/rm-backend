package io.remedymatch.institution.infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionJpaRepository extends JpaRepository<InstitutionEntity, UUID> {
	Optional<InstitutionEntity> findByInstitutionKey(final String institutionKey);
}
