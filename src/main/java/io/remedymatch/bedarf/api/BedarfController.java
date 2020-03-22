package io.remedymatch.bedarf.api;

import io.remedymatch.bedarf.domain.BedarfService;
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

import static io.remedymatch.bedarf.api.BedarfMapper.mapToEntity;

@RestController
@AllArgsConstructor
@RequestMapping("/bedarf")
public class BedarfController {

    private final BedarfService bedarfService;
    private final UserProvider userProvider;
    private final PersonRepository personRepository;

    @GetMapping()
    public ResponseEntity<List<BedarfDTO>> bedarfLaden() {
        val institutions = StreamSupport.stream(bedarfService.alleBedarfeLaden().spliterator(), false)
                .map(BedarfMapper::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(institutions);
    }

    @PostMapping("/melden")
    public ResponseEntity<Void> bedarfMelden(@RequestBody BedarfDTO bedarf) {
        val user = personRepository.findByUsername(userProvider.getUserName());
        bedarfService.bedarfMelden(mapToEntity(bedarf), user.getInstitution());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> bedarfLoeschen(@PathVariable("id") String bedarfId) {
        bedarfService.bedarfLoeschen(UUID.fromString(bedarfId));
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> bedarfUpdaten(@RequestBody BedarfDTO bedarfDTO) {
        bedarfService.bedarfUpdaten(BedarfMapper.mapToEntity(bedarfDTO));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bedienen")
    public ResponseEntity<Void> bedarfBedienen(@RequestBody BedarfBedienenRequest request) {

        return ResponseEntity.ok().build();
    }

}
