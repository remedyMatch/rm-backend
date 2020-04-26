package io.remedymatch.angebot.controller;

import static io.remedymatch.angebot.controller.AngebotControllerMapper.mapToAngeboteRO;
import static io.remedymatch.angebot.controller.AngebotControllerMapper.mapToFilterEntriesRO;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.angebot.domain.service.AngebotSucheService;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelKategorieId;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/angebot/suche")
@Validated
@Transactional
public class AngebotSucheController {

	private final AngebotSucheService angebotSucheService;

	@Transactional(readOnly = true)
	@GetMapping
	public ResponseEntity<List<AngebotRO>> getAlleNichtBedienteAngebote() {
		return ResponseEntity.ok(mapToAngeboteRO(angebotSucheService.findAlleNichtBedienteAngebote()));
	}

	@Transactional(readOnly = true)
	@GetMapping("/filter/artikelkategorie")
	public ResponseEntity<List<AngebotFilterEntryRO>> getArtikelKategorieFilter() {
		return ResponseEntity.ok(mapToFilterEntriesRO(angebotSucheService.getArtikelKategorieFilter()));
	}

	@Transactional(readOnly = true)
	@GetMapping("/filter/artikel")
	public ResponseEntity<List<AngebotFilterEntryRO>> getArtikelFilter(//
			@QueryParam("artikelKategorieId") @NotNull ArtikelKategorieId artikelKategorieId) {
		return ResponseEntity.ok(mapToFilterEntriesRO(angebotSucheService.getArtikelFilter(artikelKategorieId)));
	}

	@Transactional(readOnly = true)
	@GetMapping("/filter/artikelvariante")
	public ResponseEntity<List<AngebotFilterEntryRO>> getArtikelVarianteFilter(//
			@QueryParam("artikelId") @NotNull ArtikelId artikelId) {
		return ResponseEntity.ok(mapToFilterEntriesRO(angebotSucheService.getArtikelVarianteFilter(artikelId)));
	}
}
