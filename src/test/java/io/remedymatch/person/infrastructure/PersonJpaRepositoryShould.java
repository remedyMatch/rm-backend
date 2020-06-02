package io.remedymatch.person.infrastructure;

import io.remedymatch.TestApplication;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import lombok.val;
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
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles(profiles = {"test", "disableexternaltasks"})
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("PersonJpaRepository InMemory Test soll")
public class PersonJpaRepositoryShould {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PersonJpaRepository jpaRepository;

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("ein Person anhand username lesen")
    void eine_Person_anhand_Username_lesen() {
        val meinStandort = persist(standort());
        val meinKrankenhaus = persist(institution(meinStandort));
        val ich = persist(person("ich"));
        ich.addNeuesAktuellesStandort(meinKrankenhaus, meinStandort, true);
        persist(ich);
        entityManager.flush();

//		val ich = jpaRepository.save(person("ich", meinKrankenhaus, meinStandort));

        assertEquals(Optional.of(ich), jpaRepository.findOneByUsername("ich"));
    }

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("eine EntityNotFoundException werfen wenn gesuchte Person mit diesem Id nicht existiert")
    void eine_EntityNotFoundException_werfen_wenn_gesuchte_Person_mit_diesem_Id_nicht_existiert() {

        assertThrows(EntityNotFoundException.class, () -> jpaRepository.getOne(UUID.randomUUID()).toString());
    }

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("ein Person anhand Id lesen")
    void eine_Person_anhand_Id_lesen() {
        val meinStandort = persist(standort());
        val meinKrankenhaus = persist(institution(meinStandort));
        val ich = persist(person("ich"));
        ich.addNeuesAktuellesStandort(meinKrankenhaus, meinStandort, true);
        persist(ich);
        entityManager.flush();

        assertEquals(ich, jpaRepository.getOne(ich.getId()));
    }

    /* help methods */

    public <E> E persist(E entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    private InstitutionEntity institution(final InstitutionStandortEntity standort) {
        return InstitutionEntity.builder() //
                .name("Mein Krankenhaus") //
                .institutionKey("mein-krankehnaus") //
                .typ(InstitutionTyp.KRANKENHAUS) //
                .standorte(Arrays.asList(standort)) //
                .build();
    }

    private InstitutionStandortEntity standort() {
        return InstitutionStandortEntity.builder() //
                .name("Mein Standort") //
                .strasse("Strasse") //
                .hausnummer("10a") //
                .plz("PLZ") //
                .ort("Ort") //
                .land("Land") //
                .build();
    }

    private PersonEntity person(final String username) {
        return PersonEntity.builder() //
                .username(username) //
                .vorname("Vorname") //
                .nachname("Nachname") //
                .email("EMail") //
                .telefon("08106112233") //
                .build();
    }
}
