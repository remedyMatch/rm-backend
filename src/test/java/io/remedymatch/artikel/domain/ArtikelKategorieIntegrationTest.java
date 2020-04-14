package io.remedymatch.artikel.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.artikel.infrastructure.ArtikelJpaRepository;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieJpaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"test", "disableexternaltasks"})
@Tag("InMemory")
@Disabled
@Tag("SpringBoot")
public class ArtikelKategorieIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArtikelJpaRepository artikelJpaRepository;

    @Autowired
    private ArtikelKategorieJpaRepository artikelKategorieRepository;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    @Transactional
    public void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        artikelJpaRepository.deleteAll();
        artikelKategorieRepository.deleteAll();
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "myUsername")
    public void shouldReadAlleKategorien() throws Exception {
        artikelKategorieRepository.save(ArtikelKategorieEntity.builder().name("sample").build());
        mockMvc.perform(get("/artikelkategorie")
                .contentType("application/json")
                .accept("application/json")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is(("sample"))))
                .andReturn();
    }
}
