package io.remedymatch.article.domain;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.remedymatch.article.infrastructure.ArticleJpaRepository;

@Repository
public class ArtikleRepository {
    @Autowired
    private ArticleJpaRepository jpaRepository;

    public List<Artikel> search() {
        return jpaRepository.findAll().stream().map(ArtikelMapper::getArticle).collect(Collectors.toList());
    }

    public Artikel get(UUID articleId) {
        return ArtikelMapper.getArticle(jpaRepository.findById(articleId).orElseThrow());
    }

    public Artikel add(Artikel artikel) {
        return ArtikelMapper.getArticle(
                jpaRepository.save(
                        ArtikelMapper.getArticleEntity(artikel)
                )
        );
    }
}
