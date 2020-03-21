package io.remedymatch.persons;

import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@AllArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAll() {
      var persons =  StreamSupport.stream(personRepository.findAll().spliterator(), false)
              .map(this::mapToDTO).collect(Collectors.toList());
      return ResponseEntity.ok(persons);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName(person.getFirstName());
        personEntity.setLastName(person.getLastName());
        personEntity.setTelephoneNumber(person.getTelephoneNumber());

        PersonEntity saved = personRepository.save(personEntity);
        return ResponseEntity.ok(mapToDTO(saved));
    }

    @GetMapping("userInfo")
    public ResponseEntity<PersonDTO> userInfo(){
        Jwt jwt  = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        val userName = jwt.getClaims().get("sub").toString();
        val person = PersonDTO.builder().userName(userName).build();
        return ResponseEntity.ok(person);
    }

    private PersonDTO mapToDTO(PersonEntity entity){
        return PersonDTO.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .telephoneNumber(entity.getTelephoneNumber())
                .id(entity.getId())
                .build();
    }

}
