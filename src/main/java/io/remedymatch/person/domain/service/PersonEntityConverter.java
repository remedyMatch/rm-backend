package io.remedymatch.person.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionStandortEntityConverter;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.domain.model.PersonInstitution;
import io.remedymatch.person.domain.model.PersonInstitutionId;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonInstitutionEntity;

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
				.aktuelleInstitution(convertInstitution(entity.getAktuelleInstitution())) //
				.institutionen(convertInstitutionen(entity.getInstitutionen())) //
				.build();
	}

	private static List<PersonInstitution> convertInstitutionen(final List<PersonInstitutionEntity> entities) {
		return entities.stream().map(PersonEntityConverter::convertInstitution).collect(Collectors.toList());
	}

	private static PersonInstitution convertInstitution(final PersonInstitutionEntity entity) {
		return PersonInstitution.builder()//
				.id(new PersonInstitutionId(entity.getId())) //
				.institution(InstitutionEntityConverter.convertInstitution(entity.getInstitution())) //
				.standort(InstitutionStandortEntityConverter.convertStandort(entity.getStandort())) //
				.build();
	}
}