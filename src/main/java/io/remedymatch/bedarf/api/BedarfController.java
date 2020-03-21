package io.remedymatch.bedarf.api;

import io.remedymatch.bedarf.domain.BedarfRepository;
import io.remedymatch.person.domain.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@AllArgsConstructor
@RequestMapping("/institution")
public class BedarfController {

    private final BedarfRepository institutionsRepository;
    private final PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<List<BedarfDTO>> getAll() {
        val institutions = StreamSupport.stream(institutionsRepository.findAll().spliterator(), false)
                .map(BedarfMapper::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(institutions);
    }

    @PostMapping
    public ResponseEntity<Void> bedarfMelden(@RequestBody BedarfDTO bedarf) {


    }


}
