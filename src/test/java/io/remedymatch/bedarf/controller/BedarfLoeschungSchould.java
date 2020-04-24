package io.remedymatch.bedarf.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.usercontext.TestUserContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = { "dbinit", "test", "disableexternaltasks" })
@DirtiesContext
@Tag("InMemory")
@Tag("SpringBoot")
public class BedarfLoeschungSchould extends BedarfControllerTestBasis {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;

	@BeforeEach
	void prepare() {

		prepareBedarfEntities();

		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@AfterEach
	void clear() {
		TestUserContext.clear();
	}

	@Test
	@Transactional
	@WithMockJWT(groupsClaim = { "testgroup" }, usernameClaim = SPENDER_USERNAME)
	public void bedarf_loeschen() throws Exception {

		TestUserContext.setContextUser(spender);
		
		mockServer.expect(ExpectedCount.once(), //
				requestTo(new URI("http://localhost:8008/engine/remedy/message/korrelieren"))) //
				.andExpect(method(HttpMethod.POST)) //
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON) //
//		          .body("rest")
				);

		MockMvcBuilders.webAppContextSetup(webApplicationContext).build().perform(MockMvcRequestBuilders //
				.delete("/bedarf/" + bedarfId.getValue()) //
				.contentType(MediaType.APPLICATION_JSON) //
				.accept(MediaType.APPLICATION_JSON)) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andReturn();

		mockServer.verify();
	}
}
