package io.remedymatch.angebot.api;

import static io.remedymatch.angebot.api.AngebotMapper.mapToAngebot;
import static io.remedymatch.angebot.api.AngebotMapper.mapToDto;
import static io.remedymatch.angebot.api.AngebotMapper.mapToNeueAngebot;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.angebot.domain.AngebotService;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.user.domain.NotUserInstitutionObjectException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/angebot")
@Validated
public class AngebotController {
	private final AngebotService angebotService;

	@GetMapping
	public ResponseEntity<List<AngebotDTO>> getAll() {
		return ResponseEntity.ok(angebotService.getAlleNichtBedienteAngebote().stream()//
				.map(AngebotMapper::mapToDto)//
				.collect(Collectors.toList()));
	}

	@GetMapping("/institution")
	public ResponseEntity<List<AngebotDTO>> getInstituionAngebote() {
		return ResponseEntity.ok(angebotService.getAngeboteDerUserInstitution().stream()//
				.map(AngebotMapper::mapToDto)//
				.collect(Collectors.toList()));
	}

	@PostMapping("/v2")
	public ResponseEntity<AngebotDTO> neueAngebotEinstellen(@RequestBody @Valid NeueAngebotRequest neueAngebot) {
		return ResponseEntity.ok(mapToDto(angebotService.neueAngebotEinstellen(mapToNeueAngebot(neueAngebot))));
	}

	@PostMapping
	public ResponseEntity<Void> angebotEinstellen(@RequestBody @Valid AngebotDTO angebot) {
		angebotService.angebotMelden(mapToAngebot(angebot));
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> angebotLoeschen(@PathVariable("id") String angebotId) {
		try {
			angebotService.angebotDerUserInstitutionLoeschen(AngebotMapper.maptToAngebotId(angebotId));
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (NotUserInstitutionObjectException e) {
			return ResponseEntity.status(403).build();
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/anfragen")
	public ResponseEntity<Void> angebotAnfragen(@RequestBody @Valid AngebotAnfragenRequest request) {
		angebotService.angebotAnfrageErstellen(//
				AngebotMapper.maptToAngebotId(request.getAngebotId()), //
				new InstitutionStandortId(request.getStandortId()), //
				request.getKommentar(), //
				request.getAnzahl());
		return ResponseEntity.ok().build();
	}
}
