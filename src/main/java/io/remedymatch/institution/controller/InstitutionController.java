package io.remedymatch.institution.controller;

import static io.remedymatch.institution.controller.InstitutionMapper.mapToInstitutionRO;
import static io.remedymatch.institution.controller.InstitutionStandortMapper.mapToNeuesStandort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.domain.service.InstitutionService;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/institution")
public class InstitutionController {

	private final InstitutionService institutionService;
	private final UserService UserService;

	@GetMapping
	public ResponseEntity<InstitutionRO> institutionLaden() {
		return ResponseEntity.ok(mapToInstitutionRO(UserService.getContextInstitution()));
	}

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
			@RequestBody @Valid NeuesInstitutionStandortRequest neuesStandort) {

		// XXX sollte weg sein nachdem der RegistrierungFreigabe Prozess fertig ist...

		return ResponseEntity.ok(mapToInstitutionRO(
				institutionService.userInstitutionHauptstandortHinzufuegen(mapToNeuesStandort(neuesStandort))));
	}

	@PostMapping("/standort")
	public ResponseEntity<InstitutionRO> standortHinzufuegen(
			@RequestBody @Valid NeuesInstitutionStandortRequest neuesStandort) {
		return ResponseEntity.ok(mapToInstitutionRO(
				institutionService.userInstitutionStandortHinzufuegen(mapToNeuesStandort(neuesStandort))));
	}

	@GetMapping("/typ")
	public ResponseEntity<List<String>> typenLaden() {
		val typen = Arrays.asList(InstitutionTyp.Krankenhaus, InstitutionTyp.Arzt, InstitutionTyp.Lieferant,
				InstitutionTyp.Privat, InstitutionTyp.Andere);
		return ResponseEntity.ok(typen.stream().map(InstitutionTyp::toString).collect(Collectors.toList()));
	}

	// ist das gleiche wie pures GET
	@GetMapping("/assigned")
	@Deprecated
	public ResponseEntity<InstitutionRO> meineInstitutionLaden() {
		return institutionLaden();
	}
}
