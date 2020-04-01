package io.remedymatch.angebot.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

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
import io.remedymatch.angebot.domain.AngebotAnfrageStatus;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionTyp;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles("test")
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("AngebotAnfrageJpaRepository soll")
public class AngebotAnfrageJpaRepositoryShould {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private AngebotAnfrageJpaRepository jpaRepository;

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen fuer InstitutionAn Id zurueckliefern")
	void alle_Anfragen_fuer_InstitutionAnId_zurueckliefern() {
		InstitutionEntity meinKrankenhaus = persist(meinKrankenhaus());
		AngebotEntity angebot = persist(angebot(meinKrankenhaus));
		AngebotAnfrageEntity ersteAnfrage = persist(angebotAnfrageFuerAngebot(angebot, BigDecimal.valueOf(100)));
		AngebotAnfrageEntity zweiteAnfrage = persist(angebotAnfrageFuerAngebot(angebot, BigDecimal.valueOf(200)));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteAnfrage, zweiteAnfrage), jpaRepository.findAllByAngebot_Institution_Id(meinKrankenhaus.getId()));
	}
	
	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen fuer InstitutionVon Id zurueckliefern")
	void alle_Anfragen_fuer_InstitutionVon_Id_zurueckliefern() {
		InstitutionEntity meinKrankenhaus = persist(meinKrankenhaus());
		AngebotAnfrageEntity ersteAnfrage = persist(angebotAnfrageVonInstitution(meinKrankenhaus, BigDecimal.valueOf(100)));
		AngebotAnfrageEntity zweiteAnfrage = persist(angebotAnfrageVonInstitution(meinKrankenhaus, BigDecimal.valueOf(200)));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteAnfrage, zweiteAnfrage), jpaRepository.findAllByInstitutionVon_Id(meinKrankenhaus.getId()));
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("Status der Anfragen aktualisieren")
	void status_der_Anfragen_aktualisieren() {
		AngebotEntity angebot = persist(angebot());
		AngebotAnfrageEntity ersteOffeneAnfrage = persist(angebotAnfrage(angebot, AngebotAnfrageStatus.Offen));
		AngebotAnfrageEntity zweiteOffeneAnfrage = persist(angebotAnfrage(angebot, AngebotAnfrageStatus.Offen));
		AngebotAnfrageEntity dritteAnfrageStorniert = persist(angebotAnfrage(angebot, AngebotAnfrageStatus.Angenommen));
		entityManager.flush();

		assertEquals(Optional.of(ersteOffeneAnfrage), jpaRepository.findById(ersteOffeneAnfrage.getId()));
		assertEquals(Optional.of(zweiteOffeneAnfrage), jpaRepository.findById(zweiteOffeneAnfrage.getId()));
		assertEquals(Optional.of(dritteAnfrageStorniert), jpaRepository.findById(dritteAnfrageStorniert.getId()));
		
		jpaRepository.updateStatus(angebot.getId(), AngebotAnfrageStatus.Offen, AngebotAnfrageStatus.Storniert);
		
		entityManager.flush();
		entityManager.clear();
		
		assertEquals(AngebotAnfrageStatus.Storniert, jpaRepository.findById(ersteOffeneAnfrage.getId()).get().getStatus());
		assertEquals(AngebotAnfrageStatus.Storniert, jpaRepository.findById(zweiteOffeneAnfrage.getId()).get().getStatus());
		assertEquals(AngebotAnfrageStatus.Angenommen, jpaRepository.findById(dritteAnfrageStorniert.getId()).get().getStatus());
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
	
	private AngebotEntity angebot() {
		return AngebotEntity.builder() //
				.anzahl(BigDecimal.valueOf(100)) //
				.rest(BigDecimal.valueOf(100)) //
				.haltbarkeit(LocalDateTime.now()) //
				.steril(true) //
				.originalverpackt(true) //
				.medizinisch(true) //
				.kommentar("Bla bla") //
				.bedient(false) //
				.build();
	}
	
	private AngebotEntity angebot(InstitutionEntity institutionEntity) {
		return AngebotEntity.builder() //
				.anzahl(BigDecimal.valueOf(100)) //
				.rest(BigDecimal.valueOf(100)) //
				.institution(institutionEntity) //
				.haltbarkeit(LocalDateTime.now()) //
				.steril(true) //
				.originalverpackt(true) //
				.medizinisch(true) //
				.kommentar("Bla bla") //
				.bedient(false) //
				.build();
	}

	private AngebotAnfrageEntity angebotAnfrage(//
			AngebotEntity angebot, //
			AngebotAnfrageStatus status) {
		return AngebotAnfrageEntity.builder() //
				.anzahl(BigDecimal.valueOf(50)) //
				.angebot(angebot) //
				.status(status) //
				.build();
	}
	
	private AngebotAnfrageEntity angebotAnfrageFuerAngebot(//
			AngebotEntity angebot, //
			BigDecimal anzahl) {
		return AngebotAnfrageEntity.builder() //
				.anzahl(anzahl) //
				.angebot(angebot)//
				.build();
	}

	private AngebotAnfrageEntity angebotAnfrageVonInstitution(//
			InstitutionEntity institutionVon, //
			BigDecimal anzahl) {
		return AngebotAnfrageEntity.builder() //
				.anzahl(anzahl) //
				.institutionVon(institutionVon)//
				.build();
	}
}
