package io.remedymatch.person.api;

import io.remedymatch.person.domain.Person;
import io.remedymatch.person.domain.PersonId;

 class PersonMapper {

    static PersonDTO mapToDTO(Person person) {
        return PersonDTO.builder()
        		.id(person.getId().getValue())
                .vorname(person.getVorname())
               .nachname(person.getNachname())
                .telefon(person.getTelefon())
                .build();
    }

    static Person mapToEntity(PersonDTO dto) {
        return Person.builder()
        		.id(new PersonId(dto.getId()))
                .vorname(dto.getVorname())
                .nachname(dto.getNachname())
                .telefon(dto.getTelefon())
                .build();
    }
}