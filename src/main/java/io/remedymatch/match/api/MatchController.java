package io.remedymatch.match.api;

import io.remedymatch.match.domain.MatchService;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final UserProvider userProvider;
    private final PersonRepository personRepository;
    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchDTO>> beteiligteMatches() {
        val user = personRepository.findByUsername(userProvider.getUserName());

        return ResponseEntity.ok(matchService.beteiligteMatches(user.getInstitution())
                .stream()
                .map(MatchMapper::mapToDTO)
                .collect(Collectors.toList()));
    }
}
