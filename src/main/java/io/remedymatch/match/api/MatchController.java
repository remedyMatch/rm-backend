package io.remedymatch.match.api;

import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.match.domain.MatchRepository;
import io.remedymatch.match.domain.MatchService;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.shared.GeoCalc;
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
    private final MatchRepository matchRepository;

    @GetMapping
    public ResponseEntity<List<MatchDTO>> beteiligteMatches() {
        val user = personRepository.findByUsername(userProvider.getUserName());

        val matches = matchService.beteiligteMatches(InstitutionEntityConverter.convert(user.get().getInstitution()))
                .stream()
                .map(MatchMapper::mapToDTO)
                .collect(Collectors.toList());

        matches.forEach(m -> {
            var entfernung = GeoCalc.kilometerBerechnen(MatchStandortMapper.mapToEntity(m.getStandortVon()), MatchStandortMapper.mapToEntity(m.getStandortAn()));
            m.setEntfernung(entfernung);
        });

        return ResponseEntity.ok(matches);
    }
}
