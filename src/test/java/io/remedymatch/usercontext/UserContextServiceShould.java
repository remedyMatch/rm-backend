package io.remedymatch.usercontext;

import static io.remedymatch.usercontext.UserContextTestFixtures.beispielUserContextPerson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.person.domain.model.Person;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		UserContextService.class //
})
@Tag("Spring")
@Disabled
@DisplayName("UserContextService soll")
public class UserContextServiceShould {

	@Autowired
	private UserContextService userContextService;

	private Person userContextPerson;

	@BeforeEach
	void prepare() {
		userContextPerson = beispielUserContextPerson();
		UserContext.setContextUser(userContextPerson);
	}

	@AfterEach
	void clear() {
		UserContext.clear();
	}

	@Test
	@DisplayName("Context Person zurueckliefern")
	void context_Person_zurueckliefern() {

		assertEquals(userContextPerson, userContextService.getContextUser());
	}

	@Test
	@DisplayName("Context PersonId zurueckliefern")
	void context_PersonId_zurueckliefern() {

		assertEquals(userContextPerson.getId(), userContextService.getContextUserId());
	}

	@Test
	@DisplayName("Context Institution zurueckliefern")
	void context_Institution_zurueckliefern() {

		assertEquals(userContextPerson, userContextService.getContextInstitution());
	}

	@Test
	@DisplayName("Context InstitutionId zurueckliefern")
	void context_InstitutionId_zurueckliefern() {

		assertEquals(userContextPerson.getAktuelleInstitution().getId(), userContextService.getContextInstitutionId());
	}

	@Test
	@DisplayName("true zurueckliefern, wenn die angefragte Institution UserContext Institution ist")
	void true_zurueckliefern_wenn_die_angefragte_Institution_UserContext_Institution_ist() {

		assertTrue(userContextService
				.isUserContextInstitution(userContextPerson.getAktuelleInstitution().getInstitution().getId()));
	}

	@Test
	@DisplayName("false zurueckliefern, wenn die angefragte Institution nicht UserContext Institution ist")
	void false_zurueckliefern_wenn_die_angefragte_Institution_nicht_UserContext_Institution_ist() {

		assertFalse(userContextService.isUserContextInstitution(new InstitutionId(UUID.randomUUID())));
	}
}
