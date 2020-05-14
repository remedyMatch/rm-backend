package io.remedymatch.person.controller;

import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.person.domain.service.PersonService;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static io.remedymatch.person.controller.PersonControllerMapper.*;

@RestController
@AllArgsConstructor
@RequestMapping("/person")
@Validated
@Transactional
@Log4j2
class PersonController {

    private final UserContextService userContextService;
    private final PersonService personService;

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<PersonRO> getUserInfo() {
        return ResponseEntity.ok(mapToPersonRO(userContextService.getContextUser()));
    }

    @PutMapping
    public ResponseEntity<PersonRO> update(@RequestBody @Valid @NotNull PersonUpdateRequest personUpdate) {
        if (!personUpdate.hasAenderungen()) {
            log.info("Keine Änderungen gefunden");
            return ResponseEntity.badRequest().build();
        }

        try {
            return ResponseEntity.ok(mapToPersonRO(personService.userAktualisieren(mapToUpdate(personUpdate))));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotUserInstitutionObjectException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping("/standort")
    public ResponseEntity<PersonRO> standortZuweisen(@RequestBody @Valid NeuerPersonStandortRequest neuesStandort) {
        return ResponseEntity
                .ok(mapToPersonRO(personService.userStandortHinzufuegen(mapToNeuesStandort(neuesStandort))));
    }
}
