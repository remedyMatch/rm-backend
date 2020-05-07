package io.remedymatch.person.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.institution.controller.InstitutionMapper;
import io.remedymatch.institution.controller.InstitutionStandortMapper;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.person.domain.model.NeuesPersonStandort;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonStandort;
import io.remedymatch.person.domain.model.PersonStandortId;
import io.remedymatch.person.domain.model.PersonUpdate;

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
				.aktuellesStandort(mapStandortToRO(person.getAktuellesStandort())) //
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
				.oefentlich(personStandort.isOeffentlich()) //
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
}