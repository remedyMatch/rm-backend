package io.remedymatch.angebot.api;

import io.remedymatch.angebot.domain.AngebotService;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        val angebote = StreamSupport.stream(angebotService.alleAngeboteLaden().spliterator(), false)
                .map(AngebotMapper::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(angebote);
    }

    @PostMapping
    public ResponseEntity<Void> angebotMelden(@RequestBody AngebotDTO angebot) {
        val user = personRepository.findByUsername(userProvider.getUserName());
        angebotService.angebotMelden(mapToEntity(angebot), user.getInstitution());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> angebotLoeschen(@PathVariable("id") String angebotId) {
        angebotService.angebotLoeschen(UUID.fromString(angebotId));
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> angebotUpdaten(@RequestBody AngebotDTO angebotDTO) {
        angebotService.angebotUpdaten(mapToEntity(angebotDTO));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/anfragen")
    public ResponseEntity<Void> bedarfBedienen(@RequestBody AngebotAnfragenRequest request) {
        val user = personRepository.findByUsername(userProvider.getUserName());
        angebotService.starteAnfrage(request.getAngebotId(), user.getInstitution(), request.getKommentar(), request.getStandort());
        return ResponseEntity.ok().build();
    }

}
