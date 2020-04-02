package io.remedymatch.artikel.api;

import static io.remedymatch.artikel.api.ArtikelKategorieMapper.maptToArtikelKategorieId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.artikel.domain.ArtikelKategorieRepository;
import io.remedymatch.artikel.domain.ArtikelRepository;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("unused")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/artikelkategorie")
public class ArtikelKategorieController {
	private final ArtikelKategorieRepository artikelKategorieRepository;
	private final ArtikelRepository artikelRepository;

	@GetMapping
	@Transactional(readOnly = true)
	ResponseEntity<List<ArtikelKategorieDTO>> getAlle() {
		return ResponseEntity.ok(artikelKategorieRepository.getAlle().stream()
				.map(ArtikelKategorieMapper::getArtikelKategorieDTO).collect(Collectors.toList()));
	}

	@GetMapping("/suche")
	@Transactional(readOnly = true)
	ResponseEntity<List<ArtikelKategorieDTO>> search(@RequestParam(name = "nameLike") String name) {
		return ResponseEntity.ok(artikelKategorieRepository.findByNameLike("%" + name + "%").stream()
				.map(ArtikelKategorieMapper::getArtikelKategorieDTO).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	@Transactional(readOnly = true)
	ResponseEntity<ArtikelKategorieDTO> getById(@PathVariable("id") UUID kategorieId) {
		return ResponseEntity.ok(ArtikelKategorieMapper.getArtikelKategorieDTO(
				artikelKategorieRepository.get(maptToArtikelKategorieId(kategorieId)).orElseThrow()));
	}

	@GetMapping("/{id}/artikel")
	@Transactional(readOnly = true)
	ResponseEntity<List<ArtikelDTO>> getArtikelByKategorie(@PathVariable("id") UUID kategorieId) {
		var kategorie = artikelKategorieRepository.get(maptToArtikelKategorieId(kategorieId)).orElseThrow();

		return ResponseEntity.ok(artikelRepository.getArtikelVonKategorie(maptToArtikelKategorieId(kategorieId))
				.stream().map(ArtikelMapper::getArtikelDTO).collect(Collectors.toList()));
	}

	// @RequestMapping(method = RequestMethod.POST,consumes =
	// "application/json",produces = "application/json")
	@PostMapping
	@Transactional
	ResponseEntity<ArtikelKategorieDTO> addArtikelKategorie(@RequestBody ArtikelKategorieDTO toAdd) {
		if (artikelKategorieRepository.findByName(toAdd.getName()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return ResponseEntity.ok(ArtikelKategorieMapper.getArtikelKategorieDTO(
				artikelKategorieRepository.add(ArtikelKategorieMapper.getArtikelKategorie(toAdd))));
	}
}
