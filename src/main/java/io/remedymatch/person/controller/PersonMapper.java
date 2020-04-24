package io.remedymatch.person.controller;

import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;

class PersonMapper {
	private PersonMapper() {
	}

	static PersonRO mapToPersonRO(Person person) {
		if (person == null) {
			return null;
		}

		return PersonRO.builder() //
				.id(person.getId().getValue()) //
				.username(person.getUsername()) //
				.vorname(person.getVorname()) //
				.nachname(person.getNachname()) //
				.email(person.getEmail()) //
				.telefon(person.getTelefon()) //
				.build();
	}

	static Person mapToPerson(PersonRO ro) {
		if (ro == null) {
			return null;
		}

		return Person.builder() //
				.id(new PersonId(ro.getId())) //
				.username(ro.getUsername()) //
				.vorname(ro.getVorname()) //
				.nachname(ro.getNachname()) //
				.email(ro.getEmail()) //
				.telefon(ro.getTelefon()) //
				.build();
	}
}