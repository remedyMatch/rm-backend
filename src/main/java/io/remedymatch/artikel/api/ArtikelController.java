package io.remedymatch.artikel.api;

import static io.remedymatch.artikel.api.ArtikelMapper.maptToArtikelId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.artikel.domain.ArtikelRepository;
import lombok.AllArgsConstructor;

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
	public @ResponseBody ResponseEntity<List<ArtikelDTO>> sucheArtikeln(
			@RequestParam(value = "nameLike", required = false) String nameLike) {
		return ResponseEntity.ok(artikelRepository.findByNameLike(nameLike).stream().map(ArtikelMapper::getArtikelDTO)
				.collect(Collectors.toList()));
	}

	@Transactional(readOnly = true)
	@GetMapping
	public @ResponseBody ResponseEntity<List<ArtikelDTO>> alleArtikelLaden() {
		return ResponseEntity.ok(artikelRepository.getAlle().stream().map(ArtikelMapper::getArtikelDTO)
				.collect(Collectors.toList()));
	}

	@Transactional(readOnly = true)
	@GetMapping("/{articleId}")
	public @ResponseBody ResponseEntity<ArtikelDTO> getArtikel(@PathVariable("articleId") UUID articleId) {
		return ResponseEntity.ok(artikelRepository.get(maptToArtikelId(articleId)).map(ArtikelMapper::getArtikelDTO).orElseThrow());
	}

	@Transactional
	@PostMapping
	public @ResponseBody ResponseEntity<ArtikelDTO> addArtikel(@Valid @RequestBody ArtikelDTO artikel) {
		return ResponseEntity.ok(ArtikelMapper.getArtikelDTO(artikelRepository.add(ArtikelMapper.getArtikel(artikel))));
	}
}
