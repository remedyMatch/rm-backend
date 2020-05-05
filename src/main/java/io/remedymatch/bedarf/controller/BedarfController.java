package io.remedymatch.bedarf.controller;

import io.remedymatch.angebot.controller.AngebotAnfrageBeantwortenRequest;
import io.remedymatch.angebot.controller.AngebotAnzahlAendernRequest;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageSucheService;
import io.remedymatch.bedarf.domain.service.BedarfAnlageService;
import io.remedymatch.bedarf.domain.service.BedarfService;
import io.remedymatch.bedarf.domain.service.BedarfSucheService;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
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

import static io.remedymatch.bedarf.controller.BedarfControllerMapper.*;

@RestController
@AllArgsConstructor
@RequestMapping("/bedarf")
@Validated
@Transactional
class BedarfController {

    private final BedarfSucheService bedarfSucheService;
    private final BedarfAnlageService bedarfAnlageService;
    private final BedarfAnfrageSucheService bedarfAnfrageSucheService;
    private final BedarfService bedarfService;

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<BedarfRO>> getInstituionBedarfe() {
        return ResponseEntity.ok(mapToBedarfeRO(bedarfSucheService.findAlleNichtBedienteBedarfeDerUserInstitution()));
    }

    @PostMapping
    public ResponseEntity<BedarfRO> neuesBedarfMelden(@RequestBody @Valid NeuesBedarfRequest neueBedarf) {
        return ResponseEntity.ok(mapToBedarfRO(bedarfAnlageService.neuesBedarfEinstellen(mapToNeuesBedarf(neueBedarf))));
    }

    @DeleteMapping("/{bedarfId}")
    public ResponseEntity<Void> bedarfLoeschen(//
                                               @PathVariable("bedarfId") @NotNull UUID bedarfId) {
        try {
            bedarfService.bedarfDerUserInstitutionSchliessen(BedarfControllerMapper.mapToBedarfId(bedarfId));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotUserInstitutionObjectException e) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bedarfId}/anfrage")
    public ResponseEntity<BedarfAnfrageRO> bedarfBedienen(
            @PathVariable("bedarfId") @NotNull UUID bedarfId, //
            @RequestBody @Valid BedarfBedienenRequest request) {
        return ResponseEntity.ok(mapToAnfrageRO(bedarfService.bedarfAnfrageErstellen(//
                BedarfControllerMapper.mapToBedarfId(bedarfId), //
                new InstitutionStandortId(request.getStandortId()), //
                request.getKommentar(), //
                request.getAnzahl(), //
                new AngebotId(request.getAngebotId()))));
    }

    @PostMapping("/{bedarfId}/anfrage/{anfrageId}/stornieren")
    public ResponseEntity<Void> anfrageStornieren(
            @PathVariable("bedarfId") @NotNull UUID bedarfId, //
            @PathVariable("anfrageId") @NotNull UUID anfrageId) {
        try {
            bedarfService.bedarfAnfrageDerUserInstitutionStornieren(//
                    BedarfControllerMapper.mapToBedarfId(bedarfId), //
                    new BedarfAnfrageId(anfrageId));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotUserInstitutionObjectException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bedarfId}/schliessen")
    public ResponseEntity<Void> angebotSchliessen(
            @PathVariable("bedarfId") @NotNull UUID bedarfId) {
        try {
            bedarfService.bedarfDerUserInstitutionSchliessen(BedarfControllerMapper.mapToBedarfId(bedarfId));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotUserInstitutionObjectException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bedarfId}/anzahl")
    public ResponseEntity<Void> bedarfAnzahlAendern(
            @PathVariable("bedarfId") @NotNull UUID angebotId, @Valid @NotNull AngebotAnzahlAendernRequest request) {
        try {
            bedarfService.bedarfAnzahlAendern(BedarfControllerMapper.mapToBedarfId(angebotId), request.getAnzahl());
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
            bedarfService.bedarfAnfrageBeantworten(//
                    BedarfControllerMapper.mapToBedarfId(angebotId), //
                    new BedarfAnfrageId(anfrageId),//
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
    public ResponseEntity<List<GestellteBedarfAnfrageRO>> getGestellteAnfragen() {
        val offeneGestellteAnfragen = bedarfAnfrageSucheService.findAlleOffeneAnfragenDerUserInstitution();
        return ResponseEntity.ok(offeneGestellteAnfragen.stream().map(BedarfControllerMapper::mapToGestellteBedarfAnfrageRO).collect(Collectors.toList()));
    }
}
