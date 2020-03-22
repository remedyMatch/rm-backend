package io.remedymatch.artikel.api;

import io.remedymatch.artikel.domain.ArtikelKategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/artikelkategorie")
public class ArtikelKategorieController {
    private final ArtikelKategorieRepository artikelKategorieRepository;

    @GetMapping
    @Transactional(readOnly = true)
    ResponseEntity<List<ArtikelKategorieDTO>> getAll() {
        return ResponseEntity.ok(
                artikelKategorieRepository.findAll().stream().map(ArtikelKategorieMapper::getArtikelKategorieDTO).collect(Collectors.toList())
        );
    }

    @GetMapping("/suche")
    @Transactional(readOnly = true)
    ResponseEntity<List<ArtikelKategorieDTO>> search(@RequestParam(name = "nameLike") String name) {
        return ResponseEntity.ok(
                artikelKategorieRepository.findByNameLike("%" + name + "%").stream().map(ArtikelKategorieMapper::getArtikelKategorieDTO).collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    ResponseEntity<ArtikelKategorieDTO> getById(@PathVariable("id") UUID uuid) {
        return ResponseEntity.ok(
                ArtikelKategorieMapper.getArtikelKategorieDTO(artikelKategorieRepository.findById(uuid).orElseThrow())
        );
    }

    @GetMapping("/{id}/artikel")
    @Transactional(readOnly = true)
    ResponseEntity<List<ArtikelDTO>> getArtikelByKategorie(@PathVariable("id") UUID kategorieId) {
        var kategorie = artikelKategorieRepository.findById(kategorieId).orElseThrow();
        return ResponseEntity.ok(
                kategorie.getArtikel().stream().map(ArtikelMapper::getArticleDTO).collect(Collectors.toList())
        );
    }

    @RequestMapping(method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @Transactional
    ResponseEntity<ArtikelKategorieDTO> addArtikelKategorie(@RequestBody ArtikelKategorieDTO toAdd) {
        if (artikelKategorieRepository.findByName(toAdd.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(
                ArtikelKategorieMapper.getArtikelKategorieDTO(
                        artikelKategorieRepository.save(ArtikelKategorieMapper.getArtikelKategorieEntity(toAdd))
                ));
    }
}
