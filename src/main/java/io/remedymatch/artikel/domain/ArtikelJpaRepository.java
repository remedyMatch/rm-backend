package io.remedymatch.artikel.domain;

import io.remedymatch.artikel.api.ArtikelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtikelJpaRepository extends JpaRepository<ArtikelMapper.ArtikelEntity, UUID>
{

}
