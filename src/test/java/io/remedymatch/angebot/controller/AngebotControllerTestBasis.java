package io.remedymatch.angebot.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.artikel.infrastructure.ArtikelJpaRepository;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.service.PersonSucheService;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AngebotControllerTestBasis {

	protected static final String SPENDER_USERNAME = "itest_spender";
	protected static final String SUCHENDER_USERNAME = "itest_suchender";

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	protected ArtikelJpaRepository artikelRepository;

	@Autowired
	private PersonJpaRepository personRepository;

	@Autowired
	private PersonSucheService personSucheService;

	protected Person spender;
	protected Person suchender;

	protected AngebotId angebotId;

	protected void prepareAngebotEntities() {

		PersonEntity spenderEntity = spenderAnlegen();
		entityManager.flush();
		
		PersonEntity suchenderEntity = suchenderAnlegen();
		entityManager.flush();
		
		val artikelVariante = artikelRepository.findAll().get(0).getVarianten().get(0);

		AngebotEntity angebot = persist(AngebotEntity.builder() //
				.createdBy(spenderEntity.getId()) //
				.createdDate(LocalDateTime.now()) //
				.artikelVariante(artikelVariante) //
				.anzahl(BigDecimal.valueOf(1000)) //
				.rest(BigDecimal.valueOf(1000)) //
				.institution(spenderEntity.getInstitution()) //
				.standort(spenderEntity.getStandort()) //
				.haltbarkeit(LocalDateTime.of(2020, 12, 24, 18, 0)) //
				.kommentar("ITest Angebot Kommentar") //
				.steril(true) //
				.build());

		angebotId = new AngebotId(angebot.getId());

		persist(AngebotAnfrageEntity.builder() //
				.createdBy(suchenderEntity.getId()) //
				.createdDate(LocalDateTime.now()) //
				.angebot(angebot) //
				.anzahl(BigDecimal.valueOf(200)) //
				.institution(suchenderEntity.getInstitution()) //
				.standort(suchenderEntity.getStandort()) //
				.kommentar("ITest Angebot Anfrage Kommentar") //
				.prozessInstanzId("test_anfrage_prozess") //
				.status(AngebotAnfrageStatus.OFFEN) //
				.build());

		entityManager.flush();

		spender = personSucheService.getByUsername(SPENDER_USERNAME);
		log.info("Spender: " + spenderEntity);
		
		suchender = personSucheService.getByUsername(SUCHENDER_USERNAME);
		log.info("Suchender: " + suchenderEntity);

		log.info("Angebot: " + angebot);
	}

	protected PersonEntity spenderAnlegen() {

		val spender = personRepository.findOneByUsername(SPENDER_USERNAME);
		if (spender.isPresent()) {
			return spender.get();
		}

		InstitutionStandortEntity standort = persist(InstitutionStandortEntity.builder() //
				.name("ITest Spender Hauptstandort") //
				.strasse("ITest Spender Strasse") //
				.hausnummer("55c") //
				.plz("88456") //
				.ort("ITest Spender Ort") //
				.land("Deutschland") //
				.longitude(BigDecimal.valueOf(123)) //
				.latitude(BigDecimal.valueOf(444)) //
				.build());

		InstitutionEntity institution = persist(InstitutionEntity.builder() //
				.name("ITest Spender Institution") //
				.institutionKey("itest_spender_institution") //
				.typ(InstitutionTyp.GEWERBE_UND_INDUSTRIE) //
				.hauptstandort(standort) //
				.standorte(Arrays.asList(standort)) //
				.build());

		return persist(PersonEntity.builder() //
				.username(SPENDER_USERNAME) //
				.vorname("Spender Vorname") //
				.nachname("Spender Nachname") //
				.email("itest_spender@remedymetch.local") //
				.telefon("12345678") //
				.institution(institution) //
				.standort(standort) //
				.build());
	}

	protected PersonEntity suchenderAnlegen() {

		val suchender = personRepository.findOneByUsername(SUCHENDER_USERNAME);
		if (suchender.isPresent()) {
			return suchender.get();
		}

		InstitutionStandortEntity standort = persist(InstitutionStandortEntity.builder() //
				.name("ITest Suchender Hauptstandort") //
				.strasse("ITest Suchender Strasse") //
				.hausnummer("234") //
				.plz("88456") //
				.ort("ITest Suchender Ort") //
				.land("Deutschland") //
				.longitude(BigDecimal.valueOf(8888)) //
				.latitude(BigDecimal.valueOf(7777)) //
				.build());

		InstitutionEntity institution = persist(InstitutionEntity.builder() //
				.name("ITest Suchender Institution") //
				.institutionKey("itest_suchender_institution") //
				.typ(InstitutionTyp.KRANKENHAUS) //
				.hauptstandort(standort) //
				.standorte(Arrays.asList(standort)) //
				.build());

		return persist(PersonEntity.builder() //
				.username(SUCHENDER_USERNAME) //
				.vorname("Suchender Vorname") //
				.nachname("Suchender Nachname") //
				.email("itest_suchender@remedymetch.local") //
				.telefon("2233445566") //
				.institution(institution) //
				.standort(standort) //
				.build());
	}

	private <E> E persist(E entity) {
		entityManager.persist(entity);
		return entity;
	}
}
