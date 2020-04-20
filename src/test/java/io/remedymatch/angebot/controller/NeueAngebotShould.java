package io.remedymatch.angebot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
import lombok.val;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = { "dbinit", "test", "disableexternaltasks" })
@Tag("InMemory")
@Tag("SpringBoot")
public class NeueAngebotShould extends AngebotControllerTestBasis {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void prepare() {

		prepareAngebotEntities();
	}

	@Test
	@Transactional
	@WithMockJWT(groupsClaim = { "testgroup" }, usernameClaim = SPENDER_USERNAME)
	public void neue_Angebot_anlegen() throws Exception {

		val artikelVariante = artikelRepository.findAll().get(0).getVarianten().get(0);

		val neuesAngebot = NeuesAngebotRequest.builder() //
				.artikelVarianteId(artikelVariante.getId()) //
				.anzahl(BigDecimal.valueOf(1000)) //
				.standortId(spender.getStandort().getId().getValue()) //
				.kommentar("ITest Angebot Kommentar") //
				.haltbarkeit(LocalDateTime.of(2020, 12, 24, 18, 0)) //
				.steril(true) //
				.build();

		MvcResult result = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
				.perform(MockMvcRequestBuilders //
						.post("/angebot") //
						.content(objectMapper.writeValueAsString(neuesAngebot)).contentType(MediaType.APPLICATION_JSON) //
						.accept(MediaType.APPLICATION_JSON)) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andExpect(MockMvcResultMatchers.jsonPath("$.anzahl").value(BigDecimal.valueOf(1000))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty()) //
				.andReturn();

		AngebotRO angebot = objectMapper.readValue(result.getResponse().getContentAsString(), AngebotRO.class);

		assertEquals("ITest Angebot Kommentar", angebot.getKommentar());
	}
}
