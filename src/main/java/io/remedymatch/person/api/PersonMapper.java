package io.remedymatch.person.api;

import io.remedymatch.person.domain.PersonEntity;

public class PersonMapper {

    public static PersonDTO mapToDTO(PersonEntity entity) {
        return PersonDTO.builder()
                .vorname(entity.getVorname())
                .nachname(entity.getNachname())
                .telefon(entity.getTelefon())
                .id(entity.getId())
                .build();
    }

    public static PersonEntity mapToEntity(PersonDTO dto) {
        return PersonEntity.builder()
                .vorname(dto.getVorname())
                .nachname(dto.getNachname())
                .telefon(dto.getTelefon())
                .id(dto.getId())
                .build();
    }
}