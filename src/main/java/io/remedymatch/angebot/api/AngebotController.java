package io.remedymatch.angebot.api;

import static io.remedymatch.angebot.api.AngebotMapper.mapToAngebot;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.angebot.domain.AngebotRepository;
import io.remedymatch.angebot.domain.AngebotService;
import io.remedymatch.institution.api.InstitutionStandortMapper;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.person.domain.PersonEntity;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.shared.GeoCalc;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
@RequestMapping("/angebot")
public class AngebotController {

	private final AngebotService angebotService;
	private final PersonRepository personRepository;
	private final UserProvider userProvider;
	private final AngebotRepository angebotRepository;

	@GetMapping
	public ResponseEntity<List<AngebotDTO>> getAll() {

		val user = personRepository.findByUsername(userProvider.getUserName());

		val angebote = angebotRepository.getAlleNichtBedienteAngebote().stream().filter(angebot -> !angebot.isBedient())
				.map(AngebotMapper::mapToDto).collect(Collectors.toList());

		angebote.forEach(a -> {
			var entfernung = GeoCalc.kilometerBerechnen(user.getInstitution().getHauptstandort(),
					InstitutionStandortMapper.mapToEntity(a.getStandort()));
			a.setEntfernung(BigDecimal.valueOf(entfernung));
		});

		return ResponseEntity.ok(angebote);
	}

	@GetMapping("/institution")
	public ResponseEntity<List<AngebotDTO>> getInstituionAngebote() {

		PersonEntity user = personRepository.findByUsername(userProvider.getUserName());

		List<AngebotDTO> angebote = angebotRepository.getAngeboteVonInstitution(new InstitutionId(user.getInstitution().getId())).stream() //
		.map(AngebotMapper::mapToDto) //
		.collect(Collectors.toList());
		
		return ResponseEntity.ok(angebote);
	}
	
	@PostMapping
	public ResponseEntity<Void> angebotMelden(@RequestBody @Valid AngebotDTO angebot) {
		val user = personRepository.findByUsername(userProvider.getUserName());
		angebotService.angebotMelden(mapToAngebot(angebot), user.getInstitution());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> angebotLoeschen(@PathVariable("id") String angebotId) {

		val user = personRepository.findByUsername(userProvider.getUserName());
		val angebot = angebotService.angebotLaden(UUID.fromString(angebotId));

		if (angebot.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		if (!angebot.get().getInstitution().getId().equals(user.getInstitution().getId())) {
			return ResponseEntity.status(403).build();
		}
		angebotService.angebotLoeschen(UUID.fromString(angebotId));
		return ResponseEntity.ok().build();
	}

	@PostMapping("/anfragen")
	public ResponseEntity<Void> angebotAnfragen(@RequestBody AngebotAnfragenRequest request) {
		val user = personRepository.findByUsername(userProvider.getUserName());
		angebotService.starteAnfrage(request.getAngebotId(), user.getInstitution(), request.getKommentar(),
				request.getStandortId(), request.getAnzahl());
		return ResponseEntity.ok().build();
	}

}
