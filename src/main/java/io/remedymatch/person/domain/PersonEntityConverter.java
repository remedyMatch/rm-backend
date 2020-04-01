package io.remedymatch.person.domain;

import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonEntity.PersonEntityBuilder;

class PersonEntityConverter {

	static Person convert(final PersonEntity entity) {
		if (entity == null) {
			return null;
		}

		return Person.builder()//
				.id(new PersonId(entity.getId())) //
				.username(entity.getUsername()) //
				.vorname(entity.getVorname()) //
				.nachname(entity.getNachname()) //
				.telefon(entity.getTelefon()) //
				.institution(InstitutionEntityConverter.convert(entity.getInstitution())) //
				.build();
	}

	static PersonEntity convert(final Person person) {
		if (person == null) {
			return null;
		}

		PersonEntityBuilder builder = PersonEntity.builder();
		if (person.getId() != null) {
			builder.id(person.getId().getValue());
		}

		return builder.username(person.getUsername()) //
				.vorname(person.getVorname()) //
				.nachname(person.getNachname()) //
				.telefon(person.getTelefon()) //
				.institution(InstitutionEntityConverter.convert(person.getInstitution())) //
				.build();
	}
}