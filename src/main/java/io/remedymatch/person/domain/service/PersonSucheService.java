package io.remedymatch.person.domain.service;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Validated
@Service
public class PersonSucheService {

	private static final String EXCEPTION_MSG_PERSON_NICHT_GEFUNDEN = "Person mit diesem Id nicht gefunden. (Id: %s)";

	private final PersonJpaRepository jpaRepository;

	/**
	 * In Vergleich zur findPerson wirft eine ObjectNotFoundException wenn nicht
	 * gefunden
	 * 
	 * @param personId PersonId
	 * @return Person
	 * @throws ObjectNotFoundException
	 */
	@Transactional(readOnly = true)
	public Person getPersonOrElseThrow(final @NotNull @Valid PersonId personId) throws ObjectNotFoundException {
		return findPerson(personId).orElseThrow(() -> new ObjectNotFoundException(
				String.format(EXCEPTION_MSG_PERSON_NICHT_GEFUNDEN, personId.getValue())));
	}

	@Transactional(readOnly = true)
	public Optional<Person> findPerson(final @NotNull @Valid PersonId personId) {
		Assert.notNull(personId, "PersonId ist null.");

		return jpaRepository.findById(personId.getValue()).map(PersonEntityConverter::convert);
	}

	@Transactional(readOnly = true)
	public Optional<Person> findByUsername(final @NotBlank String username) {
		return jpaRepository.findByUsername(username).map(PersonEntityConverter::convert);
	}
}
