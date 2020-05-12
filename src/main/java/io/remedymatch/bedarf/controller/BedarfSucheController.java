package io.remedymatch.bedarf.controller;

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelKategorieId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.bedarf.domain.service.BedarfSucheService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import java.util.List;

import static io.remedymatch.bedarf.controller.BedarfControllerMapper.mapToBedarfeRO;
import static io.remedymatch.bedarf.controller.BedarfControllerMapper.mapToFilterEntriesRO;

@RestController
@AllArgsConstructor
@RequestMapping("/bedarf/suche")
@Validated
@Transactional
class BedarfSucheController {

    private final BedarfSucheService bedarfSucheService;

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<BedarfRO>> getAlleNichtBedienteOeffentlicheBedarfe(
            @QueryParam("artikelVarianteId") @Valid ArtikelVarianteId artikelVarianteId,
            @QueryParam("ohneEigene") Boolean ohneEigene) {
        return ResponseEntity.ok(mapToBedarfeRO(bedarfSucheService.findAlleNichtBedienteOeffentlicheBedarfe(artikelVarianteId, ohneEigene)));
    }

    @Transactional(readOnly = true)
    @GetMapping("/filter/artikelkategorie")
    public ResponseEntity<List<BedarfFilterEntryRO>> getArtikelKategorieFilter(@QueryParam("ohneEigene") Boolean ohneEigene) {
        return ResponseEntity.ok(mapToFilterEntriesRO(bedarfSucheService.getArtikelKategorieFilter(ohneEigene)));
    }

    @Transactional(readOnly = true)
    @GetMapping("/filter/artikel")
    public ResponseEntity<List<BedarfFilterEntryRO>> getArtikelFilter(
            @QueryParam("artikelKategorieId") @NotNull ArtikelKategorieId artikelKategorieId,
            @QueryParam("ohneEigene") Boolean ohneEigene) {
        return ResponseEntity.ok(mapToFilterEntriesRO(bedarfSucheService.getArtikelFilter(artikelKategorieId, ohneEigene)));
    }

    @Transactional(readOnly = true)
    @GetMapping("/filter/artikelvariante")
    public ResponseEntity<List<BedarfFilterEntryRO>> getArtikelVarianteFilter(
            @QueryParam("artikelId") @NotNull ArtikelId artikelId,
            @QueryParam("ohneEigene") Boolean ohneEigene) {
        return ResponseEntity.ok(mapToFilterEntriesRO(bedarfSucheService.getArtikelVarianteFilter(artikelId, ohneEigene)));
    }
}
