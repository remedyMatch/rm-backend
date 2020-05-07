package io.remedymatch.person.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionStandortEntityConverter;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.domain.model.PersonStandort;
import io.remedymatch.person.domain.model.PersonStandortId;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonStandortEntity;

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
				.aktuellesStandort(convertStandort(entity.getAktuellesStandort())) //
				.standorte(convertStandorte(entity.getStandorte())) //
				.build();
	}

	private static List<PersonStandort> convertStandorte(final List<PersonStandortEntity> entities) {
		return entities.stream().map(PersonEntityConverter::convertStandort).collect(Collectors.toList());
	}

	private static PersonStandort convertStandort(final PersonStandortEntity entity) {
		return PersonStandort.builder()//
				.id(new PersonStandortId(entity.getId())) //
				.institution(InstitutionEntityConverter.convertInstitution(entity.getInstitution())) //
				.standort(InstitutionStandortEntityConverter.convertStandort(entity.getStandort())) //
				.oeffentlich(entity.isOeffentlich()) //
				.build();
	}
}