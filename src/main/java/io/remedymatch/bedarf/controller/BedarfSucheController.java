package io.remedymatch.bedarf.controller;

import static io.remedymatch.bedarf.controller.BedarfControllerMapper.mapToBedarfeRO;
import static io.remedymatch.bedarf.controller.BedarfControllerMapper.mapToFilterEntriesRO;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelKategorieId;
import io.remedymatch.bedarf.domain.service.BedarfSucheService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/bedarf/suche")
@Validated
@Transactional
class BedarfSucheController {

	private final BedarfSucheService bedarfSucheService;

	@Transactional(readOnly = true)
	@GetMapping
	public ResponseEntity<List<BedarfRO>> getAlleNichtBedienteBedarfe() {
		return ResponseEntity.ok(mapToBedarfeRO(bedarfSucheService.findAlleNichtBedienteBedarfe()));
	}

	@Transactional(readOnly = true)
	@GetMapping("/filter/artikelkategorie")
	public ResponseEntity<List<BedarfFilterEntryRO>> getArtikelKategorieFilter() {
		return ResponseEntity.ok(mapToFilterEntriesRO(bedarfSucheService.getArtikelKategorieFilter()));
	}

	@Transactional(readOnly = true)
	@GetMapping("/filter/artikel")
	public ResponseEntity<List<BedarfFilterEntryRO>> getArtikelFilter(//
			@QueryParam("artikelKategorieId") @NotNull ArtikelKategorieId artikelKategorieId) {
		return ResponseEntity.ok(mapToFilterEntriesRO(bedarfSucheService.getArtikelFilter(artikelKategorieId)));
	}

	@Transactional(readOnly = true)
	@GetMapping("/filter/artikelvariante")
	public ResponseEntity<List<BedarfFilterEntryRO>> getArtikelVarianteFilter(//
			@QueryParam("artikelId") @NotNull ArtikelId artikelId) {
		return ResponseEntity.ok(mapToFilterEntriesRO(bedarfSucheService.getArtikelVarianteFilter(artikelId)));
	}
}
