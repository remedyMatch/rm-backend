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
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.domain.service.InstitutionService;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/institution")
@Validated
@Transactional
public class InstitutionController {

	private final InstitutionService institutionService;
	private final UserContextService UserService;

	@Transactional(readOnly = true)
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

	@Transactional(readOnly = true)
	@GetMapping("/typ")
	public ResponseEntity<List<String>> typenLaden() {
		return ResponseEntity
				.ok(Stream.of(InstitutionTyp.values()).map(InstitutionTyp::toString).collect(Collectors.toList()));
	}

    /**
     * Liefert die Institution des ContextUsers. Im Unterschied zu {@link InstitutionController#institutionLaden()}
	 * enthaelt die zurueckgelieferte Institution den HauptStandort jedoch <b>NICHT</b> in der Liste
	 * {@link InstitutionRO#getStandorte()}.
     *
     * @return Institution des ContextUsers. Der Hauptstandort ist <b>nicht</b> {@link InstitutionRO#getStandorte()}
     * enthalten!
     */
    @GetMapping("/assigned")
    @Deprecated
    public ResponseEntity<InstitutionRO> meineInstitutionLaden() {
        return ResponseEntity.ok(mapToInstitutionRO(UserService.getContextInstitution(true)));
    }
}
