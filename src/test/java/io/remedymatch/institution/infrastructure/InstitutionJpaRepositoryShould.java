package io.remedymatch.institution.infrastructure;

import io.remedymatch.TestApplication;
import io.remedymatch.institution.domain.model.InstitutionTyp;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles(profiles = {"test", "disableexternaltasks"})
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("InstitutionJpaRepository InMemory Test soll")
public class InstitutionJpaRepositoryShould {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private InstitutionJpaRepository jpaRepository;

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("eine Institution speichern")
    void eine_institution_speichern() {
        var meineInstitution = persist(institution("meine"));
        meineInstitution = persist(meineInstitution);
        entityManager.flush();

        assertEquals(Optional.of(meineInstitution), jpaRepository.findById(meineInstitution.getId()));
    }

    /* help methods */

    public <E> E persist(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    private InstitutionEntity institution(final String institutionKey) {
        return InstitutionEntity.builder() //
                .name("Mein Krankenhaus") //
                .institutionKey(institutionKey) //
                .typ(InstitutionTyp.KRANKENHAUS) //
                .build();
    }

    private InstitutionStandortEntity standort(final String name) {
        return InstitutionStandortEntity.builder() //
                .name(name) //
                .strasse("Strasse") //
                .hausnummer("10a") //
                .plz("PLZ") //
                .ort("Ort") //
                .land("Land") //
                .build();
    }
}
