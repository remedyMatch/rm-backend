package io.remedymatch.person.domain.service;

import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPerson;
import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPersonEntity;
import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPersonId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;

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
		PersonJpaRepository.class //
})
@Tag("Spring")
@DisplayName("PersonSucheService soll")
class PersonSucheServiceShould {

	@Autowired
	private PersonSucheService personSucheService;

	@MockBean
	private PersonJpaRepository personRepository;

	@Test
	@DisplayName("gesuchte Person finden")
	void gesuchtes_Artikel_finden() {

		val personId = beispielPersonId();
		val personEntity = beispielPersonEntity();

		given(personRepository.findById(personId.getValue())).willReturn(Optional.of(personEntity));

		val expectedPerson = beispielPerson();

		assertEquals(Optional.of(expectedPerson), personSucheService.findPerson(personId));

		then(personRepository).should().findById(personId.getValue());
		then(personRepository).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("eine ObjectNotFoundException werfen wenn gesuchte Person nicht existiert")
	void eine_ObjectNotFoundException_werfen_wenn_gesuchtes_Artikel_nicht_existiert() {
		assertThrows(ObjectNotFoundException.class, () -> personSucheService.getPersonOrElseThrow(beispielPersonId()));
	}

	@Test
	@DisplayName("gesuchte Person zurueckliefern")
	void gesuchte_Person_zurueckliefern() {

		val personId = beispielPersonId();
		val personEntity = beispielPersonEntity();

		given(personRepository.findById(personId.getValue())).willReturn(Optional.of(personEntity));

		val expectedPerson = beispielPerson();

		assertEquals(expectedPerson, personSucheService.getPersonOrElseThrow(personId));

		then(personRepository).should().findById(personId.getValue());
		then(personRepository).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("gesuchte Person fuer Username finden")
	void gesuchte_Person_fuer_Username_finden() {

		val personEntity = beispielPersonEntity();
		val username = personEntity.getUsername();

		given(personRepository.findByUsername(username)).willReturn(Optional.of(personEntity));

		val expectedPerson = beispielPerson();

		assertEquals(Optional.of(expectedPerson), personSucheService.findByUsername(username));

		then(personRepository).should().findByUsername(username);
		then(personRepository).shouldHaveNoMoreInteractions();
	}
}
