package io.remedymatch.match.controller;

import io.remedymatch.match.domain.MatchService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
