package io.remedymatch.dbinit;


import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelJpaRepository;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieJpaRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"dbinit"})
@Tag("InMemory")
@Tag("SpringBoot")
public class DatabaseInitializerIntegrationTest {

    @Autowired
    private DatabaseInitializer databaseInitializer;

    @Autowired
    private ArtikelKategorieJpaRepository artikelKategorieJpaRepository;

    @Autowired
    private ArtikelJpaRepository artikelJpaRepository;

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void kategorienUndArtikelSolltenAngelegtWerden() {

        List<ArtikelEntity> artikel = artikelJpaRepository.findAll();
        List<ArtikelKategorieEntity> kategorien = artikelKategorieJpaRepository.findAll();
        assertThat(artikel.isEmpty());
        assertThat(kategorien.isEmpty());

        databaseInitializer.onApplicationEvent(Mockito.mock(ContextRefreshedEvent.class));

        artikel = artikelJpaRepository.findAll();
        kategorien = artikelKategorieJpaRepository.findAll();
        assertThat(!(artikel.isEmpty()));
        assertThat(!(kategorien.isEmpty()));
    }
}
