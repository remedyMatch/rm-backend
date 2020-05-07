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
import io.remedymatch.person.domain.model.NeuesPersonStandort;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.domain.model.PersonUpdate;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Validated
@Service
@Transactional
@Log4j2
public class PersonService {

	private static final String EXCEPTION_MSG_PERSON_NICHT_GEFUNDEN = "Person mit dieser Id nicht gefunden. (Id: %s)";
	private static final String EXCEPTION_MSG_PERSON_STANDORT_NICHT_GEFUNDEN = "Person Standort nicht gefunden. (PersonId: %s, PersonStandortId: %s)";
	private static final String EXCEPTION_MSG_INSTITUTION_NICHT_IN_PERSON_STANDORTE_GEFUNDEN = "Institution nicht in Person Standorte gefunden. (PersonId: %s, InstitutionId: %s)";
	private static final String EXCEPTION_MSG_STANDORT_NICHT_DER_INSTITUTION = "Standort gehoert nicht der Institution. (InstitutionId: %s, StandortId: %s)";

	private final PersonJpaRepository personRepository;
	private final InstitutionSucheService institutionSucheService;

	private final UserContextService userService;

	public Person personAnlegen(final @NotNull @Valid NeuePerson neuePerson) {
		val institution = getInstitution(neuePerson.getInstitutionId());
		val standort = getInstitutionStandort(institution, neuePerson.getStandortId());

		PersonEntity person = personRepository.save(PersonEntity.builder() //
				.username(neuePerson.getUsername()) //
				.vorname(neuePerson.getVorname()) //
				.nachname(neuePerson.getNachname()) //
				.email(neuePerson.getEmail()) //
				.telefon(neuePerson.getTelefon()) //
				.build());
		person.addNeuesAktuellesStandort(institution, standort, neuePerson.isStandortOeffentlich());

		return convertPerson(personRepository.save(person));
	}

	public Person userAktualisieren(final @NotNull @Valid PersonUpdate personUpdate) {
		val person = getPerson(userService.getContextUserId());

		if (personUpdate.getAktuellesStandortId() != null) {
			val standort = person.getStandorte().stream()//
					.filter(standort -> standort.getId().equals(personUpdate.getAktuellesStandortId().getValue())) //
					.findAny()
					.orElseThrow(() -> new ObjectNotFoundException(
							String.format(EXCEPTION_MSG_PERSON_STANDORT_NICHT_GEFUNDEN, person.getId(),
									personUpdate.getAktuellesStandortId().getValue())));
			person.setAktuellesStandort(standort);

			log.info("Aktuelles Person Standort geändert auf: " + personUpdate.getAktuellesStandortId().getValue());
		}

		return convertPerson(personRepository.save(person));
	}

	public Person userStandortHinzufuegen(final @NotNull @Valid NeuesPersonStandort neuesStandort) {
		val person = getPerson(userService.getContextUserId());

		// nur die neue Standorte bereits vorhandenen Institutionen duerfen hinzugefuegt
		// werden
		// in neue Institutionen muss User entweder eingeladen werden oder diese
		// beantragen
		val institution = getPersonInstitution(person, neuesStandort.getInstitutionId());
		val standort = getInstitutionStandort(institution, neuesStandort.getStandortId());

		person.addNeuesAktuellesStandort(institution, standort, neuesStandort.isOeffentlich());

		log.info("Neues Person Standort hinzugefügt: " + neuesStandort);

		return convertPerson(personRepository.save(person));
	}

	public void neueInstitutionZuweisen(//
			final @NotNull @Valid PersonId personId, //
			final @NotNull @Valid InstitutionId institutionId, //
			final boolean standortOeffentlich) {
		val person = getPerson(personId);
		val institution = getInstitution(institutionId);

		person.addNeuesStandort(institution, institution.getHauptstandort(), standortOeffentlich);
		personRepository.save(person);
	}

	PersonEntity getPerson(final @NotNull @Valid PersonId personId) {
		return personRepository.findById(personId.getValue()).orElseThrow(() -> new ObjectNotFoundException(
				String.format(EXCEPTION_MSG_PERSON_NICHT_GEFUNDEN, personId.getValue())));
	}

	InstitutionEntity getInstitution(final @NotNull @Valid InstitutionId institutionId) {
		return InstitutionEntityConverter.convertInstitution(institutionSucheService.getByInstitutionId(institutionId));
	}

	InstitutionEntity getPersonInstitution(final PersonEntity person, final InstitutionId institutionId) {
		return person.getStandorte().stream() //
				.filter(personStandort -> personStandort.getInstitution().getId().equals(institutionId.getValue())) //
				.map(personStandort -> personStandort.getInstitution()) //
				.findAny() //
				.orElseThrow(() -> new ObjectNotFoundException(
						String.format(EXCEPTION_MSG_INSTITUTION_NICHT_IN_PERSON_STANDORTE_GEFUNDEN, person.getId(),
								institutionId.getValue())));
	}

	InstitutionStandortEntity getInstitutionStandort(//
			final @NotNull @Valid InstitutionEntity institution, //
			final @NotNull @Valid InstitutionStandortId standortId) {
		return institution.findStandort(standortId.getValue()).orElseThrow(() -> new ObjectNotFoundException(String
				.format(EXCEPTION_MSG_STANDORT_NICHT_DER_INSTITUTION, institution.getId(), standortId.getValue())));
	}
}
