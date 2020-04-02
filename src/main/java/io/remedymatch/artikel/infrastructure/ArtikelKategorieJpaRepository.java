package io.remedymatch.artikel.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtikelKategorieJpaRepository extends JpaRepository<ArtikelKategorieEntity, UUID> {
	List<ArtikelKategorieEntity> findByNameLike(String nameLike);

	Optional<ArtikelKategorieEntity> findByName(String name);
}
