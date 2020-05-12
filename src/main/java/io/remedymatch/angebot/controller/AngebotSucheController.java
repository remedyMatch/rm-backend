package io.remedymatch.angebot.controller;

import io.remedymatch.angebot.domain.service.AngebotSucheService;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelKategorieId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
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

import static io.remedymatch.angebot.controller.AngebotControllerMapper.mapToAngeboteRO;
import static io.remedymatch.angebot.controller.AngebotControllerMapper.mapToFilterEntriesRO;

@RestController
@AllArgsConstructor
@RequestMapping("/angebot/suche")
@Validated
@Transactional
public class AngebotSucheController {

    private final AngebotSucheService angebotSucheService;

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<AngebotRO>> getAlleNichtBedienteOeffentlicheAngebote(
            @QueryParam("artikelVarianteId") @Valid ArtikelVarianteId artikelVarianteId,
            @QueryParam("ohneEigene") boolean ohneEigene) {
        return ResponseEntity.ok(mapToAngeboteRO(angebotSucheService.findAlleNichtBedienteOeffentlicheAngebote(artikelVarianteId, ohneEigene)));
    }

    @Transactional(readOnly = true)
    @GetMapping("/filter/artikelkategorie")
    public ResponseEntity<List<AngebotFilterEntryRO>> getArtikelKategorieFilter(@QueryParam("ohneEigene") @Valid boolean ohneEigene) {
        return ResponseEntity.ok(mapToFilterEntriesRO(angebotSucheService.getArtikelKategorieFilter(ohneEigene)));
    }

    @Transactional(readOnly = true)
    @GetMapping("/filter/artikel")
    public ResponseEntity<List<AngebotFilterEntryRO>> getArtikelFilter(
            @QueryParam("artikelKategorieId") @NotNull ArtikelKategorieId artikelKategorieId,
            @QueryParam("ohneEigene") boolean ohneEigene) {
        return ResponseEntity.ok(mapToFilterEntriesRO(angebotSucheService.getArtikelFilter(artikelKategorieId, ohneEigene)));
    }

    @Transactional(readOnly = true)
    @GetMapping("/filter/artikelvariante")
    public ResponseEntity<List<AngebotFilterEntryRO>> getArtikelVarianteFilter(
            @QueryParam("artikelId") @NotNull ArtikelId artikelId,
            @QueryParam("ohneEigene") boolean ohneEigene) {
        return ResponseEntity.ok(mapToFilterEntriesRO(angebotSucheService.getArtikelVarianteFilter(artikelId, ohneEigene)));
    }
}
