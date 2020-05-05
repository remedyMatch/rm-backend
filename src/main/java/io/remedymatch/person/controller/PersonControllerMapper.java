package io.remedymatch.person.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.institution.controller.InstitutionMapper;
import io.remedymatch.institution.controller.InstitutionStandortMapper;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonInstitution;

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
				.aktuelleInstitution(mapInstitutionToRO(person.getAktuelleInstitution())) //
				.institutionen(mapInstitutionenToRO(person.getInstitutionen())) //
				.build();
	}

	private static List<PersonInstitutionRO> mapInstitutionenToRO(final List<PersonInstitution> personInstitutionen) {
		return personInstitutionen.stream().map(PersonControllerMapper::mapInstitutionToRO)
				.collect(Collectors.toList());
	}

	private static PersonInstitutionRO mapInstitutionToRO(final PersonInstitution personInstitution) {
		return PersonInstitutionRO.builder()//
				.id(personInstitution.getId().getValue()) //
				.institution(InstitutionMapper.mapToInstitutionRO(personInstitution.getInstitution())) //
				.standort(InstitutionStandortMapper.mapToStandortRO(personInstitution.getStandort())) //
				.build();
	}
}