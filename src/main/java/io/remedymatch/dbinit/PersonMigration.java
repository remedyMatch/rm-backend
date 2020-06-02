package io.remedymatch.dbinit;

import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Profile("dbmigration")
@Log4j2
public class PersonMigration implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private PersonJpaRepository personRepository;

    @Autowired
    private InstitutionJpaRepository institutionRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        migrierePersonInstitutionDaten();
    }

    @Transactional
    public void migrierePersonInstitutionDaten() {
        try {
            for (PersonEntity person : getAllePersonen()) {
                if (person.getAktuellesStandort() == null) {
                    migriereAktuellesStandort(person);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Fehler beim Erstellen des initialen Datenbestandes", ex);
        }
    }

    private void migriereAktuellesStandort(final PersonEntity person) {
        log.info("Eine Person ohne aktuellesStandort gefunden: " + person.getUsername());

        if (person.getInstitutionId() == null) {
            log.warn("Person hat auch keine alte InstitutionId");
            return;
        }

        val institutionOptional = institutionRepository.findById(person.getInstitutionId());
        if (institutionOptional.isEmpty()) {
            log.warn("Person Institution nicht gefunden: " + person.getInstitutionId());
            return;
        }

        InstitutionEntity institution = institutionOptional.get();
        InstitutionStandortEntity standort = institution.getStandorte().get(0);

        if (person.getStandortId() == null) {
            log.warn("Person hat auch kein altest StandortId - nutze Hauptstandort der Institution");
            person.addNeuesAktuellesStandort(institution, standort, false);
            personRepository.save(person);

            return;
        }

        val standortOptional = institution.findStandort(person.getStandortId());
        if (standortOptional.isEmpty()) {
            log.warn("Person Standort nicht in Institution gefunden: " + person.getInstitutionId()
                    + ". Nutze Hauptstandort der Institution");
            person.addNeuesAktuellesStandort(institution, standort, false);
            personRepository.save(person);
        }

        person.addNeuesAktuellesStandort(institution, standortOptional.get(), false);
        personRepository.save(person);
    }

    private List<PersonEntity> getAllePersonen() {
        return personRepository.findAll();
    }
}
