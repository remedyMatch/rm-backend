package io.remedymatch.artikel.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArtikelKategorieRepository extends JpaRepository<ArtikelKategorieEntity, UUID> {
    List<ArtikelKategorieEntity> findByNameLike( String nameLike );
    Optional<ArtikelKategorieEntity> findByName( String name );
}
