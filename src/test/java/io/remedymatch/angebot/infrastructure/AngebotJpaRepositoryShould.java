package io.remedymatch.angebot.infrastructure;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import lombok.val;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles(profiles = { "test", "disableexternaltasks" })
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("AngebotJpaRepository InMemory Test soll")
public class AngebotJpaRepositoryShould {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private AngebotJpaRepository jpaRepository;

	private InstitutionEntity meinKrankenhaus;
	private InstitutionStandortEntity meinStandort;
	private ArtikelKategorieEntity beispielKategorieArtikel;
	private ArtikelEntity beispielArtikel;
	private ArtikelVarianteEntity beispielArtikelVariante;

	@BeforeEach
	private void prepare() {
		meinKrankenhaus = persist(meinKrankenhaus());
		meinStandort = persist(meinStandort());
		beispielKategorieArtikel = persist(beispielArtikelKategorie());
		beispielArtikel = persist(beispielArtikel());
		beispielArtikelVariante = persist(beispielArtikelVariante());
		entityManager.flush();
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("ArtikelKategorieIds mit Anzahl nicht bediente Angebote zurueckliefern")
	void artikelKategorieIds_mit_Anzahl_nicht_bediente_Angebote_zurueckliefern() {
		// zwei Angebote in erste Kategorie
		persist(angebot(BigDecimal.valueOf(100)));
		persist(angebot(BigDecimal.valueOf(200)));

		// eine Angebot in zweite Kategorie
		val kategorie2 = persist(beispielArtikelKategorie());
		var artikelKategorie2 = beispielArtikel();
		artikelKategorie2.setArtikelKategorie(kategorie2.getId());
		artikelKategorie2 = persist(artikelKategorie2);

		var artikelVarianteKategorie2 = beispielArtikelVariante();
		artikelVarianteKategorie2.setArtikel(artikelKategorie2.getId());
		artikelVarianteKategorie2 = persist(artikelVarianteKategorie2);
		AngebotEntity angebotKategorie2 = angebot(BigDecimal.valueOf(200));
		angebotKategorie2.setArtikelVariante(artikelVarianteKategorie2);
		angebotKategorie2 = persist(angebotKategorie2);

		// keine Angebot in dritte Kategorie
		persist(beispielArtikelKategorie());

		entityManager.flush();

		FilterEntry ersteKategorie = FilterEntry.builder().id(beispielKategorieArtikel.getId()).anzahl(2).build();
		FilterEntry zweiteKategorie = FilterEntry.builder().id(kategorie2.getId()).anzahl(1).build();

		assertThat(//
				jpaRepository.findAllKategorienMitUnbedientenAnbebotenFilter(), //
				containsInAnyOrder(ersteKategorie, zweiteKategorie));
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("ArtikelIds mit Anzahl nicht bediente Angebote zurueckliefern")
	void artikelIds_mit_Anzahl_nicht_bediente_Angebote_zurueckliefern() {
		// zwei Angebote in ersten Artikel
		persist(angebot(BigDecimal.valueOf(100)));
		persist(angebot(BigDecimal.valueOf(200)));

		// eine Angebot in zweiten Artikel
		val artikel2 = persist(beispielArtikel());

		var artikelVarianteKategorie2 = beispielArtikelVariante();
		artikelVarianteKategorie2.setArtikel(artikel2.getId());
		artikelVarianteKategorie2 = persist(artikelVarianteKategorie2);
		AngebotEntity angebotKategorie2 = angebot(BigDecimal.valueOf(200));
		angebotKategorie2.setArtikelVariante(artikelVarianteKategorie2);
		angebotKategorie2 = persist(angebotKategorie2);

		// keine Angebot in dritten Artikel
		persist(beispielArtikel());

		entityManager.flush();

		FilterEntry erstesArtikel = FilterEntry.builder().id(beispielArtikel.getId()).anzahl(2).build();
		FilterEntry zweitesArtikel = FilterEntry.builder().id(artikel2.getId()).anzahl(1).build();

		assertThat(//
				jpaRepository.findAllArtikelInKagegorieMitUnbedientenAnbebotenFilter(beispielKategorieArtikel.getId()), //
				containsInAnyOrder(erstesArtikel, zweitesArtikel));
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle nicht bediente Angebote zurueckliefern")
	void alle_nicht_bediente_Angebote_zurueckliefern() {
		AngebotEntity ersteAngebot = persist(angebot(BigDecimal.valueOf(100)));
		AngebotEntity zweiteAngebot = persist(angebot(BigDecimal.valueOf(200)));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteAngebot, zweiteAngebot), jpaRepository.findAllByDeletedFalseAndBedientFalse());
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle nicht bediente Angebote einer Institution zurueckliefern")
	void alle_nicht_bediente_Angebote_einer_Institution_zurueckliefern() {
		AngebotEntity ersteAngebot = persist(angebot(BigDecimal.valueOf(100)));
		AngebotEntity zweiteAngebot = persist(angebot(BigDecimal.valueOf(200)));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteAngebot, zweiteAngebot),
				jpaRepository.findAllByDeletedFalseAndBedientFalseAndInstitution_Id(meinKrankenhaus.getId()));
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
				.typ(InstitutionTyp.KRANKENHAUS) //
				.build();
	}

	private InstitutionStandortEntity meinStandort() {
		return InstitutionStandortEntity.builder() //
				.name("Mein Standort") //
				.strasse("Strasse") //
				.hausnummer("10a") //
				.plz("PLZ") //
				.ort("Ort") //
				.land("Land") //
				.build();
	}

	private ArtikelKategorieEntity beispielArtikelKategorie() {
		return ArtikelKategorieEntity.builder() //
				.name("beispiel Kategorie") //
				.build();
	}

	private ArtikelEntity beispielArtikel() {
		return ArtikelEntity.builder() //
				.name("egal") //
				.beschreibung("beschreibung") //
				.artikelKategorie(beispielKategorieArtikel.getId()) //
				.build();
	}

	private ArtikelVarianteEntity beispielArtikelVariante() {
		return ArtikelVarianteEntity.builder() //
				.artikel(beispielArtikel.getId()) //
				.variante("egal") //
				.beschreibung("beschreibung") //
				.build();
	}

	private AngebotEntity angebot(//
			BigDecimal anzahl) {
		return AngebotEntity.builder() //
				.artikelVariante(beispielArtikelVariante) //
				.institution(meinKrankenhaus) //
				.standort(meinStandort) //
				.anzahl(anzahl) //
				.rest(anzahl) //
				.haltbarkeit(LocalDateTime.now()) //
				.steril(true) //
				.originalverpackt(true) //
				.medizinisch(true) //
				.kommentar("Bla bla") //
				.bedient(false) //
				.build();
	}
}
