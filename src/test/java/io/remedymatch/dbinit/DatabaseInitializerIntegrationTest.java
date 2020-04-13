package io.remedymatch.dbinit;


import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.artikel.infrastructure.*;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageJpaRepository;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortJpaRepository;
import io.remedymatch.match.infrastructure.MatchEntity;
import io.remedymatch.match.infrastructure.MatchJpaRepository;
import io.remedymatch.match.infrastructure.MatchStandortEntity;
import io.remedymatch.match.infrastructure.MatchStandortJpaRepository;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"dbinit", "test"})
@Tag("InMemory")
@Tag("SpringBoot")
public class DatabaseInitializerIntegrationTest {

    @Autowired
    private DatabaseInitializer databaseInitializer;
    @Autowired
    private ArtikelKategorieJpaRepository artikelKategorieJpaRepository;
    @Autowired
    private ArtikelJpaRepository artikelJpaRepository;
    @Autowired
    private ArtikelVarianteJpaRepository artikelVarianteRepository;
    @Autowired
    private InstitutionJpaRepository institutionRepository;
    @Autowired
    private InstitutionStandortJpaRepository institutionStandortRepository;
    @Autowired
    private BedarfAnfrageJpaRepository bedarfAnfrageRepository;
    @Autowired
    private BedarfJpaRepository bedarfRepository;
    @Autowired
    private AngebotAnfrageJpaRepository angebotAnfrageRepository;
    @Autowired
    private AngebotJpaRepository angebotRepository;
    @Autowired
    private PersonJpaRepository personRepository;
    @Autowired
    private MatchJpaRepository matchRepository;
    @Autowired
    private MatchStandortJpaRepository matchStandortRepository;

    private List<ArtikelEntity> artikel;
    private List<ArtikelKategorieEntity> kategorien;
    private List<ArtikelVarianteEntity> artikelVarianten;
    private List<InstitutionEntity> institutionen;
    private List<InstitutionStandortEntity> institutionStandorte;
    private List<BedarfEntity> bedarfe;
    private List<BedarfAnfrageEntity> bedarfAnfragen;
    private List<AngebotEntity> angebote;
    private List<AngebotAnfrageEntity> angebotAnfragen;
    private List<PersonEntity> personen;
    private List<MatchStandortEntity> matchStandorte;
    private List<MatchEntity> matches;

    @Test
    @Transactional
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void kategorienUndArtikelSolltenAngelegtWerden() {

        queryAllRepositories();

        assertThat(artikel.isEmpty());
        assertThat(kategorien.isEmpty());
        assertThat(artikelVarianten.isEmpty());
        assertThat(institutionen.isEmpty());
        assertThat(institutionStandorte.isEmpty());
        assertThat(bedarfe.isEmpty());
        assertThat(bedarfAnfragen.isEmpty());
        assertThat(angebote.isEmpty());
        assertThat(angebotAnfragen.isEmpty());
        assertThat(personen.isEmpty());
        assertThat(matchStandorte.isEmpty());
        assertThat(matches.isEmpty());

        databaseInitializer.onApplicationEvent(Mockito.mock(ContextRefreshedEvent.class));

        queryAllRepositories();

        assertThat(!(artikel.isEmpty()));
        assertThat(!(kategorien.isEmpty()));
        assertThat(!(artikelVarianten.isEmpty()));
        assertThat(!(institutionen.isEmpty()));
        assertThat(!(institutionStandorte.isEmpty()));
        assertThat(!(bedarfe.isEmpty()));
        assertThat(!(bedarfAnfragen.isEmpty()));
        assertThat(!(angebote.isEmpty()));
        assertThat(!(angebotAnfragen.isEmpty()));
        assertThat(!(personen.isEmpty()));
        assertThat(!(matchStandorte.isEmpty()));
        assertThat(!(matches.isEmpty()));
    }

