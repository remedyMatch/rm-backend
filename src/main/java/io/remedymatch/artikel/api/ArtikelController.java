package io.remedymatch.artikel.api;

import io.remedymatch.artikel.domain.ArtikelRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.remedymatch.artikel.api.ArtikelMapper.maptToArtikelId;

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

    private final ArtikelRepository artikelRepository;

    @Transactional(readOnly = true)
    @GetMapping("/suche")
    public @ResponseBody
    ResponseEntity<List<ArtikelDTO>> sucheArtikel(
            @RequestParam(value = "nameLike", required = false) String nameLike) {
        return ResponseEntity.ok(artikelRepository.findByNameLike(nameLike).stream().map(ArtikelMapper::getArtikelDTO)
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    @GetMapping
    public @ResponseBody
    ResponseEntity<List<ArtikelDTO>> alleArtikelLaden() {
        return ResponseEntity.ok(artikelRepository.getAlle().stream().map(ArtikelMapper::getArtikelDTO)
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    @GetMapping("/{articleId}")
    public @ResponseBody
    ResponseEntity<ArtikelDTO> getArtikel(@PathVariable("articleId") UUID articleId) {
        return ResponseEntity.ok(artikelRepository.get(maptToArtikelId(articleId)).map(ArtikelMapper::getArtikelDTO).orElseThrow());
    }

    @Transactional
    @PostMapping
    public @ResponseBody
    ResponseEntity<ArtikelDTO> addArtikel(@Valid @RequestBody ArtikelDTO artikel) {
        return ResponseEntity.ok(ArtikelMapper.getArtikelDTO(artikelRepository.add(ArtikelMapper.getArtikel(artikel))));
    }
}
