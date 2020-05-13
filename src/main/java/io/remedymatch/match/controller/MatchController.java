package io.remedymatch.match.controller;

import io.remedymatch.match.domain.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/match")
public class MatchController {
    private final MatchService matchService;
//
//    @GetMapping
//    public ResponseEntity<List<MatchRO>> gematchteAnfragenLaden() {
//        return ResponseEntity
//                .ok(matchService.beteiligteMatches().stream().map(MatchMapper::mapToDTO).collect(Collectors.toList()));
//    }
}
