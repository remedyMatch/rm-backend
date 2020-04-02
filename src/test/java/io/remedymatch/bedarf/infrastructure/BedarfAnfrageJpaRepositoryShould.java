package io.remedymatch.bedarf.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
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
import io.remedymatch.bedarf.domain.BedarfAnfrageStatus;
import io.remedymatch.institution.domain.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles("test")
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
	private BedarfEntity beispielBedarf;

	@BeforeEach
	private void prepare() {
		meinKrankenhaus = persist(meinKrankenhaus());
		meinStandort = persist(meinStandort());
		beispielKategorieArtikel = persist(beispielArtikelKategorie());
		beispielArtikel = persist(beispielArtikel());
		beispielBedarf = persist(beispielBedarf());
		entityManager.flush();
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen fuer InstitutionAn Id zurueckliefern")
	void alle_Anfragen_fuer_InstitutionAnId_zurueckliefern() {
		BedarfAnfrageEntity ersteAnfrage = persist(bedarfAnfrageFuerBedarf(beispielBedarf, BigDecimal.valueOf(100)));
		BedarfAnfrageEntity zweiteAnfrage = persist(bedarfAnfrageFuerBedarf(beispielBedarf, BigDecimal.valueOf(200)));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteAnfrage, zweiteAnfrage),
				jpaRepository.findAllByBedarf_Institution_Id(meinKrankenhaus.getId()));
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("alle Anfragen fuer InstitutionVon Id zurueckliefern")
	void alle_Anfragen_fuer_InstitutionVon_Id_zurueckliefern() {
		BedarfAnfrageEntity ersteAnfrage = persist(
				bedarfAnfrageVonInstitution(meinKrankenhaus, BigDecimal.valueOf(100)));
		BedarfAnfrageEntity zweiteAnfrage = persist(
				bedarfAnfrageVonInstitution(meinKrankenhaus, BigDecimal.valueOf(200)));
		entityManager.flush();

		assertEquals(Arrays.asList(ersteAnfrage, zweiteAnfrage),
				jpaRepository.findAllByInstitutionVon_Id(meinKrankenhaus.getId()));
	}

	@Rollback(true)
	@Transactional
	@Test
	@DisplayName("Status der Anfragen aktualisieren")
	void status_der_Anfragen_aktualisieren() {
		BedarfAnfrageEntity ersteOffeneAnfrage = persist(bedarfAnfrage(beispielBedarf, BedarfAnfrageStatus.Offen));
		BedarfAnfrageEntity zweiteOffeneAnfrage = persist(bedarfAnfrage(beispielBedarf, BedarfAnfrageStatus.Offen));
		BedarfAnfrageEntity dritteAnfrageStorniert = persist(
				bedarfAnfrage(beispielBedarf, BedarfAnfrageStatus.Angenommen));
		entityManager.flush();

		assertEquals(Optional.of(ersteOffeneAnfrage), jpaRepository.findById(ersteOffeneAnfrage.getId()));
		assertEquals(Optional.of(zweiteOffeneAnfrage), jpaRepository.findById(zweiteOffeneAnfrage.getId()));
		assertEquals(Optional.of(dritteAnfrageStorniert), jpaRepository.findById(dritteAnfrageStorniert.getId()));

		jpaRepository.updateStatus(beispielBedarf.getId(), BedarfAnfrageStatus.Offen, BedarfAnfrageStatus.Storniert);

		entityManager.flush();
		entityManager.clear();

		assertEquals(BedarfAnfrageStatus.Storniert,
				jpaRepository.findById(ersteOffeneAnfrage.getId()).get().getStatus());
		assertEquals(BedarfAnfrageStatus.Storniert,
				jpaRepository.findById(zweiteOffeneAnfrage.getId()).get().getStatus());
		assertEquals(BedarfAnfrageStatus.Angenommen,
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
				.build();
	}

	private ArtikelEntity beispielArtikel() {
		return ArtikelEntity.builder() //
				.ean("EAN") //
				.name("egal") //
				.beschreibung("beschreibung") //
				.hersteller("hersteller") //
				.artikelKategorie(beispielKategorieArtikel) //
				.build();
	}

	private BedarfEntity beispielBedarf() {
		return BedarfEntity.builder() //
				.artikel(beispielArtikel) //
				.institution(meinKrankenhaus) //
				.standort(meinStandort) //
				.anzahl(BigDecimal.valueOf(100)) //
				.rest(BigDecimal.valueOf(100)) //
				.institution(meinKrankenhaus) //
				.steril(true) //
				.originalverpackt(true) //
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
				.institutionVon(meinKrankenhaus) //
				.standortVon(meinStandort) //
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
				.institutionVon(meinKrankenhaus) //
				.standortVon(meinStandort) //
				.anzahl(anzahl) //
				.kommentar("Bla bla") //
				.status(BedarfAnfrageStatus.Offen) //
				.build();
	}

	private BedarfAnfrageEntity bedarfAnfrageVonInstitution(//
			InstitutionEntity institutionVon, //
			BigDecimal anzahl) {
		return BedarfAnfrageEntity.builder() //
				.bedarf(beispielBedarf) //
				.institutionVon(institutionVon)//
				.standortVon(meinStandort) //
				.anzahl(anzahl) //
				.kommentar("Bla bla") //
				.status(BedarfAnfrageStatus.Offen) //
				.build();
	}
}
