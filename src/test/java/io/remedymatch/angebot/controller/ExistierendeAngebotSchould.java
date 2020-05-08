package io.remedymatch.angebot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.usercontext.TestUserContext;
import lombok.val;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"dbinit", "test", "disableexternaltasks"})
@DirtiesContext
@Tag("InMemory")
@Tag("SpringBoot")
public class ExistierendeAngebotSchould extends AngebotControllerTestBasis {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void prepare() {

        prepareAngebotEntities();

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @AfterEach
    void clear() {
        TestUserContext.clear();
    }

    @Test
    @Transactional
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = SUCHENDER_USERNAME)
    public void angebot_anfragen_koennen() throws Exception {

        TestUserContext.setContextUser(suchender);

        mockServer.expect(ExpectedCount.once(), //
                requestTo(new URI("http://localhost:8008/engine/remedy/message/korrelieren"))) //
                .andExpect(method(HttpMethod.POST)) //
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON) //
//		          .body("rest")
                );

        val neueAngebotAnfrage = AngebotAnfragenRequest.builder() //
                .anzahl(BigDecimal.valueOf(200)) //
                .kommentar("ITest Angebot Anfrage Kommentar") //
                .bedarfId(UUID.randomUUID()) //
                .build();

        MvcResult result = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
                .perform(MockMvcRequestBuilders //
                        .post("/angebot/" + angebotId.getValue() + "/anfrage") //
                        .content(objectMapper.writeValueAsString(neueAngebotAnfrage))//
                        .contentType(MediaType.APPLICATION_JSON) //
                        .accept(MediaType.APPLICATION_JSON)) //
                .andDo(print()) //
                .andExpect(status().isOk()) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.anzahl").value(BigDecimal.valueOf(200))) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty()) //
                .andReturn();

        AngebotAnfrageRO angebotAnfrage = objectMapper.readValue(result.getResponse().getContentAsString(),
                AngebotAnfrageRO.class);

        assertEquals("ITest Angebot Anfrage Kommentar", angebotAnfrage.getKommentar());

        mockServer.verify();
    }
}
