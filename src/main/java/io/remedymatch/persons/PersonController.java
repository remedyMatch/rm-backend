package io.remedymatch.persons;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private PersonRepository personRepository;

    public PersonController( PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    @GetMapping("/persons")
    public Iterable<PersonEntity> getAll() {
        return personRepository.findAll();
    }

    @PostMapping("/persons")
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
}
