package io.remedymatch.anfrage.api;


import io.remedymatch.anfrage.domain.AnfrageRepository;
import io.remedymatch.anfrage.domain.AnfrageService;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/anfrage")
public class AnfrageController {
    private final AnfrageService anfrageService;
    private final AnfrageRepository anfrageRepository;
    private final PersonRepository personRepository;
    private final UserProvider userProvider;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> anfrageStornieren(@PathVariable("id") String anfrageId) {

        val user = personRepository.findByUsername(userProvider.getUserName());
        val anfrage = anfrageRepository.findById(UUID.fromString(anfrageId));

        if (anfrage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!anfrage.get().getInstitutionVon().getId().equals(user.getInstitution().getId())
                && !anfrage.get().getInstitutionAn().getId().equals(user.getInstitution().getId())) {
            return ResponseEntity.status(403).build();
        }

        anfrageService.anfrageStornieren(anfrageId);
        return ResponseEntity.ok().build();
    }
}
