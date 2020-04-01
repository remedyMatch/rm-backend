package io.remedymatch.institution.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface InstitutionStandortJpaRepository extends JpaRepository<InstitutionStandortEntity, UUID> {
	// leer
}
