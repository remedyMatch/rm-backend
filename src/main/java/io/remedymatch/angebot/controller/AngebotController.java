package io.remedymatch.angebot.controller;

import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.service.AngebotAnfrageSucheService;
import io.remedymatch.angebot.domain.service.AngebotAnlageService;
import io.remedymatch.angebot.domain.service.AngebotService;
import io.remedymatch.angebot.domain.service.AngebotSucheService;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAllowedException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.remedymatch.angebot.controller.AngebotControllerMapper.*;

@RestController
@AllArgsConstructor
@RequestMapping("/angebot")
@Validated
@Transactional
public class AngebotController {

    private final AngebotSucheService angebotSucheService;
    private final AngebotAnfrageSucheService angebotAnfrageSucheService;
    private final AngebotAnlageService angebotAnlageService;
    private final AngebotService angebotService;

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<InstitutionAngebotRO>> getInstituionAngebote() {
        return ResponseEntity.ok(mapToInstitutionAngebotRO(angebotSucheService.findAlleNichtBedienteAngeboteDerUserInstitution()));
    }

    @PostMapping
    public ResponseEntity<AngebotRO> neuesAngebotEinstellen(@RequestBody @NotNull @Valid NeuesAngebotRequest neueAngebot) {
        return ResponseEntity
                .ok(mapToAngebotRO(angebotAnlageService.neueAngebotEinstellen(mapToNeueAngebot(neueAngebot))));
    }

    @PostMapping("/{angebotId}/schliessen")
    public ResponseEntity<Void> angebotSchliessen(
            @PathVariable("angebotId") @NotNull UUID angebotId) {
        try {
            angebotService.angebotDerUserInstitutionSchliessen(AngebotControllerMapper.mapToAngebotId(angebotId));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotUserInstitutionObjectException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{angebotId}/anzahl")
    public ResponseEntity<Void> angebotAnzahlAendern(
            @PathVariable("angebotId") @NotNull UUID angebotId,
            @Valid @NotNull AngebotAnzahlAendernRequest request) {
        try {
            angebotService.angebotAnzahlAendern(AngebotControllerMapper.mapToAngebotId(angebotId), request.getAnzahl());
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotUserInstitutionObjectException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{angebotId}/anfrage")
    public ResponseEntity<AngebotAnfrageRO> angebotAnfragen(
            @PathVariable("angebotId") @NotNull UUID angebotId, //
            @RequestBody @Valid AngebotAnfragenRequest request) {
        try {
            return ResponseEntity.ok(mapToAnfrageRO(angebotService.angebotAnfrageErstellen(//
                    AngebotControllerMapper.mapToAngebotId(angebotId), //
                    request.getNachricht(), //
                    request.getAnzahl(), //
                    new BedarfId(request.getBedarfId()))));
        } catch (OperationNotAllowedException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping("/{angebotId}/anfrage/{anfrageId}/stornieren")
    public ResponseEntity<Void> anfrageStornieren(
            @PathVariable("angebotId") @NotNull UUID angebotId, //
            @PathVariable("anfrageId") @NotNull UUID anfrageId) {
        try {
            angebotService.angebotAnfrageDerUserInstitutionStornieren(//
                    AngebotControllerMapper.mapToAngebotId(angebotId), //
                    new AngebotAnfrageId(anfrageId));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotUserInstitutionObjectException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{angebotId}/anfrage/{anfrageId}/beantworten")
    public ResponseEntity<Void> anfrageBeantworten(
            @PathVariable("angebotId") @NotNull UUID angebotId, //
            @PathVariable("anfrageId") @NotNull UUID anfrageId, //
            @RequestBody @Valid AngebotAnfrageBeantwortenRequest request) {
        try {
            angebotService.angebotAnfrageBeantworten(//
                    AngebotControllerMapper.mapToAngebotId(angebotId), //
                    new AngebotAnfrageId(anfrageId),//
                    request.getEntscheidung());
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotUserInstitutionObjectException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = true)
    @GetMapping("/anfrage/gestellt")
    public ResponseEntity<List<GestellteAngebotAnfrageRO>> getGestellteAnfragen() {
        val offeneGestellteAnfragen = angebotAnfrageSucheService.findAlleOffeneAnfragenDerUserInstitution();
        return ResponseEntity.ok(offeneGestellteAnfragen.stream().map(AngebotControllerMapper::mapToGestellteAngebotAnfrageRO).collect(Collectors.toList()));
    }
}
