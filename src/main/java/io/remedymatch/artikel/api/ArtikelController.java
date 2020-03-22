package io.remedymatch.artikel.api;

import io.remedymatch.artikel.domain.ArtikleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

/**
 * Artikel REST API
 *
 * @author mmala
 */
@RestController
@RequestMapping("/artikel")
@Validated
@AllArgsConstructor
public class ArtikelController {

    private final ArtikleRepository artikelRepository;

    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET, path = "/suche", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<ArtikelDTO>> sucheArtikeln(@RequestParam(value = "nameLike", required = false) String nameLike) {
        return ResponseEntity.ok(artikelRepository.search(nameLike));
    }

    @Transactional(readOnly = true)
    @GetMapping
    public @ResponseBody
    ResponseEntity<List<ArtikelDTO>> alleArtikelLaden() {
        return ResponseEntity.ok(artikelRepository.search(null));
    }

    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET, path = "/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<ArtikelDTO> getArtikel(
            @PathVariable("articleId") UUID articleId) {
        return ResponseEntity.ok(artikelRepository.get(articleId));
    }

    @Transactional
    @PostMapping
    public @ResponseBody
    ResponseEntity<ArtikelDTO> addArtikel(@Valid @RequestBody ArtikelDTO artikel) {
        return ResponseEntity.ok(artikelRepository.add(artikel));
    }

    @Transactional
    @PutMapping
    public ResponseEntity<ArtikelDTO> updateArtikel(@RequestBody ArtikelDTO artikelDTO) {
        return ResponseEntity.ok(artikelRepository.update(artikelDTO));
    }

}
