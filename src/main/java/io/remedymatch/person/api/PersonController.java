package io.remedymatch.person.api;

import io.remedymatch.person.domain.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.remedymatch.person.api.PersonMapper.mapToDTO;
import static io.remedymatch.person.api.PersonMapper.mapToEntity;

@RestController
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> alleLaden() {
        var persons = StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .map(PersonMapper::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(persons);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
        val personEntity = mapToEntity(person);
        return ResponseEntity.ok(mapToDTO(personRepository.save(personEntity)));
    }

    @GetMapping("userInfo")
    public ResponseEntity<PersonDTO> userInfo() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        val userName = jwt.getClaims().get("sub").toString();
        val person = PersonDTO.builder().userName(userName).build();
        return ResponseEntity.ok(person);
    }


}
