package io.remedymatch.person.api;

import static io.remedymatch.person.api.PersonMapper.mapToDTO;
import static io.remedymatch.person.api.PersonMapper.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.person.domain.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
@RequestMapping("/person")
class PersonController {

    private final PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> alleLaden() {
        return ResponseEntity.ok(personRepository.getAlle().stream().map(PersonMapper::mapToDTO).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
        return ResponseEntity.ok(mapToDTO(personRepository.update(mapToPerson(person))));
    }

    @GetMapping("userInfo")
    public ResponseEntity<PersonDTO> userInfo() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        val userName = jwt.getClaims().get("sub").toString();
        val person = PersonDTO.builder().userName(userName).build();
        return ResponseEntity.ok(person);
    }


}
