package io.remedymatch.aufgabe.api;

import io.remedymatch.aufgabe.domain.AufgabeService;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/aufgabe")
public class AufgabeController {

    private final UserProvider userProvider;
    private final AufgabeService aufgabeService;

    @GetMapping
    public ResponseEntity<List<String>> aufgabenLaden() {
        return ResponseEntity.ok(aufgabeService.aufgabenLaden());
    }

    @PostMapping
    public ResponseEntity<Void> aufgabeAbschliessen() {
        return ResponseEntity.ok().build();
    }

}
