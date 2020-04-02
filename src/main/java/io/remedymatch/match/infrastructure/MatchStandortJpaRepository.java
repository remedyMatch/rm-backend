package io.remedymatch.match.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchStandortJpaRepository extends JpaRepository<MatchStandortEntity, UUID> {
	// leer
}
