package io.remedymatch.angebot.infrastructure;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

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
import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
import io.remedymatch.institution.domain.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

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

	private InstitutionEntity meinKrankenhaus;
	private InstitutionStandortEntity meinStandort;
	private ArtikelKategorieEntity beispielKategorieArtikel;
	private ArtikelEntity beispielArtikel;
	private ArtikelVarianteEntity beispielArtikelVariante;
	private AngebotEntity beispielAngebot;

	@BeforeEach
	private void prepare() {
		meinKrankenhaus = persist(meinKrankenhaus());
		meinStandort = persist(meinStandort());
		beispielKategorieArtikel = persist(beispielArtikelKategorie());
		beispielArtikel = persist(beispielArtikel());
		beispielArtikelVariante = persist(beispielArtikelVariante());
		beispielAngebot = persist(beispielAngebot());
		entityManager.flush();
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen fuer InstitutionAn Id zurueckliefern")
	void alle_Anfragen_fuer_InstitutionAnId_zurueckliefern() {
		AngebotAnfrageEntity ersteAnfrage = persist(
				angebotAnfrageFuerAngebot(beispielAngebot, BigDecimal.valueOf(100)));
		AngebotAnfrageEntity zweiteAnfrage = persist(
				angebotAnfrageFuerAngebot(beispielAngebot, BigDecimal.valueOf(200)));
		entityManager.flush();

		assertThat(//
				jpaRepository.findAllByAngebot_Institution_Id(meinKrankenhaus.getId()), //
				containsInAnyOrder(ersteAnfrage, zweiteAnfrage));
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen fuer InstitutionVon Id zurueckliefern")
	void alle_Anfragen_fuer_InstitutionVon_Id_zurueckliefern() {
		AngebotAnfrageEntity ersteAnfrage = persist(
				angebotAnfrageVonInstitution(meinKrankenhaus, BigDecimal.valueOf(100)));
		AngebotAnfrageEntity zweiteAnfrage = persist(
				angebotAnfrageVonInstitution(meinKrankenhaus, BigDecimal.valueOf(200)));
		entityManager.flush();

		assertThat(//
				jpaRepository.findAllByInstitutionVon_Id(meinKrankenhaus.getId()), //
				containsInAnyOrder(ersteAnfrage, zweiteAnfrage));
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("Status der Anfragen aktualisieren")
	void status_der_Anfragen_aktualisieren() {
		AngebotAnfrageEntity ersteOffeneAnfrage = persist(angebotAnfrage(beispielAngebot, AngebotAnfrageStatus.Offen));
		AngebotAnfrageEntity zweiteOffeneAnfrage = persist(angebotAnfrage(beispielAngebot, AngebotAnfrageStatus.Offen));
		AngebotAnfrageEntity dritteAnfrageStorniert = persist(
				angebotAnfrage(beispielAngebot, AngebotAnfrageStatus.Angenommen));
		entityManager.flush();

		assertEquals(Optional.of(ersteOffeneAnfrage), jpaRepository.findById(ersteOffeneAnfrage.getId()));
		assertEquals(Optional.of(zweiteOffeneAnfrage), jpaRepository.findById(zweiteOffeneAnfrage.getId()));
		assertEquals(Optional.of(dritteAnfrageStorniert), jpaRepository.findById(dritteAnfrageStorniert.getId()));

		jpaRepository.updateStatus(beispielAngebot.getId(), AngebotAnfrageStatus.Offen, AngebotAnfrageStatus.Storniert);

		entityManager.flush();
		entityManager.clear();

		assertEquals(AngebotAnfrageStatus.Storniert,
				jpaRepository.findById(ersteOffeneAnfrage.getId()).get().getStatus());
		assertEquals(AngebotAnfrageStatus.Storniert,
				jpaRepository.findById(zweiteOffeneAnfrage.getId()).get().getStatus());
		assertEquals(AngebotAnfrageStatus.Angenommen,
				jpaRepository.findById(dritteAnfrageStorniert.getId()).get().getStatus());
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

	private ArtikelKategorieEntity beispielArtikelKategorie() {
		return ArtikelKategorieEntity.builder() //
				.name("beispiel Kategorie") //
				.icon("icon") //
				.build();
	}

	private ArtikelEntity beispielArtikel() {
		return ArtikelEntity.builder() //
				.artikelKategorie(beispielKategorieArtikel.getId()) //
				.name("egal") //
				.beschreibung("beschreibung") //
				.build();
	}

	private ArtikelVarianteEntity beispielArtikelVariante() {
		return ArtikelVarianteEntity.builder() //
				.artikel(beispielArtikel.getId()) //
				.variante("egal") //
				.norm("egal") //
				.beschreibung("beschreibung") //
				.build();
	}

	private AngebotEntity beispielAngebot() {
		return AngebotEntity.builder() //
				.artikelVariante(beispielArtikelVariante) //
				.institution(meinKrankenhaus) //
				.standort(meinStandort) //
				.anzahl(BigDecimal.valueOf(100)) //
				.rest(BigDecimal.valueOf(100)) //
				.institution(meinKrankenhaus) //
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
				.angebot(angebot) //
				.institutionVon(meinKrankenhaus) //
				.standortVon(meinStandort) //
				.anzahl(BigDecimal.valueOf(50)) //
				.kommentar("Bla bla") //
				.status(status) //
				.build();
	}

	private AngebotAnfrageEntity angebotAnfrageFuerAngebot(//
			AngebotEntity angebot, //
			BigDecimal anzahl) {
		return AngebotAnfrageEntity.builder() //
				.angebot(angebot)//
				.institutionVon(meinKrankenhaus) //
				.standortVon(meinStandort) //
				.anzahl(anzahl) //
				.kommentar("Bla bla") //
				.status(AngebotAnfrageStatus.Offen) //
				.build();
	}

	private AngebotAnfrageEntity angebotAnfrageVonInstitution(//
			InstitutionEntity institutionVon, //
			BigDecimal anzahl) {
		return AngebotAnfrageEntity.builder() //
				.angebot(beispielAngebot) //
				.institutionVon(institutionVon)//
				.standortVon(meinStandort) //
				.anzahl(anzahl) //
				.kommentar("Bla bla") //
				.status(AngebotAnfrageStatus.Offen) //
				.build();
	}
}
