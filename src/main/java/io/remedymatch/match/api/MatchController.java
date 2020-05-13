package io.remedymatch.match.api;

import io.remedymatch.match.domain.MatchService;
import lombok.AllArgsConstructor;
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
    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchRO>> beteiligteMatches() {
        return ResponseEntity
                .ok(matchService.beteiligteMatches().stream().map(MatchMapper::mapToDTO).collect(Collectors.toList()));
    }
}
