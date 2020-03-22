package io.remedymatch.artikel.domain;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.artikel.api.ArtikelKategorieDTO;
import io.remedymatch.artikel.api.ArtikelKategorieMapper;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("InMemory")
@Tag("SpringBoot")
public class ArtikelIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArtikelJpaRepository artikelJpaRepository;

    @Autowired
    private ArtikelKategorieRepository artikelKategorieRepository;

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

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "myUsername")
    public void shouldAddArtike() throws Exception {

        var artikelKategorie = artikelKategorieRepository.save(
                ArtikelKategorieEntity.builder()
                .name("shouldAddArtike")
                .build()
        );

        MvcResult postArtikelResult = mockMvc.perform(post("/artikel")
                .contentType("application/json")
                .accept("application/json")
                .content(
                        objectMapper.writeValueAsString(
                                ArtikelDTO.builder()
                                        .beschreibung("Testartikel-shouldAddArtikel")
                                        .ean("ean-shouldAddArtikel")
                                        .hersteller("hersteller-shouldAddArtikel")
                                        .name("shouldAddArtikel")
                                        .artikelKategorie(ArtikelKategorieMapper.getArtikelKategorieDTO(artikelKategorie))
                                        .build()
                        )
                )).andReturn();

        assertThat(postArtikelResult.getResponse().getStatus(), is(200));
        ArtikelDTO artikelDTO = objectMapper.readValue(postArtikelResult.getResponse().getContentAsString(), ArtikelDTO.class);
        assertThat(artikelDTO, notNullValue());
        assertThat(artikelDTO.getId(), notNullValue());
        assertThat(artikelDTO.getName(), is("shouldAddArtikel"));
        assertThat(artikelDTO.getEan(), is("ean-shouldAddArtikel"));
        assertThat(artikelDTO.getHersteller(), is("hersteller-shouldAddArtikel"));
        assertThat(artikelDTO.getBeschreibung(), is("Testartikel-shouldAddArtikel"));
        assertThat(artikelDTO.getArtikelKategorie().getId(), is(artikelKategorie.getId()));
    }
}
