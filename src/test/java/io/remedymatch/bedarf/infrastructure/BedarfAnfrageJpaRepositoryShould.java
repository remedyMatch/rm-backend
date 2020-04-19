package io.remedymatch.bedarf.infrastructure;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
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
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import lombok.val;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles(profiles = {"test", "disableexternaltasks"})
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("BedarfAnfrageJpaRepository soll")
public class BedarfAnfrageJpaRepositoryShould {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private BedarfAnfrageJpaRepository jpaRepository;

	private InstitutionEntity meinKrankenhaus;
	private InstitutionStandortEntity meinStandort;
	private ArtikelKategorieEntity beispielKategorieArtikel;
	private ArtikelEntity beispielArtikel;
	private ArtikelVarianteEntity beispielArtikelVariante;
	private BedarfEntity beispielBedarf;

	@BeforeEach
	private void prepare() {
		meinKrankenhaus = persist(meinKrankenhaus());
		meinStandort = persist(meinStandort());
		beispielKategorieArtikel = persist(beispielArtikelKategorie());
		beispielArtikel = persist(beispielArtikel());
		beispielArtikelVariante = persist(beispielArtikelVariante());
		beispielBedarf = persist(beispielBedarf());
		entityManager.flush();
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen der Institution zurueckliefern")
	void alle_Anfragen_der_Institution_zurueckliefern() {
		BedarfAnfrageEntity ersteAnfrage = persist(bedarfAnfrageFuerBedarf(beispielBedarf, BigDecimal.valueOf(100)));
		BedarfAnfrageEntity zweiteAnfrage = persist(bedarfAnfrageFuerBedarf(beispielBedarf, BigDecimal.valueOf(200)));
		entityManager.flush();

		assertThat(//
				jpaRepository.findAllByBedarf_Institution_Id(meinKrankenhaus.getId()), //
				containsInAnyOrder(ersteAnfrage, zweiteAnfrage));
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen der Bedarf Institution zurueckliefern")
	void alle_Anfragen_der_Bedarf_Institution_zurueckliefern() {
		BedarfAnfrageEntity ersteAnfrage = persist(
				bedarfAnfrageVonInstitution(meinKrankenhaus, BigDecimal.valueOf(100)));
		BedarfAnfrageEntity zweiteAnfrage = persist(
				bedarfAnfrageVonInstitution(meinKrankenhaus, BigDecimal.valueOf(200)));
		entityManager.flush();

		assertThat(//
				jpaRepository.findAllByInstitution_Id(meinKrankenhaus.getId()), //
				containsInAnyOrder(ersteAnfrage, zweiteAnfrage));
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("Offene Anfrage fuer BedarfId und AnfrageId zurueckliefern")
	void offene_Anfrage_fuer_BedarfId_und_AnfrageId_zurueckliefern() {
		val anfrage = persist(bedarfAnfrageVonInstitution(meinKrankenhaus, BigDecimal.valueOf(100)));
		entityManager.flush();

		assertEquals(Optional.of(anfrage), jpaRepository.findByBedarfIdAndAnfrageIdAndStatusOffen(//
				beispielBedarf.getId(), //
				anfrage.getId()));
	}
	
	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("Status der Anfragen aktualisieren")
	void status_der_Anfragen_aktualisieren() {
		BedarfAnfrageEntity ersteOffeneAnfrage = persist(bedarfAnfrage(beispielBedarf, BedarfAnfrageStatus.OFFEN));
		BedarfAnfrageEntity zweiteOffeneAnfrage = persist(bedarfAnfrage(beispielBedarf, BedarfAnfrageStatus.OFFEN));
		BedarfAnfrageEntity dritteAnfrageStorniert = persist(
				bedarfAnfrage(beispielBedarf, BedarfAnfrageStatus.ANGENOMMEN));
		entityManager.flush();

		assertEquals(Optional.of(ersteOffeneAnfrage), jpaRepository.findById(ersteOffeneAnfrage.getId()));
		assertEquals(Optional.of(zweiteOffeneAnfrage), jpaRepository.findById(zweiteOffeneAnfrage.getId()));
		assertEquals(Optional.of(dritteAnfrageStorniert), jpaRepository.findById(dritteAnfrageStorniert.getId()));

		jpaRepository.updateStatus(beispielBedarf.getId(), BedarfAnfrageStatus.OFFEN, BedarfAnfrageStatus.STORNIERT);

		entityManager.flush();
		entityManager.clear();

		assertEquals(BedarfAnfrageStatus.STORNIERT,
				jpaRepository.findById(ersteOffeneAnfrage.getId()).get().getStatus());
		assertEquals(BedarfAnfrageStatus.STORNIERT,
				jpaRepository.findById(zweiteOffeneAnfrage.getId()).get().getStatus());
		assertEquals(BedarfAnfrageStatus.ANGENOMMEN,
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

	private BedarfEntity beispielBedarf() {
		return BedarfEntity.builder() //
				.artikel(beispielArtikel) //
				.artikelVariante(beispielArtikelVariante) //
				.institution(meinKrankenhaus) //
				.standort(meinStandort) //
				.anzahl(BigDecimal.valueOf(100)) //
				.rest(BigDecimal.valueOf(100)) //
				.institution(meinKrankenhaus) //
				.steril(true) //
				.medizinisch(true) //
				.kommentar("Bla bla") //
				.bedient(false) //
				.build();
	}

	private BedarfAnfrageEntity bedarfAnfrage(//
			BedarfEntity bedarf, //
			BedarfAnfrageStatus status) {
		return BedarfAnfrageEntity.builder() //
				.bedarf(bedarf) //
				.institution(meinKrankenhaus) //
				.standort(meinStandort) //
				.anzahl(BigDecimal.valueOf(50)) //
				.kommentar("Bla bla") //
				.status(status) //
				.build();
	}

	private BedarfAnfrageEntity bedarfAnfrageFuerBedarf(//
			BedarfEntity bedarf, //
			BigDecimal anzahl) {
		return BedarfAnfrageEntity.builder() //
				.bedarf(bedarf)//
				.institution(meinKrankenhaus) //
				.standort(meinStandort) //
				.anzahl(anzahl) //
				.kommentar("Bla bla") //
				.status(BedarfAnfrageStatus.OFFEN) //
				.build();
	}

	private BedarfAnfrageEntity bedarfAnfrageVonInstitution(//
			InstitutionEntity institutionVon, //
			BigDecimal anzahl) {
		return BedarfAnfrageEntity.builder() //
				.bedarf(beispielBedarf) //
				.institution(institutionVon)//
				.standort(meinStandort) //
				.anzahl(anzahl) //
				.kommentar("Bla bla") //
				.status(BedarfAnfrageStatus.OFFEN) //
				.build();
	}
}
