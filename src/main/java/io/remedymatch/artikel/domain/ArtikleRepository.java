package io.remedymatch.artikel.domain;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.artikel.api.ArtikelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArtikleRepository {
    @Autowired
    private ArtikelJpaRepository jpaRepository;

    public List<ArtikelDTO> search() {
        return jpaRepository.findAll().stream().map(ArtikelMapper::getArticleDTO).collect(Collectors.toList());
    }

    public ArtikelDTO get(UUID articleId) {
        return ArtikelMapper.getArticleDTO(jpaRepository.findById(articleId).orElseThrow());
    }

    public ArtikelDTO add(ArtikelDTO artikel) {
        return ArtikelMapper.getArticleDTO(
                jpaRepository.save(
                        ArtikelMapper.getArticleEntity(artikel)
                )
        );
    }
}
