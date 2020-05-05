package io.remedymatch.bedarf.infrastructure;

import io.remedymatch.TestApplication;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import lombok.val;
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
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles(profiles = {"test", "disableexternaltasks"})
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("BedarfJpaRepository InMemory Test soll")
public class BedarfJpaRepositoryShould {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BedarfJpaRepository jpaRepository;

    private InstitutionEntity meinKrankenhaus;
    private InstitutionStandortEntity meinStandort;
    private ArtikelKategorieEntity beispielKategorieArtikel;
    private ArtikelEntity beispielArtikel;
    private ArtikelVarianteEntity beispielArtikelVariante;

    @BeforeEach
    private void prepare() {
        meinKrankenhaus = persist(meinKrankenhaus());
        meinStandort = persist(meinStandort());
        beispielKategorieArtikel = persist(beispielArtikelKategorie());
        beispielArtikel = persist(beispielArtikel());
        beispielArtikelVariante = persist(beispielArtikelVariante());
        entityManager.flush();
    }

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("ArtikelKategorieIds mit Anzahl nicht bedienten Bedarfe zurueckliefern")
    void artikelKategorieIds_mit_Anzahl_nicht_bedienten_Bedarfe_zurueckliefern() {
        // zwei Bedarfe in erste Kategorie
        val kategorie1 = persist(beispielArtikelKategorie());
        val kategorie1Artikel1 = persist(artikel(kategorie1));
        val kategorie1Artikel1Variante1 = persist(artikelVariante(kategorie1Artikel1, "variante1"));
        persist(bedarf(kategorie1Artikel1, kategorie1Artikel1Variante1));
        persist(bedarf(kategorie1Artikel1, kategorie1Artikel1Variante1));

        // ein Bedarf in zweite Kategorie
        val kategorie2 = persist(beispielArtikelKategorie());
        val kategorie2Artikel1 = persist(artikel(kategorie2));
        val kategorie2Artikel1Variante1 = persist(artikelVariante(kategorie2Artikel1, "variante1"));
        persist(bedarf(kategorie2Artikel1, kategorie2Artikel1Variante1));

        // ein bedientes Bedarf in dritte Kategorie
        val kategorie3 = persist(beispielArtikelKategorie());
        val kategorie3Artikel1 = persist(artikel(kategorie3));
        val kategorie3Artikel1Variante1 = persist(artikelVariante(kategorie3Artikel1, "variante1"));
        val bedientesBedarf = bedarf(kategorie3Artikel1, kategorie3Artikel1Variante1);
        bedientesBedarf.setBedient(true);
        persist(bedientesBedarf);

        // ein geloeschtes Bedarf in vierte Kategorie
        val kategorie4 = persist(beispielArtikelKategorie());
        val kategorie4Artikel1 = persist(artikel(kategorie4));
        val kategorie4Artikel1Variante1 = persist(artikelVariante(kategorie4Artikel1, "variante1"));
        val geloeschtesBedarf = bedarf(kategorie4Artikel1, kategorie4Artikel1Variante1);
        geloeschtesBedarf.setDeleted(true);
        persist(geloeschtesBedarf);

        // default Kategorie ist ohne Bedarf

        assertEquals(3, jpaRepository.findAllByDeletedFalseAndBedientFalse().size());

        FilterEntry ersteKategorie = filterEntry(kategorie1.getId(), 2);
        FilterEntry zweiteKategorie = filterEntry(kategorie2.getId(), 1);
        assertThat(//
                jpaRepository.countAllBedarfKategorienByDeletedFalseAndBedientFalse(), //
                containsInAnyOrder(ersteKategorie, zweiteKategorie));
    }

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("ArtikelIds mit Anzahl nicht bedienten Bedarfe zurueckliefern")
    void artikelIds_mit_Anzahl_nicht_bedienten_Bedarfe_zurueckliefern() {
        // zwei Bedarfe in ersten Artikel
        val kategorie1 = persist(beispielArtikelKategorie());
        val kategorie1Artikel1 = persist(artikel(kategorie1));
        val kategorie1Artikel1Variante1 = persist(artikelVariante(kategorie1Artikel1, "variante1"));
        persist(bedarf(kategorie1Artikel1, kategorie1Artikel1Variante1));
        persist(bedarf(kategorie1Artikel1, kategorie1Artikel1Variante1));

        // ein Bedarf in zweiten Artikel
        val kategorie1Artikel2 = persist(artikel(kategorie1));
        val kategorie1Artikel2Variante1 = persist(artikelVariante(kategorie1Artikel2, "variante1"));
        persist(bedarf(kategorie1Artikel2, kategorie1Artikel2Variante1));

        // ein bedientes Bedarf in dritten Artikel
        val kategorie1Artikel3 = persist(artikel(kategorie1));
        val kategorie1Artikel3Variante1 = persist(artikelVariante(kategorie1Artikel3, "variante1"));
        val bedientesBedarf = bedarf(kategorie1Artikel3, kategorie1Artikel3Variante1);
        bedientesBedarf.setBedient(true);
        persist(bedientesBedarf);

        // ein geloeschtes Bedarf in vierten Artikel
        val kategorie1Artikel4 = persist(artikel(kategorie1));
        val kategorie1Artikel4Variante1 = persist(artikelVariante(kategorie1Artikel4, "variante1"));
        val geloeschtesBedarf = bedarf(kategorie1Artikel4, kategorie1Artikel4Variante1);
        geloeschtesBedarf.setDeleted(true);
        persist(geloeschtesBedarf);

        // default Artikel mit Bedarf
        persist(bedarf(BigDecimal.valueOf(1)));

        assertEquals(4, jpaRepository.findAllByDeletedFalseAndBedientFalse().size());

        FilterEntry erstesArtikel = filterEntry(kategorie1Artikel1.getId(), 2);
        FilterEntry zweitesArtikel = filterEntry(kategorie1Artikel2.getId(), 1);
        assertThat(//
                jpaRepository.countAllBedarfArtikelByDeletedFalseAndBedientFalseAndArtikel_ArtikelKategorie(
                        kategorie1.getId()), //
                containsInAnyOrder(erstesArtikel, zweitesArtikel));
    }

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("ArtikelVarianteIds mit Anzahl nicht bedienten Bedarfe zurueckliefern")
    void artikelVarianteIds_mit_Anzahl_nicht_bedienten_Bedarfe_zurueckliefern() {
        // zwei Bedarfe in erster Artikel-Variante
        val kategorie1 = persist(beispielArtikelKategorie());
        val kategorie1Artikel1 = persist(artikel(kategorie1));
        val kategorie1Artikel1Variante1 = persist(artikelVariante(kategorie1Artikel1, "variante1"));
        persist(bedarf(kategorie1Artikel1, kategorie1Artikel1Variante1));
        persist(bedarf(kategorie1Artikel1, kategorie1Artikel1Variante1));

        // ein Bedarf in zweiter Variante
        val kategorie1Artikel1Variante2 = persist(artikelVariante(kategorie1Artikel1, "variante2"));
        persist(bedarf(kategorie1Artikel1, kategorie1Artikel1Variante2));

        // ein bedientes Bedarf in dritte Variante
        val kategorie1Artikel1Variante3 = persist(artikelVariante(kategorie1Artikel1, "variante3"));
        val bedientesBedarf = bedarf(kategorie1Artikel1, kategorie1Artikel1Variante3);
        bedientesBedarf.setBedient(true);
        persist(bedientesBedarf);

        // ein geloeschtes Bedarf in vierte Variante
        val kategorie1Artikel1Variante4 = persist(artikelVariante(kategorie1Artikel1, "variante4"));
        val geloeschtesBedarf = bedarf(kategorie1Artikel1, kategorie1Artikel1Variante4);
        geloeschtesBedarf.setDeleted(true);
        persist(geloeschtesBedarf);

        // default Variante mit Bedarf
        persist(bedarf(BigDecimal.valueOf(1)));

        assertEquals(4, jpaRepository.findAllByDeletedFalseAndBedientFalse().size());

        FilterEntry ersteVariante = filterEntry(kategorie1Artikel1Variante1.getId(), 2);
        FilterEntry zweiteVariante = filterEntry(kategorie1Artikel1Variante2.getId(), 1);
        assertThat(//
                jpaRepository.countAllBedarfArtikelVariantenByDeletedFalseAndBedientFalseAndArtikel_Id(
                        kategorie1Artikel1.getId()), //
                containsInAnyOrder(ersteVariante, zweiteVariante));
    }

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("alle nicht bediente Bedarfe zurueckliefern")
    void alle_nicht_bediente_Bedarfe_zurueckliefern() {
        BedarfEntity ersteBedarf = persist(bedarf(BigDecimal.valueOf(100)));
        BedarfEntity zweiteBedarf = persist(bedarf(BigDecimal.valueOf(200)));
        entityManager.flush();

        assertThat(//
                jpaRepository.findAllByDeletedFalseAndBedientFalse(), //
                containsInAnyOrder(ersteBedarf, zweiteBedarf));
    }

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("alle nicht bediente Bedarfe einer Institution zurueckliefern")
    void alle_nicht_bediente_Bedarfe_einer_Institution_zurueckliefern() {
        BedarfEntity ersteBedarf = persist(bedarf(BigDecimal.valueOf(100)));
        BedarfEntity zweiteBedarf = persist(bedarf(BigDecimal.valueOf(200)));
        entityManager.flush();

        assertThat(//
                jpaRepository.findAllByDeletedFalseAndBedientFalseAndInstitution_Id(meinKrankenhaus.getId()), //
                containsInAnyOrder(ersteBedarf, zweiteBedarf));
    }

