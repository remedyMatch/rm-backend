package io.remedymatch.bedarf.controller;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = { "dbinit", "test", "disableexternaltasks" })
@DirtiesContext
@Tag("InMemory")
@Tag("SpringBoot")
public class BedarfFiltersShould extends BedarfControllerTestBasis {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void prepare() {

		prepareBedarfEntities();
	}

	@Test
	@Transactional
	@WithMockJWT(groupsClaim = { "testgroup" }, usernameClaim = SPENDER_USERNAME)
	public void alle_artikel_filter_lesen() throws Exception {

		MvcResult result = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
				.perform(MockMvcRequestBuilders //
						.get("/bedarf/suche/filter/artikelkategorie") //
						.contentType(MediaType.APPLICATION_JSON) //
						.accept(MediaType.APPLICATION_JSON)) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$", hasSize(1))) //
				.andExpect(jsonPath("$[0].id", not(emptyString()))) //
				.andExpect(jsonPath("$[0].anzahl").value(BigInteger.valueOf(1))) //
				.andReturn();

		List<BedarfFilterEntryRO> kategorieFilter = objectMapper.readValue(result.getResponse().getContentAsString(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, BedarfFilterEntryRO.class));

		result = MockMvcBuilders.webAppContextSetup(webApplicationContext).build().perform(MockMvcRequestBuilders //
				.get("/bedarf/suche/filter/artikel") //
				.queryParam("artikelKategorieId", kategorieFilter.get(0).getId().toString()) //
				.contentType(MediaType.APPLICATION_JSON) //
				.accept(MediaType.APPLICATION_JSON)) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$", hasSize(1))) //
				.andExpect(jsonPath("$[0].id", not(emptyString()))) //
				.andExpect(jsonPath("$[0].anzahl").value(BigInteger.valueOf(1))) //
				.andReturn();

		List<BedarfFilterEntryRO> artikelFilter = objectMapper.readValue(result.getResponse().getContentAsString(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, BedarfFilterEntryRO.class));

		MockMvcBuilders.webAppContextSetup(webApplicationContext).build().perform(MockMvcRequestBuilders //
				.get("/bedarf/suche/filter/artikelvariante") //
				.queryParam("artikelId", artikelFilter.get(0).getId().toString()) //
				.contentType(MediaType.APPLICATION_JSON) //
				.accept(MediaType.APPLICATION_JSON)) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$", hasSize(1))) //
				.andExpect(jsonPath("$[0].id", not(emptyString()))) //
				.andExpect(jsonPath("$[0].anzahl").value(BigInteger.valueOf(1))) //
				.andReturn();
	}
}
