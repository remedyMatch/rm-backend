package io.remedymatch.artikel.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtikelJpaRepository extends JpaRepository<ArtikelEntity, UUID> {
	List<ArtikelEntity> findByNameLike(String nameLike);

	List<ArtikelEntity> findAllByArtikelKategorie_Id(UUID id);
}
