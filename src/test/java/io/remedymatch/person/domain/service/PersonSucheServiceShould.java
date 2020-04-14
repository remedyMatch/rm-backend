package io.remedymatch.person.domain.service;

import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPerson;
import static io.remedymatch.person.domain.service.PersonTestFixtures.beispielPersonEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class PersonSucheServiceShould {

	@Autowired
	private PersonSucheService personSucheService;

	@MockBean
	private PersonJpaRepository personRepository;

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
