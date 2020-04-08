package io.remedymatch.artikel.controller;

import static io.remedymatch.artikel.controller.ArtikelControllerMapper.mapArtikelToRO;
import static io.remedymatch.artikel.controller.ArtikelControllerMapper.mapKategorienToRO;
import static io.remedymatch.artikel.controller.ArtikelControllerMapper.maptToArtikelId;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import lombok.AllArgsConstructor;
import lombok.val;

/**
 * Artikel REST API
 */
@RestController
@RequestMapping("/artikel")
@Validated
@AllArgsConstructor
public class ArtikelController {

	private final ArtikelSucheService artikelSucheService;

	@Transactional(readOnly = true)
	@GetMapping("/suche")
	public @ResponseBody ResponseEntity<List<ArtikelRO>> sucheArtikel(
			@RequestParam(value = "nameLike", required = false) String nameLike) {
		return ResponseEntity.ok(mapArtikelToRO(artikelSucheService.findByNameLike(nameLike)));
	}

	@Transactional(readOnly = true)
	@GetMapping
	public @ResponseBody ResponseEntity<List<ArtikelRO>> alleArtikelLaden() {
		return ResponseEntity.ok(mapArtikelToRO(artikelSucheService.findAlleArtikel()));
	}

	@Transactional(readOnly = true)
	@GetMapping("/kategorie")
	public @ResponseBody ResponseEntity<List<ArtikelKategorieRO>> alleKategorienLaden() {
		return ResponseEntity.ok(mapKategorienToRO(artikelSucheService.findAlleKategorien()));
	}

	@Transactional(readOnly = true)
	@GetMapping("/{articleId}")
	public @ResponseBody ResponseEntity<ArtikelRO> getArtikel(@PathVariable("articleId") @NotNull UUID articleId) {
		val artikel = artikelSucheService.findArtikel(maptToArtikelId(articleId));
		if (artikel.isPresent()) {
			return ResponseEntity.ok(mapArtikelToRO(artikel.get()));
		}

		return ResponseEntity.badRequest().build();
	}
}
