package io.remedymatch.aufgabe.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.aufgabe.domain.AufgabeService;
import io.remedymatch.engine.TaskDTO;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
@RequestMapping("/aufgabe")
public class AufgabeController {

	private final UserContextService userService;
	private final AufgabeService aufgabeService;

	@GetMapping
	public ResponseEntity<List<TaskDTO>> aufgabenLaden() {
		val person = userService.getContextUser();
		val aufgaben = aufgabeService.aufgabenLaden(person);
		return ResponseEntity.ok(aufgaben);
	}

	@Transactional
	@PostMapping
	public ResponseEntity<Void> aufgabeAbschliessen(@RequestBody AufgabeAbschliessenRequest request) {
		val person = userService.getContextUser();

		if (!aufgabeService.isBearbeiter(request.getTaskId(), person.getInstitution().getId().toString())) {
			return ResponseEntity.status(403).build();
		}

		aufgabeService.aufgabeAbschließen(request.getTaskId(), request.getVariables());

		return ResponseEntity.ok().build();
	}

}
