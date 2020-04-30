package io.remedymatch.person.domain.service;

import static io.remedymatch.person.domain.service.PersonEntityConverter.convertPerson;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionSucheService;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.person.domain.model.NeuePerson;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Validated
@Service
@Transactional
public class PersonService {

	private static final String EXCEPTION_MSG_INSTITUTION_NICHT_GEFUNDEN = "Institution mit diesem Id nicht gefunden. (Id: %s)";
	private static final String EXCEPTION_MSG_STANDORT_NICHT_DER_INSTITUTION = "Standort gehoert nicht der Institution. (InstitutionId: %s, StandortId: %s)";

	private final InstitutionSucheService institutionSucheService;
	private final PersonJpaRepository personRepository;

	public Person personAnlegen(final @NotNull @Valid NeuePerson neuePerson) {
		val institution = getInstitution(neuePerson.getInstitutionId());
		val standort = getInstitutionStandort(institution, neuePerson.getStandortId());

		PersonEntity personEntity = personRepository.save(PersonEntity.builder() //
				.username(neuePerson.getUsername()) //
				.vorname(neuePerson.getVorname()) //
				.nachname(neuePerson.getNachname()) //
				.email(neuePerson.getEmail()) //
				.telefon(neuePerson.getTelefon()) //
				.build());
		personEntity.addNeueAktuelleInstitution(institution, standort);

		return convertPerson(personRepository.save(personEntity));
	}

	InstitutionEntity getInstitution(final @NotNull @Valid InstitutionId institutionId) {
		return institutionSucheService.findInstitution(institutionId)
				.map(InstitutionEntityConverter::convertInstitution)//
				.orElseThrow(() -> new ObjectNotFoundException(
						String.format(EXCEPTION_MSG_INSTITUTION_NICHT_GEFUNDEN, institutionId.getValue())));
	}

	InstitutionStandortEntity getInstitutionStandort(//
			final @NotNull @Valid InstitutionEntity institution, //
			final @NotNull @Valid InstitutionStandortId standortId) {
		return institution.findStandort(standortId.getValue()).orElseThrow(() -> new ObjectNotFoundException(String
				.format(EXCEPTION_MSG_STANDORT_NICHT_DER_INSTITUTION, institution.getId(), standortId.getValue())));
	}
}
