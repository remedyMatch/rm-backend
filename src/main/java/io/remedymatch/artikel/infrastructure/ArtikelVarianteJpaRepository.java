package io.remedymatch.artikel.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtikelVarianteJpaRepository extends JpaRepository<ArtikelVarianteEntity, UUID> {
	// leer
}
