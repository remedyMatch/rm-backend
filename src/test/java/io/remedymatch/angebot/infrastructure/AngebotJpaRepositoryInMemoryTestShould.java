package io.remedymatch.angebot.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import io.remedymatch.artikel.domain.ArtikelEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles("test")
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("AngebotJpaRepository InMemory Test soll")
public class AngebotJpaRepositoryInMemoryTestShould {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private AngebotJpaRepository jpaRepository;

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle nicht bediente Angebote zurueckliefern")
	void alle_nicht_bediente_Angebote_zurueckliefern() {
		ArtikelEntity beispielArtikel = persist(beispielArtikel());
		AngebotEntity ersteAngebot = persist(angebot(beispielArtikel, BigDecimal.valueOf(100)));
		AngebotEntity zweiteAngebot = persist(angebot(beispielArtikel, BigDecimal.valueOf(200)));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteAngebot, zweiteAngebot), jpaRepository.findAllByBedientFalse());
	}

	/* help methods */

	public <E> E persist(E entity) {
		entityManager.persist(entity);
		return entity;
	}

	private AngebotEntity angebot(//
			ArtikelEntity artikel,
			BigDecimal anzahl) {
		return AngebotEntity.builder() //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.artikel(artikel) //
				.haltbarkeit(LocalDateTime.now()) //
				.steril(true) //
				.originalverpackt(true) //
				.medizinisch(true) //
				.kommentar("Bla bla") //
				.bedient(false) //
				.build();
	}

	private ArtikelEntity beispielArtikel() {
		return ArtikelEntity.builder() //
				.ean("EAN") //
				.name("egal") //
				.beschreibung("beschreibung") //
				.hersteller("hersteller") //
				.build();
	}
}
