package io.remedymatch.artikel.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ArtikelJpaRepository extends JpaRepository<ArtikelEntity, UUID> {
    List<ArtikelEntity> findByNameLike( String nameLike );
}