    /* help methods */

    public <E> E persist(E entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    private InstitutionEntity meinKrankenhaus() {
        return InstitutionEntity.builder() //
                .institutionKey("mein_krankenhaus") //
                .name("Mein Krankenhaus") //
                .typ(InstitutionTyp.KRANKENHAUS) //
                .build();
    }

    private InstitutionStandortEntity meinStandort() {
        return InstitutionStandortEntity.builder() //
                .name("Mein Standort") //
                .strasse("Strasse") //
                .hausnummer("10a") //
                .plz("PLZ") //
                .ort("Ort") //
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
                .name("egal") //
                .beschreibung("beschreibung") //
                .artikelKategorie(beispielKategorieArtikel.getId()) //
                .build();
    }

    private ArtikelEntity artikel(final ArtikelKategorieEntity artikelKategorie) {
        return ArtikelEntity.builder() //
                .name("egal") //
                .beschreibung("beschreibung") //
                .artikelKategorie(artikelKategorie.getId()) //
                .build();
    }

    private ArtikelVarianteEntity beispielArtikelVariante() {
        return ArtikelVarianteEntity.builder() //
                .artikel(beispielArtikel.getId()) //
                .variante("egal") //
                .beschreibung("beschreibung") //
                .build();
    }

    private ArtikelVarianteEntity artikelVariante(final ArtikelEntity artikel, final String variante) {
        return ArtikelVarianteEntity.builder() //
                .artikel(artikel.getId()) //
                .variante(variante) //
                .beschreibung("beschreibung") //
                .build();
    }

    private BedarfEntity bedarf(//
                                BigDecimal anzahl) {
        return BedarfEntity.builder() //
                .artikel(beispielArtikel) //
                .artikelVariante(beispielArtikelVariante) //
                .institution(meinKrankenhaus) //
                .standort(meinStandort) //
                .anzahl(anzahl) //
                .rest(anzahl) //
                .steril(true) //
                .medizinisch(true) //
                .kommentar("Bla bla") //
                .bedient(false) //
                .build();
    }

    private BedarfEntity bedarf(final ArtikelEntity artikel, final ArtikelVarianteEntity artikelVariante) {
        return BedarfEntity.builder() //
                .artikel(artikel) //
                .artikelVariante(artikelVariante) //
                .institution(meinKrankenhaus) //
                .standort(meinStandort) //
                .anzahl(BigDecimal.valueOf(123)) //
                .rest(BigDecimal.valueOf(123)) //
                .steril(true) //
                .medizinisch(true) //
                .kommentar("Bla bla") //
                .bedient(false) //
                .build();
    }

    private FilterEntry filterEntry(final UUID id, final long anzahl) {
        return FilterEntry.builder() //
                .id(id) //
                .anzahl(anzahl) //
                .build();
    }
}
