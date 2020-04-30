package io.remedymatch.dbinit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Component
@Profile("dbmigration")
@Slf4j
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
				if (person.getAktuelleInstitution() == null) {
					migriereAktuelleInstitution(person);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Fehler beim Erstellen des initialen Datenbestandes", ex);
		}
	}

	private void migriereAktuelleInstitution(final PersonEntity person) {
		log.info("Eine Person ohne aktuelleInstitution gefunden: " + person.getUsername());

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
		InstitutionStandortEntity standort = institution.getHauptstandort();
		
		if (person.getStandortId() == null) {
			log.warn("Person hat auch kein altest StandortId - nutze Hauptstandort der Institution");
			person.addNeueAktuelleInstitution(institution, standort);
			personRepository.save(person);
			
			return;
		}
		
		val standortOptional = institution.findStandort(person.getStandortId());
		if (standortOptional.isEmpty()) {
			log.warn("Person Standort nicht in Institution gefunden: " + person.getInstitutionId() + ". Nutze Hauptstandort der Institution");
			person.addNeueAktuelleInstitution(institution, standort);
			personRepository.save(person);
		}
		
		person.addNeueAktuelleInstitution(institution, standortOptional.get());
		personRepository.save(person);
	}

	private List<PersonEntity> getAllePersonen() {
		return personRepository.findAll();
	}
}
