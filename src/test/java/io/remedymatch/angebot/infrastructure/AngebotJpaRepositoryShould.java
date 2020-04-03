package io.remedymatch.angebot.infrastructure;

import io.remedymatch.TestApplication;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;
import io.remedymatch.institution.domain.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles("test")
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("AngebotJpaRepository InMemory Test soll")
public class AngebotJpaRepositoryShould {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AngebotJpaRepository jpaRepository;

    private InstitutionEntity meinKrankenhaus;
    private InstitutionStandortEntity meinStandort;
    private ArtikelKategorieEntity beispielKategorieArtikel;
    private ArtikelEntity beispielArtikel;

    @BeforeEach
    private void prepare() {
        meinKrankenhaus = persist(meinKrankenhaus());
        meinStandort = persist(meinStandort());
        beispielKategorieArtikel = persist(beispielArtikelKategorie());
        beispielArtikel = persist(beispielArtikel());
        entityManager.flush();
    }

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("alle nicht bediente Angebote zurueckliefern")
    void alle_nicht_bediente_Angebote_zurueckliefern() {
        AngebotEntity ersteAngebot = persist(angebot(BigDecimal.valueOf(100)));
        AngebotEntity zweiteAngebot = persist(angebot(BigDecimal.valueOf(200)));
        entityManager.flush();

        assertEquals(Arrays.asList(ersteAngebot, zweiteAngebot), jpaRepository.findAllByDeletedFalseAndBedientFalse());
    }

    /* help methods */

    public <E> E persist(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    private InstitutionEntity meinKrankenhaus() {
        return InstitutionEntity.builder() //
                .institutionKey("mein_krankenhaus") //
                .name("Mein Krankenhaus") //
                .typ(InstitutionTyp.Krankenhaus) //
                .build();
    }

    private InstitutionStandortEntity meinStandort() {
        return InstitutionStandortEntity.builder() //
                .name("Mein Standort") //
                .plz("PLZ") //
                .ort("Ort") //
                .strasse("Strasse") //
                .land("Land") //
                .build();
    }

    private ArtikelKategorieEntity beispielArtikelKategorie() {
        return ArtikelKategorieEntity.builder() //
                .name("beispiel Kategorie") //
                .build();
    }

    private ArtikelEntity beispielArtikel() {
        return ArtikelEntity.builder() //
                .ean("EAN") //
                .name("egal") //
                .beschreibung("beschreibung") //
                .hersteller("hersteller") //
                .artikelKategorie(beispielKategorieArtikel) //
                .build();
    }

    private AngebotEntity angebot(//
                                  BigDecimal anzahl) {
        return AngebotEntity.builder() //
                .artikel(beispielArtikel) //
                .institution(meinKrankenhaus) //
                .standort(meinStandort) //
                .anzahl(anzahl) //
                .rest(anzahl) //
                .haltbarkeit(LocalDateTime.now()) //
                .steril(true) //
                .originalverpackt(true) //
                .medizinisch(true) //
                .kommentar("Bla bla") //
                .bedient(false) //
                .build();
    }
}
