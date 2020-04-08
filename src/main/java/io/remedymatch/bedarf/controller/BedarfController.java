package io.remedymatch.bedarf.controller;

import static io.remedymatch.bedarf.controller.BedarfControllerMapper.mapToBedarfRO;
import static io.remedymatch.bedarf.controller.BedarfControllerMapper.mapToBedarfeRO;
import static io.remedymatch.bedarf.controller.BedarfControllerMapper.mapToNeuesBedarf;

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

import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.service.BedarfAnlageService;
import io.remedymatch.bedarf.domain.service.BedarfService;
import io.remedymatch.bedarf.domain.service.BedarfSucheService;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.InstitutionStandortId;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/bedarf")
@Validated
public class BedarfController {

	private final BedarfSucheService bedarfSucheService;
	private final BedarfAnlageService bedarfAnlageService;
	private final BedarfService bedarfService;

	@Transactional(readOnly = true)
	@GetMapping("/suche")
	public ResponseEntity<List<BedarfRO>> getAlleNichtBedienteBedarfe() {
		return ResponseEntity.ok(mapToBedarfeRO(bedarfSucheService.getAlleNichtBedienteBedarfe()));
	}

	@Transactional(readOnly = true)
	@GetMapping
	public ResponseEntity<List<BedarfRO>> getInstituionBedarfe() {
		return ResponseEntity.ok(mapToBedarfeRO(bedarfSucheService.getBedarfeDerUserInstitution()));
	}

	@PostMapping
	public ResponseEntity<BedarfRO> neueBedarfEinstellen(@RequestBody @Valid NeuesBedarfRequest neueBedarf) {
		return ResponseEntity.ok(mapToBedarfRO(bedarfAnlageService.neueBedarfEinstellen(mapToNeuesBedarf(neueBedarf))));
	}

	@PostMapping("/{bedarfId}/anfrage")
	public ResponseEntity<Void> bedarfBedienen(//
			@PathVariable("bedarfId") @NotNull UUID bedarfId, //
			@RequestBody @Valid BedarfBedienenRequest request) {
		bedarfService.bedarfAnfrageErstellen(//
				BedarfControllerMapper.maptToBedarfId(request.getBedarfId()), //
				new InstitutionStandortId(request.getStandortId()), //
				request.getKommentar(), //
				request.getAnzahl());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{bedarfId}")
	public ResponseEntity<Void> bedarfLoeschen(//
			@PathVariable("bedarfId") @NotNull UUID bedarfId) {
		try {
			bedarfService.bedarfDerUserInstitutionLoeschen(BedarfControllerMapper.maptToBedarfId(bedarfId));
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
			bedarfService.bedarfAnfrageDerUserInstitutionLoeschen(new BedarfAnfrageId(UUID.fromString(anfrageId)));
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (NotUserInstitutionObjectException e) {
			return ResponseEntity.status(403).build();
		}
		return ResponseEntity.ok().build();
	}
}
