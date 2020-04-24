package io.remedymatch.match.infrastructure;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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

import io.remedymatch.TestApplication;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.match.domain.MatchStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles(profiles = { "test", "disableexternaltasks" })
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("MatchJpaRepository  soll")
public class MatchJpaRepositoryShould {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private MatchJpaRepository jpaRepository;

	private InstitutionEntity institutionVon;
	private MatchStandortEntity standortVon;
	private InstitutionEntity institutionAn;
	private MatchStandortEntity standortAn;

	@BeforeEach
	private void prepare() {
		institutionVon = persist(institution());
		standortVon = persist(standort());
		institutionAn = persist(institution());
		standortAn = persist(standort());
		entityManager.flush();
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Matches fuer InstitutionAn Id zurueckliefern")
	void alle_Matches_fuer_InstitutionAnId_zurueckliefern() {
		MatchEntity erstesMatch = persist(match());
		MatchEntity zweitesMatch = persist(match());
		entityManager.flush();

		assertThat(//
				jpaRepository.findAllByInstitutionAn_Id(institutionAn.getId()), //
				containsInAnyOrder(erstesMatch, zweitesMatch));
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Matches fuer InstitutionVon Id zurueckliefern")
	void alle_Matches_fuer_InstitutionVon_Id_zurueckliefern() {
		MatchEntity erstesMatch = persist(match());
		MatchEntity zweitesMatch = persist(match());
		entityManager.flush();

		assertThat(//
				jpaRepository.findAllByInstitutionVon_Id(institutionVon.getId()), //
				containsInAnyOrder(erstesMatch, zweitesMatch));
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Matches fuer Status zurueckliefern")
	void alle_Matches_fuer_Status_Id_zurueckliefern() {
		MatchEntity erstesOffenesMatch = persist(match(MatchStatus.Offen));
		MatchEntity zweitesOffenesMatch = persist(match(MatchStatus.Offen));
		persist(match(MatchStatus.Ausgeliefert));
		entityManager.flush();

		assertThat(//
				jpaRepository.findAllByStatus(MatchStatus.Offen), //
				containsInAnyOrder(erstesOffenesMatch, zweitesOffenesMatch));
	}

	/* help methods */

	public <E> E persist(E entity) {
		entityManager.persist(entity);
		return entity;
	}

	private InstitutionEntity institution() {
		return InstitutionEntity.builder() //
				.institutionKey("mein_krankenhaus") //
				.name("Mein Krankenhaus") //
				.typ(InstitutionTyp.KRANKENHAUS) //
				.build();
	}

	private MatchStandortEntity standort() {
		return MatchStandortEntity.builder() //
				.institutionStandortId(UUID.randomUUID()) //
				.name("Mein Standort") //
				.strasse("Strasse") //
				.hausnummer("10.") //
				.plz("PLZ") //
				.ort("Ort") //
				.land("Land") //
				.build();
	}

	private MatchEntity match() {
		return match(MatchStatus.Offen);
	}

	private MatchEntity match(MatchStatus status) {
		return MatchEntity.builder() //
				.anfrageId(UUID.randomUUID()) //
				.institutionAn(institutionAn) //
				.standortAn(standortAn) //
				.institutionVon(institutionVon) //
				.standortVon(standortVon) //
				.kommentar("Bla bla") //
				.anzahl(BigDecimal.TEN).anfrageTyp("Bedarf").artikelId(UUID.randomUUID()).status(status) //
				.build();
	}
}
