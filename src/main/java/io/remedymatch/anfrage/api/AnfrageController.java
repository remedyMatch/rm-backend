package io.remedymatch.anfrage.api;


import io.remedymatch.anfrage.domain.AnfrageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/angebot")
public class AnfrageController {
    private final AnfrageService anfrageService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> anfrageStornieren(@PathVariable("id") String anfrageId) {
        anfrageService.anfrageStornieren(anfrageId);
        return ResponseEntity.ok().build();
    }
}
