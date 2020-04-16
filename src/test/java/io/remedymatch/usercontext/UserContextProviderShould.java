package io.remedymatch.usercontext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = { "test", "disableexternaltasks" })
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("UserContextProvider soll")
public class UserContextProviderShould {

	@Autowired
	private UserContextProvider userProvide;

	@Test
	@WithMockJWT(usernameClaim = "myUsername")
	public void username_aus_JWT_zurueckliefern() {

		assertEquals("myUsername", userProvide.getUserName());
	}
}
