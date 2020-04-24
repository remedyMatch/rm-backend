package io.remedymatch.bedarf.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.usercontext.TestUserContext;
import lombok.val;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = { "dbinit", "test", "disableexternaltasks" })
@DirtiesContext
@Tag("InMemory")
@Tag("SpringBoot")
public class NeuesBedarfShould extends BedarfControllerTestBasis {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void prepare() {

		prepareBedarfEntities();
	}

	@AfterEach
	void clear() {
		TestUserContext.clear();
	}

	@Test
	@Transactional
	@WithMockJWT(groupsClaim = { "testgroup" }, usernameClaim = SPENDER_USERNAME)
	public void neue_Bedarf_anlegen() throws Exception {

		TestUserContext.setContextUser(spender);

		val artikelVariante = artikelRepository.findAll().get(0).getVarianten().get(0);

		val neuesBedarf = NeuesBedarfRequest.builder() //
				.artikelVarianteId(artikelVariante.getId()) //
				.anzahl(BigDecimal.valueOf(1000)) //
				.standortId(spender.getStandort().getId().getValue()) //
				.kommentar("ITest Bedarf Kommentar") //
				.steril(true) //
				.build();

		MvcResult result = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
				.perform(MockMvcRequestBuilders //
						.post("/bedarf") //
						.content(objectMapper.writeValueAsString(neuesBedarf)).contentType(MediaType.APPLICATION_JSON) //
						.accept(MediaType.APPLICATION_JSON)) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andExpect(MockMvcResultMatchers.jsonPath("$.anzahl").value(BigDecimal.valueOf(1000))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty()) //
				.andReturn();

		BedarfRO bedarf = objectMapper.readValue(result.getResponse().getContentAsString(), BedarfRO.class);

		assertEquals("ITest Bedarf Kommentar", bedarf.getKommentar());
	}
}
