package io.remedymatch.artikel.api;

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

import io.remedymatch.artikel.domain.ArtikleRepository;

/**
 * Artikel REST API
 *
 * @author mmala
 */
@RestController
@RequestMapping("/artikel")
@Validated
public class ArtikelController {
    @Autowired
    private ArtikleRepository artikleRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<ArtikelDTO>> sucheArtikeln() {
        return ResponseEntity.ok(artikleRepository.search());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<ArtikelDTO> getArtikel(
            @NotBlank @PathVariable("articleId") UUID articleId) {
        return ResponseEntity.ok(artikleRepository.get(articleId));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<ArtikelDTO> addArtikel(@Valid @RequestBody ArtikelDTO artikel) {
        return ResponseEntity.ok(artikleRepository.add(artikel));
    }
}
