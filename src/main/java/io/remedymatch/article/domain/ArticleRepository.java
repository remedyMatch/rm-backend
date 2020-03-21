package io.remedymatch.article.domain;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.remedymatch.article.infrastructure.ArticleJpaRepository;

@Repository
public class ArticleRepository {
    @Autowired
    private ArticleJpaRepository jpaRepository;

    public List<Article> search() {
        return jpaRepository.findAll().stream().map(ArticleMapper::getArticle).collect(Collectors.toList());
    }

    public Article get(UUID articleId) {
        return ArticleMapper.getArticle(jpaRepository.findById(articleId).orElseThrow());
    }

    public Article add(Article article) {
        return ArticleMapper.getArticle(
                jpaRepository.save(
                        ArticleMapper.getArticleEntity(article)
                )
        );
    }
}
