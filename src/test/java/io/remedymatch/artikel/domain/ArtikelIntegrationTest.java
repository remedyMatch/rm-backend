package io.remedymatch.artikel.domain;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.artikel.api.ArtikelKategorieDTO;
import io.remedymatch.artikel.api.ArtikelKategorieMapper;
import io.remedymatch.artikel.api.ArtikelMapper;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
                        objectMapper.writeValueAsString(artikelForTC("shouldAddArtikel", ArtikelKategorieMapper.getArtikelKategorieDTO(artikelKategorie)))
                )).andReturn();

        assertThat(postArtikelResult.getResponse().getStatus(), is(200));
        ArtikelDTO artikelDTO = objectMapper.readValue(postArtikelResult.getResponse().getContentAsString(), ArtikelDTO.class);
        assertThat(artikelDTO, notNullValue());
        assertThat(artikelDTO.getId(), notNullValue());
        assertThat(artikelDTO.getName(), is("shouldAddArtikel-name"));
        assertThat(artikelDTO.getEan(), is("shouldAddArtikel-ean"));
        assertThat(artikelDTO.getHersteller(), is("shouldAddArtikel-hersteller"));
        assertThat(artikelDTO.getBeschreibung(), is("shouldAddArtikel-description"));
        assertThat(artikelDTO.getArtikelKategorie().getId(), is(artikelKategorie.getId()));

        assertThat(artikelJpaRepository.findById(artikelDTO.getId()), notNullValue());
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "myUsername")
    public void shouldGetArtikelById() throws Exception {
        var artikelKategorie = artikelKategorieRepository.save(
                ArtikelKategorieEntity.builder()
                        .name("shouldGetArtikelById")
                        .build()
        );
        var artikelDTO = artikelForTC("shouldGetArtikelById", ArtikelKategorieMapper.getArtikelKategorieDTO(artikelKategorie));
        var artikel = artikelJpaRepository.save(ArtikelMapper.getArticleEntity(artikelDTO));

        MvcResult getArtikelResult = mockMvc.perform(get("/artikel/" + artikel.getId()).accept("application/json")).andReturn();
        assertThat(getArtikelResult.getResponse().getStatus(), is(200));
        ArtikelDTO fetchedArtikel = objectMapper.readValue(getArtikelResult.getResponse().getContentAsString(), ArtikelDTO.class);
        assertThat(fetchedArtikel.getId(), is(artikel.getId()));
        assertThat(fetchedArtikel.getArtikelKategorie().getId(), is(artikel.getArtikelKategorie().getId()));
        assertThat(fetchedArtikel.getBeschreibung(), is(artikelDTO.getBeschreibung()));
        assertThat(fetchedArtikel.getHersteller(), is(artikelDTO.getHersteller()));
        assertThat(fetchedArtikel.getEan(), is(artikelDTO.getEan()));
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "myUsername")
    public void shouldUpdateArtikel() throws Exception {
        var artikelKategorie = artikelKategorieRepository.save(
                ArtikelKategorieEntity.builder()
                        .name("shouldUpdateArtikel")
                        .build()
        );
        var updateArtikelKategorie = artikelKategorieRepository.save(
                ArtikelKategorieEntity.builder()
                        .name("kategorie-updated")
                        .build()
        );
        var artikelDTO = artikelForTC("shouldUpdateArtikel", ArtikelKategorieMapper.getArtikelKategorieDTO(artikelKategorie));
        var savedArtikel = artikelJpaRepository.save(ArtikelMapper.getArticleEntity(artikelDTO));

        artikelDTO.setId(savedArtikel.getId());
        artikelDTO.setEan("ean-updated");
        artikelDTO.setBeschreibung("beschreibung-updated");
        artikelDTO.setHersteller("hersteller-updated");
        artikelDTO.setArtikelKategorie(ArtikelKategorieMapper.getArtikelKategorieDTO(updateArtikelKategorie));

        MvcResult putArtikelResult = mockMvc.perform(put("/artikel")
                .contentType("application/json")
                .accept("application/json")
                .content(objectMapper.writeValueAsString(artikelDTO))).andReturn();
        assertThat(putArtikelResult.getResponse().getStatus(), is(200));
        ArtikelDTO fetchedArtikelUpdate = objectMapper.readValue(putArtikelResult.getResponse().getContentAsString(), ArtikelDTO.class);
        assertThat(fetchedArtikelUpdate.getId(), is(savedArtikel.getId()));
        assertThat(fetchedArtikelUpdate.getArtikelKategorie().getId(), is(artikelDTO.getArtikelKategorie().getId()));
        assertThat(fetchedArtikelUpdate.getBeschreibung(), is(artikelDTO.getBeschreibung()));
        assertThat(fetchedArtikelUpdate.getHersteller(), is(artikelDTO.getHersteller()));
        assertThat(fetchedArtikelUpdate.getEan(), is(artikelDTO.getEan()));
    }


    private ArtikelDTO artikelForTC(String tcNAME, ArtikelKategorieDTO artikelKategorieDTO) {
        return ArtikelDTO.builder()
                .beschreibung(tcNAME + "-description")
                .ean(tcNAME + "-ean")
                .hersteller(tcNAME + "-hersteller")
                .name(tcNAME + "-name")
                .artikelKategorie(artikelKategorieDTO)
                .build();
    }

}