package io.remedymatch.article.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.article.domain.Article;
import io.remedymatch.article.domain.ArticleRepository;

/**
 * Artikel REST API
 *
 * @author mmala
 */
@RestController
@RequestMapping("/artikel")
@Validated
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<Article>> sucheArtikeln() {
        return ResponseEntity.ok(articleRepository.search());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Article> getArtikel(
            @NotBlank @PathVariable("articleId") UUID articleId) {
        return ResponseEntity.ok(articleRepository.get(articleId));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Article> addArtikel(@Valid @RequestBody Article article) {
        return ResponseEntity.ok(articleRepository.add(article));
    }
}
