package io.remedymatch.angebot.api;

import io.remedymatch.angebot.domain.AngebotService;
import io.remedymatch.institution.api.InstitutionStandortMapper;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.shared.GeoCalc;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.remedymatch.angebot.api.AngebotMapper.mapToEntity;


@RestController
@AllArgsConstructor
@RequestMapping("/angebot")
public class AngebotController {

    private final AngebotService angebotService;
    private final PersonRepository personRepository;
    private final UserProvider userProvider;

    @GetMapping
    public ResponseEntity<List<AngebotDTO>> getAll() {

        val user = personRepository.findByUsername(userProvider.getUserName());

        val angebote = StreamSupport.stream(angebotService.alleAngeboteLaden().spliterator(), false)
                .filter(angebot -> !angebot.isBedient()).map(AngebotMapper::mapToDTO).collect(Collectors.toList());

        angebote.forEach(a -> {
            var entfernung = GeoCalc.kilometerBerechnen(user.getInstitution().getHauptstandort(), InstitutionStandortMapper.mapToEntity(a.getStandort()));
            a.setEntfernung(entfernung);
        });

        return ResponseEntity.ok(angebote);
    }

    @PostMapping
    public ResponseEntity<Void> angebotMelden(@RequestBody @Valid AngebotDTO angebot) {
        val user = personRepository.findByUsername(userProvider.getUserName());
        angebotService.angebotMelden(mapToEntity(angebot), user.getInstitution());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> angebotLoeschen(@PathVariable("id") String angebotId) {

        val user = personRepository.findByUsername(userProvider.getUserName());
        val angebot = angebotService.angebotLaden(UUID.fromString(angebotId));

        if (angebot.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!angebot.get().getInstitution().getId().equals(user.getInstitution().getId())) {
            return ResponseEntity.status(403).build();
        }
        angebotService.angebotLoeschen(UUID.fromString(angebotId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/anfragen")
    public ResponseEntity<Void> angebotAnfragen(@RequestBody AngebotAnfragenRequest request) {
        val user = personRepository.findByUsername(userProvider.getUserName());
        angebotService.starteAnfrage(
                request.getAngebotId(),
                user.getInstitution(),
                request.getKommentar(),
                request.getStandortId(),
                request.getAnzahl());
        return ResponseEntity.ok().build();
    }

}
