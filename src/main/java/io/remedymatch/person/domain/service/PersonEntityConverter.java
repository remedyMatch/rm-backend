package io.remedymatch.person.domain.service;

import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionStandortEntityConverter;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.infrastructure.PersonEntity;

class PersonEntityConverter {
	private PersonEntityConverter() {
		
	}

	static Person convertPerson(final PersonEntity entity) {
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
}