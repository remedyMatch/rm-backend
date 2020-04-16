package io.remedymatch.usercontext;

import static io.remedymatch.usercontext.UserContextTestFixtures.beispielUserContextPerson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.person.domain.service.PersonSucheService;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		UserContextService.class, //
		UserContextProvider.class, //
		PersonSucheService.class //
})
@Tag("Spring")
@DisplayName("UserContextService soll")
public class UserContextServiceShould {

	@Autowired
	private UserContextService userContextService;

	@MockBean
	private UserContextProvider userProviced;

	@MockBean
	private PersonSucheService personSucheService;

	@Test
	@DisplayName("Context Person zurueckliefern")
	void context_Person_zurueckliefern() {

		val username = "sample_username";
		val userContextPerson = beispielUserContextPerson();

		given(userProviced.getUserName()).willReturn(username);
		given(personSucheService.findByUsername(username)).willReturn(Optional.of(userContextPerson));

		assertEquals(userContextPerson, userContextService.getContextUser());

		then(userProviced).should().getUserName();
		then(userProviced).shouldHaveNoMoreInteractions();
		then(personSucheService).should().findByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("Context Institution zurueckliefern")
	void context_Institution_zurueckliefern() {

		val username = "sample_username";
		val userContextPerson = beispielUserContextPerson();

		given(userProviced.getUserName()).willReturn(username);
		given(personSucheService.findByUsername(username)).willReturn(Optional.of(userContextPerson));

		assertEquals(userContextPerson.getInstitution(), userContextService.getContextInstitution());

		then(userProviced).should().getUserName();
		then(userProviced).shouldHaveNoMoreInteractions();
		then(personSucheService).should().findByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("Context Institution zurueckliefern")
	void context_InstitutionId_zurueckliefern() {

		val username = "sample_username";
		val userContextPerson = beispielUserContextPerson();

		given(userProviced.getUserName()).willReturn(username);
		given(personSucheService.findByUsername(username)).willReturn(Optional.of(userContextPerson));

		assertEquals(userContextPerson.getInstitution().getId(), userContextService.getContextInstitutionId());

		then(userProviced).should().getUserName();
		then(userProviced).shouldHaveNoMoreInteractions();
		then(personSucheService).should().findByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("true zurueckliefern, wenn die angefragte Institution UserContext Institution ist")
	void true_zurueckliefern_wenn_die_angefragte_Institution_UserContext_Institution_ist() {

		val username = "sample_username";
		val userContextPerson = beispielUserContextPerson();

		given(userProviced.getUserName()).willReturn(username);
		given(personSucheService.findByUsername(username)).willReturn(Optional.of(userContextPerson));

		assertTrue(userContextService.isUserContextInstitution(userContextPerson.getInstitution().getId()));

		then(userProviced).should().getUserName();
		then(userProviced).shouldHaveNoMoreInteractions();
		then(personSucheService).should().findByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("false zurueckliefern, wenn die angefragte Institution nicht UserContext Institution ist")
	void false_zurueckliefern_wenn_die_angefragte_Institution_nicht_UserContext_Institution_ist() {

		val username = "sample_username";
		val userContextPerson = beispielUserContextPerson();

		given(userProviced.getUserName()).willReturn(username);
		given(personSucheService.findByUsername(username)).willReturn(Optional.of(userContextPerson));

		assertFalse(userContextService.isUserContextInstitution(new InstitutionId(UUID.randomUUID())));

		then(userProviced).should().getUserName();
		then(userProviced).shouldHaveNoMoreInteractions();
		then(personSucheService).should().findByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}
}
