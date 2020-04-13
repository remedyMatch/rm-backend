package io.remedymatch.person.domain;

import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionStandortEntityConverter;
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
				.email(entity.getEmail()) //
				.telefon(entity.getTelefon()) //
				.institution(InstitutionEntityConverter.convertInstitution(entity.getInstitution())) //
				.standort(InstitutionStandortEntityConverter.convertStandort(entity.getStandort())) //
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

		return builder //
				.username(person.getUsername()) //
				.vorname(person.getVorname()) //
				.nachname(person.getNachname()) //
				.email(person.getEmail()) //
				.telefon(person.getTelefon()) //
				.institution(InstitutionEntityConverter.convertInstitution(person.getInstitution())) //
				.standort(InstitutionStandortEntityConverter.convertStandort(person.getStandort())) //
				.build();
	}
}