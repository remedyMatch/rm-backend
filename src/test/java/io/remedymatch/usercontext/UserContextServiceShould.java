package io.remedymatch.usercontext;

import static io.remedymatch.usercontext.UserContextTestFixtures.beispielUserContextPerson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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

import io.remedymatch.institution.domain.model.Institution;
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
	private UserContextProvider userContextProvider;

	@MockBean
	private PersonSucheService personSucheService;

	@Test
	@DisplayName("Context Person zurueckliefern")
	void context_Person_zurueckliefern() {

		val username = "sample_username";
		val userContextPerson = beispielUserContextPerson();

		given(userContextProvider.getUserName()).willReturn(username);
		given(personSucheService.getByUsername(username)).willReturn(userContextPerson);

		assertEquals(userContextPerson, userContextService.getContextUser());

		then(userContextProvider).should().getUserName();
		then(userContextProvider).shouldHaveNoMoreInteractions();
		then(personSucheService).should().getByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("Context PersonId zurueckliefern")
	void context_PersonId_zurueckliefern() {

		val username = "sample_username";
		val userContextPerson = beispielUserContextPerson();

		given(userContextProvider.getUserName()).willReturn(username);
		given(personSucheService.getByUsername(username)).willReturn(userContextPerson);

		assertEquals(userContextPerson.getId(), userContextService.getContextUserId());

		then(userContextProvider).should().getUserName();
		then(userContextProvider).shouldHaveNoMoreInteractions();
		then(personSucheService).should().getByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("Context Institution zurueckliefern mit Hauptstandort nicht in Standorten enthalten")
	void context_Institution_zurueckliefern_mit_Hauptstandort_nicht_in_Standorten_enthalten()
			throws CloneNotSupportedException {

		val username = "sample_username";
		val userContextPerson = beispielUserContextPerson();

		given(userContextProvider.getUserName()).willReturn(username);
		given(personSucheService.getByUsername(username)).willReturn(userContextPerson);

		Institution contextInstitution = userContextService.getContextInstitution(true);

		assertFalse(contextInstitution.getStandorte().contains(contextInstitution.getHauptstandort()));

		then(userContextProvider).should().getUserName();
		then(userContextProvider).shouldHaveNoMoreInteractions();
		then(personSucheService).should().getByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("Context Institution zurueckliefern mit Hauptstandort in Standorten enthalten")
	void context_Institution_zurueckliefern_mit_Hauptstandort_in_Standorten_enthalten() {

		val username = "sample_username";
		val beispielUserContextPerson = beispielUserContextPerson();

		given(userContextProvider.getUserName()).willReturn(username);
		given(personSucheService.getByUsername(username)).willReturn(beispielUserContextPerson);
		Institution contextInstitution = userContextService.getContextInstitution();

		assertTrue(contextInstitution.getStandorte().contains(contextInstitution.getHauptstandort()));

		then(userContextProvider).should().getUserName();
		then(userContextProvider).shouldHaveNoMoreInteractions();
		then(personSucheService).should().getByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("Context InstitutionId zurueckliefern")
	void context_InstitutionId_zurueckliefern() {

		val username = "sample_username";
		val userContextPerson = beispielUserContextPerson();

		given(userContextProvider.getUserName()).willReturn(username);
		given(personSucheService.getByUsername(username)).willReturn(userContextPerson);

		assertEquals(userContextPerson.getInstitution().getId(), userContextService.getContextInstitutionId());

		then(userContextProvider).should().getUserName();
		then(userContextProvider).shouldHaveNoMoreInteractions();
		then(personSucheService).should().getByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("true zurueckliefern, wenn die angefragte Institution UserContext Institution ist")
	void true_zurueckliefern_wenn_die_angefragte_Institution_UserContext_Institution_ist() {

		val username = "sample_username";
		val userContextPerson = beispielUserContextPerson();

		given(userContextProvider.getUserName()).willReturn(username);
		given(personSucheService.getByUsername(username)).willReturn(userContextPerson);

		assertTrue(userContextService.isUserContextInstitution(userContextPerson.getInstitution().getId()));

		then(userContextProvider).should().getUserName();
		then(userContextProvider).shouldHaveNoMoreInteractions();
		then(personSucheService).should().getByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("false zurueckliefern, wenn die angefragte Institution nicht UserContext Institution ist")
	void false_zurueckliefern_wenn_die_angefragte_Institution_nicht_UserContext_Institution_ist() {

		val username = "sample_username";
		val userContextPerson = beispielUserContextPerson();

		given(userContextProvider.getUserName()).willReturn(username);
		given(personSucheService.getByUsername(username)).willReturn(userContextPerson);

		assertFalse(userContextService.isUserContextInstitution(new InstitutionId(UUID.randomUUID())));

		then(userContextProvider).should().getUserName();
		then(userContextProvider).shouldHaveNoMoreInteractions();
		then(personSucheService).should().getByUsername(username);
		then(personSucheService).shouldHaveNoMoreInteractions();
	}
}
