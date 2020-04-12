package io.remedymatch.person.infrastructure;

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
import io.remedymatch.institution.domain.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import lombok.val;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles("test")
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
		val ich = persist(person("ich", meinKrankenhaus, meinStandort));
		entityManager.flush();

		assertEquals(Optional.of(ich), jpaRepository.findByUsername("ich"));
	}

	/* help methods */

	public <E> E persist(E entity) {
		entityManager.persist(entity);
		return entity;
	}

	private InstitutionEntity institution(final InstitutionStandortEntity standort) {
		return InstitutionEntity.builder() //
				.name("Mein Krankenhaus") //
				.institutionKey("mein-krankehnaus") //
				.typ(InstitutionTyp.Krankenhaus) //
				.hauptstandort(standort) //
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

	private PersonEntity person(final String username, final InstitutionEntity institution,
			final InstitutionStandortEntity standort) {
		return PersonEntity.builder() //
				.username(username) //
				.vorname("Vorname") //
				.nachname("Nachname") //
				.telefon("08106112233") //
				.institution(institution) //
				.standort(standort) //
				.build();
	}
}
