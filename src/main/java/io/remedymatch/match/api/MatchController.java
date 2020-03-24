package io.remedymatch.match.api;

import io.remedymatch.aufgabe.api.AufgabeAbschliessenRequest;
import io.remedymatch.aufgabe.domain.AufgabeService;
import io.remedymatch.engine.TaskDTO;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final UserProvider userProvider;
    private final PersonRepository personRepository;
    private final AufgabeService aufgabeService;

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> aufgabenLaden() {
        val person = personRepository.findByUsername(userProvider.getUserName());
        val aufgaben = aufgabeService.aufgabenLaden(person);
        return ResponseEntity.ok(aufgaben);
    }

    @PostMapping()
    public ResponseEntity<Void> aufgabeAbschliessen(@RequestBody AufgabeAbschliessenRequest request) {
        val person = personRepository.findByUsername(userProvider.getUserName());

        if (!aufgabeService.isBearbeiter(request.getTaskId(), person.getInstitution().getId().toString())) {
            return ResponseEntity.status(403).build();
        }

        aufgabeService.aufgabeAbschließen(request.getTaskId(), request.getVariables());
        return ResponseEntity.ok().build();
    }

}
