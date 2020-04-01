package io.remedymatch.institution.infrastructure;

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
@DisplayName("InstitutionStandortJpaRepository InMemory Test soll")
public class InstitutionStandortJpaRepositoryShould {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private InstitutionStandortJpaRepository jpaRepository;

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("ein Standort lesen")
	void ein_standort_lesen() {
		InstitutionStandortEntity standort = persist(standort());
		entityManager.flush();

		assertEquals(Optional.of(standort), jpaRepository.findById(standort.getId()));
	}
	
	/* help methods */
	
	public <E> E persist(E entity) {
		entityManager.persist(entity);
		return entity;
	}
	
	private InstitutionStandortEntity standort() {
		return InstitutionStandortEntity.builder() //
				.name("Mein Standort") //
				.plz("PLZ") //
				.ort("Ort") //
				.strasse("Strasse") //
				.land("Land") //
				.build();
	}
}