    @Test
    @Transactional
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void kategorienUndArtikelSolltenNichtAngelegtWerdenWennBereitsVorhanden() {

        queryAllRepositories();

        assertThat(artikel.isEmpty());
        assertThat(kategorien.isEmpty());
        assertThat(artikelVarianten.isEmpty());
        assertThat(institutionen.isEmpty());
        assertThat(institutionStandorte.isEmpty());
        assertThat(bedarfe.isEmpty());
        assertThat(bedarfAnfragen.isEmpty());
        assertThat(angebote.isEmpty());
        assertThat(angebotAnfragen.isEmpty());
        assertThat(personen.isEmpty());
        assertThat(matchStandorte.isEmpty());
        assertThat(matches.isEmpty());

        // first run
        databaseInitializer.onApplicationEvent(Mockito.mock(ContextRefreshedEvent.class));

        queryAllRepositories();
        int artikelCountNachErstemDbInit = artikel.size();
        int kategorieCountNachErstemDbInit = kategorien.size();
        int artikelVariantenCountNachErstemDbInit = artikelVarianten.size();
        int angebotCountNachErstemDbInit = angebote.size();
        int angebotAnfragenCountNachErstemDbInit = angebotAnfragen.size();
        int institutionCountNachErstemDbInit = institutionen.size();
        int institutionStandortCountNachErstemDbInit = institutionStandorte.size();
        int personCountNachErstemDbInit = personen.size();
        int bedarfCountNachErstemDbInit = bedarfe.size();
        int bedarfAnfragenCountNachErstemDbInit = bedarfAnfragen.size();
        int matchCountNachErstemDbInit = matches.size();
        int matchStandorteCountNachErstemDbInit = matchStandorte.size();

        // second run
        databaseInitializer.onApplicationEvent(Mockito.mock(ContextRefreshedEvent.class));

        queryAllRepositories();

        assertThat(artikel).hasSize(artikelCountNachErstemDbInit);
        assertThat(kategorien).hasSize(kategorieCountNachErstemDbInit);
        assertThat(artikelVarianten).hasSize(artikelVariantenCountNachErstemDbInit);
        assertThat(angebotAnfragen).hasSize(angebotAnfragenCountNachErstemDbInit);
        assertThat(angebote).hasSize(angebotCountNachErstemDbInit);
        assertThat(institutionen).hasSize(institutionCountNachErstemDbInit);
        assertThat(institutionStandorte).hasSize(institutionStandortCountNachErstemDbInit);
        assertThat(personen).hasSize(personCountNachErstemDbInit);
        assertThat(bedarfe).hasSize(bedarfCountNachErstemDbInit);
        assertThat(bedarfAnfragen).hasSize(bedarfAnfragenCountNachErstemDbInit);
        assertThat(matches).hasSize(matchCountNachErstemDbInit);
        assertThat(matchStandorte).hasSize(matchStandorteCountNachErstemDbInit);
    }

    @Test
    @Transactional
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void esSolltenKeineDoppeltenArtikelVariantenAngelegtWerden() {
        databaseInitializer.onApplicationEvent(Mockito.mock(ContextRefreshedEvent.class));

        queryAllRepositories();
        final List<ArtikelEntity> alleArtikel = artikelJpaRepository.findAll();
        final Map<String, Integer> sameVariantsByArtikelIdAndVariantenName = new HashMap<>();
        for (final ArtikelEntity artikel : alleArtikel) {
            for (final ArtikelVarianteEntity variante : artikel.getVarianten()) {
                final String key = variante.getArtikel().toString() + "_" + variante.getVariante();
                sameVariantsByArtikelIdAndVariantenName.putIfAbsent(key, 0);
                sameVariantsByArtikelIdAndVariantenName.put(key, sameVariantsByArtikelIdAndVariantenName.get(key) + 1);
            }
        }
        for (final Integer variantCount : sameVariantsByArtikelIdAndVariantenName.values()) {
            // Hat ein Artikel mehrere Varianten mit dem selben Namen, kann der Anwender in der GUI nicht mehr unterscheiden.
            // TODO unique constraint in DB
            assertThat(variantCount).isEqualTo(1);
        }
    }


    private void queryAllRepositories() {
        artikel = artikelJpaRepository.findAll();
        kategorien = artikelKategorieJpaRepository.findAll();
        artikelVarianten = artikelVarianteRepository.findAll();
        institutionen = institutionRepository.findAll();
        institutionStandorte = institutionStandortRepository.findAll();
        bedarfe = bedarfRepository.findAll();
        bedarfAnfragen = bedarfAnfrageRepository.findAll();
        angebote = angebotRepository.findAll();
        angebotAnfragen = angebotAnfrageRepository.findAll();
        personen = personRepository.findAll();
        matchStandorte = matchStandortRepository.findAll();
        matches = matchRepository.findAll();
    }
}
