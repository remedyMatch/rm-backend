package io.remedymatch.angebot.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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

import io.remedymatch.TestApplication;
import io.remedymatch.angebot.domain.anfrage.AngebotAnfrageEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionTyp;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles("test")
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("AngebotAnfrageJpaRepository InMemory Test soll")
public class AngebotAnfrageJpaRepositoryInMemoryTestShould {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private AngebotAnfrageJpaRepository jpaRepository;

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen fuer InstitutionAn zurueckliefern")
	void alle_Anfragen_fuer_InstitutionAn_zurueckliefern() {
		InstitutionEntity meinKrankenhaus = persist(meinKrankenhaus());
		AngebotAnfrageEntity ersteAnfrage = persist(angebotAnfrageFuerInstitution(meinKrankenhaus, 100));
		AngebotAnfrageEntity zweiteAnfrage = persist(angebotAnfrageFuerInstitution(meinKrankenhaus, 100));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteAnfrage, zweiteAnfrage), jpaRepository.findAllByInstitutionAn(meinKrankenhaus));
	}
	
	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen fuer InstitutionAn Id zurueckliefern")
	void alle_Anfragen_fuer_InstitutionAnId_zurueckliefern() {
		InstitutionEntity meinKrankenhaus = persist(meinKrankenhaus());
		AngebotAnfrageEntity ersteAnfrage = persist(angebotAnfrageFuerInstitution(meinKrankenhaus, 100));
		AngebotAnfrageEntity zweiteAnfrage = persist(angebotAnfrageFuerInstitution(meinKrankenhaus, 100));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteAnfrage, zweiteAnfrage), jpaRepository.findAllByInstitutionAn_Id(meinKrankenhaus.getId()));
	}
	
	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen fuer InstitutionVon zurueckliefern")
	void alle_Anfragen_fuer_InstitutionVon_zurueckliefern() {
		InstitutionEntity meinKrankenhaus = persist(meinKrankenhaus());
		AngebotAnfrageEntity ersteAnfrage = persist(angebotAnfrageVonInstitution(meinKrankenhaus, 100));
		AngebotAnfrageEntity zweiteAnfrage = persist(angebotAnfrageVonInstitution(meinKrankenhaus, 100));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteAnfrage, zweiteAnfrage), jpaRepository.findAllByInstitutionVon(meinKrankenhaus));
	}
	
	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen fuer InstitutionVon Id zurueckliefern")
	void alle_Anfragen_fuer_InstitutionVon_Id_zurueckliefern() {
		InstitutionEntity meinKrankenhaus = persist(meinKrankenhaus());
		AngebotAnfrageEntity ersteAnfrage = persist(angebotAnfrageVonInstitution(meinKrankenhaus, 100));
		AngebotAnfrageEntity zweiteAnfrage = persist(angebotAnfrageVonInstitution(meinKrankenhaus, 100));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteAnfrage, zweiteAnfrage), jpaRepository.findAllByInstitutionVon_Id(meinKrankenhaus.getId()));
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

	private AngebotAnfrageEntity angebotAnfrageFuerInstitution(//
			InstitutionEntity institutionAn, //
			double anzahl) {
		return AngebotAnfrageEntity.builder() //
				.anzahl(100) //
				.institutionAn(institutionAn)//
				.build();
	}

	private AngebotAnfrageEntity angebotAnfrageVonInstitution(//
			InstitutionEntity institutionVon, //
			double anzahl) {
		return AngebotAnfrageEntity.builder() //
				.anzahl(100) //
				.institutionVon(institutionVon)//
				.build();
	}
}
