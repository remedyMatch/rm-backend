package io.remedymatch.match.controller;

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.match.domain.MatchService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchRO>> beteiligteMatchesLaden() {
        val beteiligteMatches = matchService.ladeBeteiligteMatches();
        return ResponseEntity.ok(MatchMapper.map(beteiligteMatches));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchRO> matchLaden(@PathVariable("id") UUID matchId) {
        try {
            return ResponseEntity.ok(MatchMapper.map(matchService.ladeMatch(matchId)));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
