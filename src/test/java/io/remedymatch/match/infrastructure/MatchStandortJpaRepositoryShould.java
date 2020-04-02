package io.remedymatch.match.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles("test")
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("MatchStandortJpaRepository InMemory Test soll")
public class MatchStandortJpaRepositoryShould {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private MatchStandortJpaRepository jpaRepository;

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("ein Standort lesen")
	void ein_standort_lesen() {
		MatchStandortEntity standort = persist(standort());
		entityManager.flush();

		assertEquals(Optional.of(standort), jpaRepository.findById(standort.getId()));
	}
	
	/* help methods */
	
	public <E> E persist(E entity) {
		entityManager.persist(entity);
		return entity;
	}
	
	private MatchStandortEntity standort() {
		return MatchStandortEntity.builder() //
				.name("Mein Standort") //
				.plz("PLZ") //
				.ort("Ort") //
				.strasse("Strasse") //
				.land("Land") //
				.build();
	}
}
