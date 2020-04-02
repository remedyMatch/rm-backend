package io.remedymatch.bedarf.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

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
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.institution.domain.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles("test")
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("BedarfJpaRepository InMemory Test soll")
public class BedarfJpaRepositoryShould {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private BedarfJpaRepository jpaRepository;

	private InstitutionEntity meinKrankenhaus;
	private InstitutionStandortEntity meinStandort;
	private ArtikelEntity beispielArtikel;
	
	@BeforeEach
	private void prepare()
	{
		meinKrankenhaus = persist(meinKrankenhaus());
		meinStandort = persist(meinStandort());
		beispielArtikel = persist(beispielArtikel());
		entityManager.flush();
	}
	
	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle nicht bediente Bedarfe zurueckliefern")
	void alle_nicht_bediente_Bedarfe_zurueckliefern() {
		BedarfEntity ersteBedarf = persist(bedarf(BigDecimal.valueOf(100)));
		BedarfEntity zweiteBedarf = persist(bedarf(BigDecimal.valueOf(200)));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteBedarf, zweiteBedarf), jpaRepository.findAllByBedientFalse());
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
	
	private InstitutionStandortEntity meinStandort() {
		return InstitutionStandortEntity.builder() //
				.name("Mein Standort") //
				.plz("PLZ") //
				.ort("Ort") //
				.strasse("Strasse") //
				.land("Land") //
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
	
	private BedarfEntity bedarf(//
			BigDecimal anzahl) {
		return BedarfEntity.builder() //
				.artikel(beispielArtikel) //
				.institution(meinKrankenhaus) //
				.standort(meinStandort) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.steril(true) //
				.originalverpackt(true) //
				.medizinisch(true) //
				.kommentar("Bla bla") //
				.bedient(false) //
				.build();
	}
}
