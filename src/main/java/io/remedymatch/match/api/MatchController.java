package io.remedymatch.match.api;

import io.remedymatch.match.domain.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/match")
public class MatchController {

    //    private final UserProvider userProvider;
//    private final PersonRepository personRepository;
//    private final AufgabeService aufgabeService;
    private final MatchRepository matchRepository;

//    @GetMapping()
//    public ResponseEntity<List<TaskDTO>> aufgabenLaden() {
//        val person = personRepository.findByUsername(userProvider.getUserName());
//        val aufgaben = aufgabeService.aufgabenLaden(person);
//        return ResponseEntity.ok(aufgaben);
//    }

    @PostMapping
    public void test() {

    }
}
