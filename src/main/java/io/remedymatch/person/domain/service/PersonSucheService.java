package io.remedymatch.person.domain.service;

import static io.remedymatch.person.domain.service.PersonEntityConverter.convert;

import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Validated
@Service
@Slf4j
public class PersonSucheService {

	private static final String EXCEPTION_MSG_PERSON_NICHT_GEFUNDEN = "Person mit diesem Id nicht gefunden. (Id: %s)";
	private static final String EXCEPTION_MSG_USERNAME_NICHT_GEFUNDEN = "Person mit diesem Username nicht gefunden. (Username: %s)";

	private final PersonJpaRepository jpaRepository;

	@PersistenceContext
	private final EntityManager entityManager;

	/**
	 * In Vergleich zur findPerson wirft eine ObjectNotFoundException wenn nicht
	 * gefunden
	 * 
	 * @param personId PersonId
	 * @return Person
	 * @throws ObjectNotFoundException
	 */
	@Transactional(readOnly = true)
	public Person getByPersonId(final @NotNull @Valid PersonId personId) throws ObjectNotFoundException {

		log.info("Get Person: " + personId);

		try {
			return detachAndConvert(() -> jpaRepository.getOne(personId.getValue()));
		} catch (EntityNotFoundException e) {
			throw new ObjectNotFoundException(String.format(EXCEPTION_MSG_PERSON_NICHT_GEFUNDEN, personId.getValue()));
		}
	}

	@Transactional(readOnly = true)
	public Person getByUsername(final @NotBlank String username) {

		log.info("Get Person fuer username: " + username);

		return detachAndConvert(() -> jpaRepository.findOneByUsername(username).orElseThrow(
				() -> new ObjectNotFoundException(String.format(EXCEPTION_MSG_USERNAME_NICHT_GEFUNDEN, username))));
	}

	private Person detachAndConvert(Supplier<PersonEntity> supplier) {
		PersonEntity personEntity = supplier.get();
		entityManager.detach(personEntity);
		return convert(personEntity);
	}
}
