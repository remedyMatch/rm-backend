package io.remedymatch.persons;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private PersonRepository personRepository;

    public PersonController( PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    @GetMapping
    @Secured("ROLE_admin")
    public Iterable<PersonEntity> getAll() {
        return personRepository.findAll();
    }

    @PostMapping
    @Secured("ROLE_admin")
    public PersonDTO create(@RequestBody PersonDTO person) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName(person.getFirstName());
        personEntity.setLastName(person.getLastName());
        personEntity.setTelephoneNumber(person.getTelephoneNumber());

        PersonEntity saved = personRepository.save(personEntity);
        return PersonDTO.builder()
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .telephoneNumber(saved.getTelephoneNumber())
                .id(saved.getId())
                .build();
    }


    @GetMapping("/ping")
    String ping() {
        return "pong";
    }
}
