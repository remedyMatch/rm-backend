package io.remedymatch.institution.infrastructure;

import io.remedymatch.TestApplication;
import io.remedymatch.institution.domain.model.InstitutionAntragStatus;
import io.remedymatch.institution.domain.model.InstitutionRolle;
import io.remedymatch.institution.domain.model.InstitutionTyp;
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
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles(profiles = {"test", "disableexternaltasks"})
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("InstitutionAntragJpaRepository InMemory Test soll")
public class InstitutionAntragJpaRepositoryShould {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private InstitutionAntragJpaRepository jpaRepository;

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("ein Antrag lesen")
    void ein_antrag_lesen() {
        InstitutionAntragEntity standort = persist(antrag());
        entityManager.flush();
        assertEquals(Optional.of(standort), jpaRepository.findById(standort.getId()));
    }

    @Rollback(true)
    @Transactional
    @Test
    @DisplayName("alle Anträge zu einer Person lesen")
    void alle_antraege_einer_person_lesen() {
        val personId = UUID.randomUUID();
        persist(antrag(personId));
        persist(antrag(personId));
        entityManager.flush();
        assertEquals(2, jpaRepository.findAllByAntragsteller(personId).size());
    }

    /* help methods */

    public <E> E persist(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    private InstitutionAntragEntity antrag() {
        return antrag(UUID.randomUUID());
    }

    private InstitutionAntragEntity antrag(UUID personId) {
        return InstitutionAntragEntity.builder() //
                .name("Mein Standort") //
                .strasse("Strasse") //
                .hausnummer("10a") //
                .plz("PLZ") //
                .ort("Ort") //
                .land("Land") //
                .rolle(InstitutionRolle.EMPFAENGER)
                .status(InstitutionAntragStatus.OFFEN)
                .institutionTyp(InstitutionTyp.ANDERE)
                .webseite("HTTP")
                .antragsteller(personId)
                .build();
    }
}
