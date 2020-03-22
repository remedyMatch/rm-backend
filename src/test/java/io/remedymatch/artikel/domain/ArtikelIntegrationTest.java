package io.remedymatch.artikel.domain;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.remedymatch.RmBeApplication;
import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.artikel.api.ArtikelKategorieDTO;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RmBeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Tag("InMemory")
@Disabled
public class ArtikelIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArtikelJpaRepository artikelJpaRepository;

    @Autowired
    private ArtikelKategorieRepository artikelKategorieRepository;

    @BeforeEach
    @Transactional
    public void beforeEach() {
        artikelJpaRepository.deleteAll();
        artikelKategorieRepository.deleteAll();
    }

    @Test
    public void shouldAddArtike() throws Exception {

        ArtikelKategorieDTO kategorieDTO = ArtikelKategorieDTO.builder()
                .id(UUID.randomUUID())
                .name("shouldAddArtikel")
                .build();
        MvcResult savedKategorieResult = mockMvc.perform(post("/remedy/artikelkategorie")
                .contentType("application/json")
                .accept("application/json")
                .content(objectMapper.writeValueAsString(kategorieDTO))
        ).andReturn();
        assertThat( savedKategorieResult.getResponse().getStatus(), CoreMatchers.is(200));
        ArtikelKategorieDTO savedKategorieDTO = objectMapper.readValue(savedKategorieResult.getResponse().getContentAsString(), ArtikelKategorieDTO.class);

        MvcResult postArtikelResult = mockMvc.perform(post("/remedy/")
                .contentType("application/json")
                .accept("application/json")
                .content(
                        objectMapper.writeValueAsString(
                                ArtikelDTO.builder()
                                        .beschreibung("Testartikel-shouldAddArtikel")
                                        .ean("ean-shouldAddArtikel")
                                        .hersteller("hersteller-shouldAddArtikel")
                                        .name("shouldAddArtikel")
                                        .artikelKategorie(savedKategorieDTO)
                                        .build()
                        )
                )).andReturn();

        assertThat(postArtikelResult.getResponse().getStatus(), CoreMatchers.is(200));
        ArtikelDTO artikelDTO = objectMapper.readValue(postArtikelResult.getResponse().getContentAsString(), ArtikelDTO.class);
        assertThat(artikelDTO, notNullValue());
        assertThat(artikelDTO.getId(), notNullValue());
        assertThat(artikelDTO.getName(), CoreMatchers.is("shouldAddArtikel"));
        assertThat(artikelDTO.getEan(), CoreMatchers.is("ean-shouldAddArtikel"));
        assertThat(artikelDTO.getHersteller(), CoreMatchers.is("hersteller-shouldAddArtikel"));
        assertThat(artikelDTO.getBeschreibung(), CoreMatchers.is("Testartikel-shouldAddArtikel"));
        assertThat(artikelDTO.getArtikelKategorie().getId(), CoreMatchers.is(savedKategorieDTO.getId()));
    }
}
