package io.remedymatch.bedarf.controller;

import static io.remedymatch.bedarf.controller.BedarfControllerMapper.mapToAnfrageRO;
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
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/bedarf")
@Validated
@Transactional
class BedarfController {

	private final BedarfSucheService bedarfSucheService;
	private final BedarfAnlageService bedarfAnlageService;
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
			bedarfService.bedarfDerUserInstitutionLoeschen(BedarfControllerMapper.mapToBedarfId(bedarfId));
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (NotUserInstitutionObjectException e) {
			return ResponseEntity.status(403).build();
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/{bedarfId}/anfrage")
	public ResponseEntity<BedarfAnfrageRO> bedarfBedienen(//
			@PathVariable("bedarfId") @NotNull UUID bedarfId, //
			@RequestBody @Valid BedarfBedienenRequest request) {
		return ResponseEntity.ok(mapToAnfrageRO(bedarfService.bedarfAnfrageErstellen(//
				BedarfControllerMapper.mapToBedarfId(bedarfId), //
				new InstitutionStandortId(request.getStandortId()), //
				request.getKommentar(), //
				request.getAnzahl())));
	}

	@DeleteMapping("/{bedarfId}/anfrage/{anfrageId}")
	public ResponseEntity<Void> anfrageStornieren(//
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
}
