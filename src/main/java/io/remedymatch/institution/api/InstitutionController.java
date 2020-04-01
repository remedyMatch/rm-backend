package io.remedymatch.institution.api;

import static io.remedymatch.institution.api.InstitutionMapper.mapToDTO;
import static io.remedymatch.institution.api.InstitutionStandortMapper.mapToStandort;
import static io.remedymatch.institution.api.InstitutionStandortMapper.mapToStandortId;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.angebot.api.AngebotController;
import io.remedymatch.angebot.api.AngebotDTO;
import io.remedymatch.bedarf.api.BedarfController;
import io.remedymatch.bedarf.api.BedarfDTO;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.InstitutionService;
import io.remedymatch.institution.domain.InstitutionTyp;
import io.remedymatch.user.domain.NotUserInstitutionObjectException;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
@RequestMapping("/institution")
public class InstitutionController {
	private final InstitutionService institutionService;
	private final AngebotController angebotController;
	private final BedarfController bedarfController;

	@PutMapping
	public ResponseEntity<Void> update(@RequestBody InstitutionUpdateRequest institutionUpdate) {
		try {
			institutionService.userInstitutionAktualisieren(//
					InstitutionMapper.maptToInstitutionId(institutionUpdate.getId()), //
					institutionUpdate.getName(), //
					institutionUpdate.getTyp());
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (NotUserInstitutionObjectException e) {
			return ResponseEntity.status(403).build();
		}

		return ResponseEntity.ok().build();
	}

	@PutMapping("/hauptstandort")
	public ResponseEntity<InstitutionDTO> updateHauptstandort(@RequestBody @Valid InstitutionStandortDTO standort) {
		return ResponseEntity
				.ok(mapToDTO(institutionService.userInstitutionHauptstandortAktualisieren(mapToStandort(standort))));
	}

	@PostMapping("/standort")
	public ResponseEntity<InstitutionDTO> standortHinzufuegen(@RequestBody @Valid InstitutionStandortDTO standort) {
		return ResponseEntity
				.ok(mapToDTO(institutionService.userInstitutionStandortHinzufuegen(mapToStandort(standort))));
	}

	@DeleteMapping("/standort/{standortId}")
	public ResponseEntity<InstitutionDTO> standortEntfernen(@PathVariable("standortId") String standortId) {
		return ResponseEntity
				.ok(mapToDTO(institutionService.userInstitutionStandortEntfernen(mapToStandortId(standortId))));
	}

	@GetMapping("/typ")
	public ResponseEntity<List<String>> typenLaden() {
		val typen = Arrays.asList(InstitutionTyp.Krankenhaus, InstitutionTyp.Arzt, InstitutionTyp.Lieferant,
				InstitutionTyp.Privat, InstitutionTyp.Andere);
		return ResponseEntity.ok(typen.stream().map(InstitutionTyp::toString).collect(Collectors.toList()));
	}

	@GetMapping("/bedarf")
	public ResponseEntity<List<BedarfDTO>> bedarfLaden() {
		// FIXME: entfernen
		return bedarfController.getInstituionBedarfee();
	}

	@GetMapping("/angebot")
	public ResponseEntity<List<AngebotDTO>> angebotLaden() {
		// FIXME: entfernen
		return angebotController.getInstituionAngebote();
	}

	@GetMapping("/assigned")
	public ResponseEntity<InstitutionDTO> institutionLaden() {
		return ResponseEntity.ok(mapToDTO(institutionService.userInstitutionLaden()));
	}

	@GetMapping("/anfragen/gestellt")
	public ResponseEntity<List<AnfrageDTO>> gestellteAnfragen() {
		return ResponseEntity.ok(institutionService.getGestellteUserInstitutionAnfragen().stream()
				.map(AnfrageMapper::mapToDTO).collect(Collectors.toList()));
	}

	@GetMapping("/anfragen/erhalten")
	public ResponseEntity<List<AnfrageDTO>> erhalteneAnfragen() {
		return ResponseEntity.ok(institutionService.getErhalteneUserInstitutionAnfragen().stream()
				.map(AnfrageMapper::mapToDTO).collect(Collectors.toList()));
	}
}
