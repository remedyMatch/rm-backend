package io.remedymatch.angebot.controller;

import static io.remedymatch.angebot.controller.AngebotControllerMapper.*;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.service.AngebotAnlageService;
import io.remedymatch.angebot.domain.service.AngebotService;
import io.remedymatch.angebot.domain.service.AngebotSucheService;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.InstitutionStandortId;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/angebot")
@Validated
public class AngebotController {

	private final AngebotSucheService angebotSucheService;
	private final AngebotAnlageService angebotAnlageService;
	private final AngebotService angebotService;

	@Transactional(readOnly = true)
	@GetMapping("/suche")
	public ResponseEntity<List<AngebotRO>> getAlleNichtBedienteAngebote() {
		return ResponseEntity.ok(mapToAngeboteRO(angebotSucheService.getAlleNichtBedienteAngebote()));
	}

	@Transactional(readOnly = true)
	@GetMapping
	public ResponseEntity<List<AngebotRO>> getInstituionAngebote() {
		return ResponseEntity.ok(mapToAngeboteRO(angebotSucheService.getAngeboteDerUserInstitution()));
	}

	@PostMapping
	public ResponseEntity<AngebotRO> neueAngebotEinstellen(@RequestBody @Valid NeuesAngebotRequest neueAngebot) {
		return ResponseEntity
				.ok(mapToAngebotRO(angebotAnlageService.neueAngebotEinstellen(mapToNeueAngebot(neueAngebot))));
	}

	@PostMapping("/{angebotId}/anfrage")
	public ResponseEntity<Void> angebotAnfragen(//
			@PathVariable("angebotId") @NotNull UUID angebotId, //
			@RequestBody @Valid AngebotAnfragenRequest request) {
		angebotService.angebotAnfrageErstellen(//
				AngebotControllerMapper.maptToAngebotId(request.getAngebotId()), //
				new InstitutionStandortId(request.getStandortId()), //
				request.getKommentar(), //
				request.getAnzahl());
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{angebotId}")
	public ResponseEntity<Void> angebotLoeschen(//
			@PathVariable("angebotId") @NotNull UUID angebotId) {
		try {
			angebotService.angebotDerUserInstitutionLoeschen(AngebotControllerMapper.maptToAngebotId(angebotId));
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (NotUserInstitutionObjectException e) {
			return ResponseEntity.status(403).build();
		}

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/anfrage/{anfrageId}")
	public ResponseEntity<Void> anfrageStornieren(@PathVariable("anfrageId") String anfrageId) {
		try {
			angebotService.angebotAnfrageDerUserInstitutionLoeschen(new AngebotAnfrageId(UUID.fromString(anfrageId)));
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (NotUserInstitutionObjectException e) {
			return ResponseEntity.status(403).build();
		}
		return ResponseEntity.ok().build();
	}
}
