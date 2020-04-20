package io.remedymatch.person.domain.service;

import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPerson;
import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPersonEntity;
import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPersonId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		PersonSucheService.class, //
		PersonJpaRepository.class, //
		EntityManager.class //
})
@Tag("Spring")
@DisplayName("PersonSucheService soll")
class PersonSucheServiceShould {

	@Autowired
	private PersonSucheService personSucheService;

	@MockBean
	private PersonJpaRepository personRepository;

	@MockBean
	private EntityManager entityManager;
	
	@Test
	@DisplayName("eine ObjectNotFoundException werfen wenn gesuchte Person mit diesem PersonId nicht existiert")
	void eine_ObjectNotFoundException_werfen_wenn_gesuchte_Person_mit_diesem_PersonId_nicht_existiert() {

		val personId = beispielPersonId();

		given(personRepository.getOne(personId.getValue())).willThrow(new EntityNotFoundException());

		assertThrows(ObjectNotFoundException.class, () -> personSucheService.getByPersonId(beispielPersonId()));
	}

	@Test
	@DisplayName("gesuchte Person fuer PersonId zurueckliefern")
	void gesuchte_Person_fuer_PersonId_zurueckliefern() {

		val personId = beispielPersonId();
		val personEntity = beispielPersonEntity();

		given(personRepository.getOne(personId.getValue())).willReturn(personEntity);

		val expectedPerson = beispielPerson();

		assertEquals(expectedPerson, personSucheService.getByPersonId(personId));

		then(personRepository).should().findById(personId.getValue());
		then(personRepository).shouldHaveNoMoreInteractions();
		then(entityManager).should().detach(personEntity);
		then(entityManager).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("eine ObjectNotFoundException werfen wenn gesuchte Person mit diesem Username nicht existiert")
	void eine_ObjectNotFoundException_werfen_wenn_gesuchte_Person_mit_diesem_Username_nicht_existiert() {

		given(personRepository.findOneByUsername("username")).willThrow(new EntityNotFoundException());

		assertThrows(ObjectNotFoundException.class, () -> personSucheService.getByUsername("username"));
	}

	@Test
	@DisplayName("gesuchte Person fuer Username zurueckliefern")
	void gesuchte_Person_fuer_Username_finden() {

		val personEntity = beispielPersonEntity();
		val username = personEntity.getUsername();

		given(personRepository.findOneByUsername(username)).willReturn(Optional.of(personEntity));

		val expectedPerson = beispielPerson();

		assertEquals(expectedPerson, personSucheService.getByUsername(username));

		then(personRepository).should().findOneByUsername(username);
		then(personRepository).shouldHaveNoMoreInteractions();
		then(entityManager).should().detach(personEntity);
		then(entityManager).shouldHaveNoMoreInteractions();
	}
}
