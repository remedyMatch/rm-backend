package io.remedymatch.person.controller;

import io.remedymatch.institution.controller.InstitutionMapper;
import io.remedymatch.institution.controller.InstitutionStandortMapper;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.person.domain.model.*;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

class PersonControllerMapper {
    private PersonControllerMapper() {
    }

    static PersonRO mapToPersonRO(final Person person) {
        return PersonRO.builder() //
                .id(person.getId().getValue()) //
                .username(person.getUsername()) //
                .vorname(person.getVorname()) //
                .nachname(person.getNachname()) //
                .email(person.getEmail()) //
                .telefon(person.getTelefon()) //
                .aktuellerStandort(mapStandortToRO(person.getAktuellesStandort())) //
                .standorte(mapStandorteToRO(person.getStandorte())) //
                .build();
    }

    private static List<PersonStandortRO> mapStandorteToRO(final List<PersonStandort> personStandorte) {
        return personStandorte.stream().map(PersonControllerMapper::mapStandortToRO).collect(Collectors.toList());
    }

    private static PersonStandortRO mapStandortToRO(final PersonStandort personStandort) {
        return PersonStandortRO.builder()//
                .id(personStandort.getId().getValue()) //
                .institution(InstitutionMapper.mapToInstitutionRO(personStandort.getInstitution())) //
                .standort(InstitutionStandortMapper.mapToStandortRO(personStandort.getStandort())) //
                .oeffentlich(personStandort.isOeffentlich()) //
                .build();
    }


    static PersonUpdate mapToUpdate(final PersonUpdateRequest personUpdateRequest) {
        return PersonUpdate.builder()//
                .aktuellesStandortId(new PersonStandortId(personUpdateRequest.getAktuellesStandortId())) //
                .build();
    }

    static NeuesPersonStandort mapToNeuesStandort(final NeuesPersonStandortRequest neuesPersonStandortRequest) {
        return NeuesPersonStandort.builder()//
                .institutionId(new InstitutionId(neuesPersonStandortRequest.getInstitutionId())) //
                .standortId(new InstitutionStandortId(neuesPersonStandortRequest.getStandortId())) //
                .oeffentlich(neuesPersonStandortRequest.isOeffentlich()) //
                .build();
    }

    static List<Institution2StandortRO> mapToInstitution2StandortRO(List<PersonStandort> personStandorte) {
        var institutionId2PersonStandortMap = personStandorte.stream()
                .collect(Collectors.groupingBy(p -> p.getInstitution().getId().getValue()));

        var institution2Standorte = institutionId2PersonStandortMap.keySet().stream().map(institutionId -> {
            val institution = institutionId2PersonStandortMap.get(institutionId).get(0).getInstitution();
            val standorte = institutionId2PersonStandortMap.get(institutionId).stream().map(PersonStandort::getStandort).collect(Collectors.toList());
            var inst2Standort = Institution2StandortRO.builder()
                    .institution(InstitutionMapper.mapToInstitutionRO(institution))
                    .standorte(InstitutionStandortMapper.mapToStandorteRO(standorte))
                    .build();
            return inst2Standort;
        }).collect(Collectors.toList());

        return institution2Standorte;
    }
}