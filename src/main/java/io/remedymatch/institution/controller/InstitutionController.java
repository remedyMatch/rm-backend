package io.remedymatch.institution.controller;

import static io.remedymatch.institution.controller.InstitutionMapper.mapToInstitutionRO;
import static io.remedymatch.institution.controller.InstitutionStandortMapper.mapToNeuesStandort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.model.InstitutionAntrag;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.domain.service.InstitutionService;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
@RequestMapping("/institution")
@Validated
@Transactional
public class InstitutionController {

	private final InstitutionService institutionService;

	@PutMapping
	public ResponseEntity<Void> update(@RequestBody @Valid @NotNull InstitutionUpdateRequest institutionUpdate) {
		try {
			institutionService.userInstitutionAktualisieren(InstitutionMapper.mapToUpdate(institutionUpdate));
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (NotUserInstitutionObjectException e) {
			return ResponseEntity.status(403).build();
		}

		return ResponseEntity.ok().build();
	}

	@PutMapping("/hauptstandort")
	public ResponseEntity<InstitutionRO> updateHauptstandort(
			@RequestBody @Valid NeuerInstitutionStandortRequest neuesStandort) {

		// XXX sollte weg sein nachdem der RegistrierungFreigabe Prozess fertig ist...

		return ResponseEntity.ok(mapToInstitutionRO(
				institutionService.userInstitutionHauptstandortHinzufuegen(mapToNeuesStandort(neuesStandort))));
	}

	@PostMapping("/standort")
	public ResponseEntity<InstitutionRO> standortHinzufuegen(
			@RequestBody @Valid NeuerInstitutionStandortRequest neuesStandort) {
		return ResponseEntity.ok(mapToInstitutionRO(
				institutionService.userInstitutionStandortHinzufuegen(mapToNeuesStandort(neuesStandort))));
	}

	@Transactional(readOnly = true)
	@GetMapping("/typ")
	public ResponseEntity<List<String>> typenLaden() {
		return ResponseEntity
				.ok(Stream.of(InstitutionTyp.values()).map(InstitutionTyp::toString).collect(Collectors.toList()));
	}

	/**
	 * Mit diesem Aufruf kann eine Institution beantragt werden.
	 *
	 * @param request Die Daten der Insitution, die beantrag wird
	 */
	@PostMapping("/beantragen")
	public ResponseEntity<Void> institutionBeantragen(@RequestBody @Valid InstitutionBeantragenRequest request) {
		institutionService.institutionBeantragen(InstitutionAntrag.builder().hausnummer(request.getHausnummer())
				.rolle(request.getRolle()).land(request.getLand()).name(request.getName()).ort(request.getOrt())
				.plz(request.getPlz()).strasse(request.getStrasse()).webseite(request.getWebseite())
				.institutionTyp(request.getInstitutionTyp()).build());
		return ResponseEntity.ok().build();
	}

	/**
	 * Dieser Aufruf lädt alle erstellten Anträge.
	 *
	 * @return Die erstellten Anträge
	 */
	@GetMapping("/antrag")
	public ResponseEntity<List<InstitutionAntragRO>> ladeInstitutionAntraege() {
		val antraege = institutionService.ladeErstellteAntraege().stream().map(InstitutionAntragMapper::mapToRO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(antraege);
	}
}
