package io.remedymatch.institution.infrastructure;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;


public interface InstitutionStandortJpaRepository extends CrudRepository<InstitutionStandortEntity, UUID> {
	// leer
}
