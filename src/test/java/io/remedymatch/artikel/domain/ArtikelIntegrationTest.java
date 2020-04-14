package io.remedymatch.artikel.domain;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.TestApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"test", "disableexternaltasks"})
@Tag("InMemory")
@Tag("SpringBoot")
public class ArtikelIntegrationTest {

//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private ArtikelJpaRepository artikelJpaRepository;
//
//    @Autowired
//    private ArtikelRepository artikelRepository;
//    
//    @Autowired
//    private ArtikelKategorieJpaRepository artikelKategorieJpaRepository;
//
//    @Autowired
//    private ArtikelKategorieRepository artikelKategorieRepository;
//    
//    @Autowired
//    private BedarfJpaRepository bedarfRepository;
//
//    @Autowired
//    private AngebotJpaRepository angebotRepository;
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @BeforeEach
//    @Transactional
//    public void beforeEach() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//        angebotRepository.deleteAll();
//        bedarfRepository.deleteAll();
//        artikelJpaRepository.deleteAll();
//        artikelKategorieJpaRepository.deleteAll();
//    }
//
//    @Test
//    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "myUsername")
//    public void shouldAddArtike() throws Exception {
//
//        var artikelKategorie = artikelKategorieRepository.add(
//                ArtikelKategorie.builder()
//                        .name("shouldAddArtike")
//                        .build()
//        );
//
//        MvcResult postArtikelResult = mockMvc.perform(post("/artikel")
//                .contentType("application/json")
//                .accept("application/json")
//                .content(
//                        objectMapper.writeValueAsString(artikelForTC("shouldAddArtikel", ArtikelKategorieMapper.getArtikelKategorieDTO(artikelKategorie)))
//                )).andReturn();
//
//        assertThat(postArtikelResult.getResponse().getStatus(), is(200));
//        ArtikelDTO artikelDTO = objectMapper.readValue(postArtikelResult.getResponse().getContentAsString(), ArtikelDTO.class);
//        assertThat(artikelDTO, notNullValue());
//        assertThat(artikelDTO.getId(), notNullValue());
//        assertThat(artikelDTO.getName(), is("shouldAddArtikel-name"));
//        assertThat(artikelDTO.getEan(), is("shouldAddArtikel-ean"));
//        assertThat(artikelDTO.getHersteller(), is("shouldAddArtikel-hersteller"));
//        assertThat(artikelDTO.getBeschreibung(), is("shouldAddArtikel-description"));
//        assertThat(artikelDTO.getArtikelKategorie().getId(), is(artikelKategorie.getId().getValue()));
//
//        assertThat(artikelJpaRepository.findById(artikelDTO.getId()), notNullValue());
//    }
//
//    @Test
//    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "myUsername")
//    public void shouldGetArtikelById() throws Exception {
//        var artikelKategorie = artikelKategorieRepository.add(
//                ArtikelKategorie.builder()
//                        .name("shouldGetArtikelById")
//                        .build()
//        );
//        var artikelDTO = artikelForTC("shouldGetArtikelById", ArtikelKategorieMapper.getArtikelKategorieDTO(artikelKategorie));
//        var artikel = artikelRepository.add(ArtikelMapper.getArtikel(artikelDTO));
//
//        MvcResult getArtikelResult = mockMvc.perform(get("/artikel/" + artikel.getId().getValue()).accept("application/json")).andReturn();
//        assertThat(getArtikelResult.getResponse().getStatus(), is(200));
//        ArtikelDTO fetchedArtikel = objectMapper.readValue(getArtikelResult.getResponse().getContentAsString(), ArtikelDTO.class);
//        assertThat(fetchedArtikel.getId(), is(artikel.getId().getValue()));
//        assertThat(fetchedArtikel.getArtikelKategorie().getId(), is(artikel.getArtikelKategorie().getId().getValue()));
//        assertThat(fetchedArtikel.getBeschreibung(), is(artikelDTO.getBeschreibung()));
//        assertThat(fetchedArtikel.getHersteller(), is(artikelDTO.getHersteller()));
//        assertThat(fetchedArtikel.getEan(), is(artikelDTO.getEan()));
//    }
//
//    private ArtikelDTO artikelForTC(String tcNAME, ArtikelKategorieDTO artikelKategorieDTO) {
//        return ArtikelDTO.builder()
//                .beschreibung(tcNAME + "-description")
//                .ean(tcNAME + "-ean")
//                .hersteller(tcNAME + "-hersteller")
//                .name(tcNAME + "-name")
//                .artikelKategorie(artikelKategorieDTO)
//                .build();
//    }

}