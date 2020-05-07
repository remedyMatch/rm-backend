package io.remedymatch.angebot.controller;

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
import io.remedymatch.usercontext.TestUserContext;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

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
        spender = personSucheService.getByUsername(SPENDER_USERNAME);

        PersonEntity suchenderEntity = suchenderAnlegen();
        entityManager.flush();
        suchender = personSucheService.getByUsername(SUCHENDER_USERNAME);

        val artikelVariante = artikelRepository.findAll().get(0).getVarianten().get(0);
        val aritkel = artikelRepository.findAll().get(0);

        TestUserContext.setContextUser(spender);
        AngebotEntity angebot = persist(AngebotEntity.builder() //
                .createdBy(spenderEntity.getId()) //
                .createdDate(LocalDateTime.now()) //
                .artikelVariante(artikelVariante) //
                .artikel(aritkel) //
                .anzahl(BigDecimal.valueOf(1000)) //
                .rest(BigDecimal.valueOf(1000)) //
                .institution(spenderEntity.getAktuellesStandort().getInstitution()) //
                .standort(spenderEntity.getAktuellesStandort().getStandort()) //
                .haltbarkeit(LocalDateTime.of(2020, 12, 24, 18, 0)) //
                .kommentar("ITest Angebot Kommentar") //
                .steril(true) //
                .build());
        TestUserContext.clear();

        angebotId = new AngebotId(angebot.getId());

        TestUserContext.setContextUser(suchender);
        persist(AngebotAnfrageEntity.builder() //
                .createdBy(suchenderEntity.getId()) //
                .createdDate(LocalDateTime.now()) //
                .angebot(angebot) //
                .bedarfId(UUID.randomUUID()) //
                .anzahl(BigDecimal.valueOf(200)) //
                .institution(suchenderEntity.getAktuellesStandort().getInstitution()) //
                .standort(suchenderEntity.getAktuellesStandort().getStandort()) //
                .kommentar("ITest Angebot Anfrage Kommentar") //
                .status(AngebotAnfrageStatus.OFFEN) //
                .build());
        TestUserContext.clear();

        entityManager.flush();

        log.info("Spender: " + spenderEntity);
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

        PersonEntity person = persist(PersonEntity.builder() //
                .username(SPENDER_USERNAME) //
                .vorname("Spender Vorname") //
                .nachname("Spender Nachname") //
                .email("itest_spender@remedymetch.local") //
                .telefon("12345678") //
                .build());
        person.addNeuesAktuellesStandort(institution, standort, true);

        return persist(person);
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

        PersonEntity person = persist(PersonEntity.builder() //
                .username(SUCHENDER_USERNAME) //
                .vorname("Suchender Vorname") //
                .nachname("Suchender Nachname") //
                .email("itest_suchender@remedymetch.local") //
                .telefon("2233445566") //
                .build());
        person.addNeuesAktuellesStandort(institution, standort, false);

        return persist(person);
    }

    private <E> E persist(E entity) {
        entityManager.persist(entity);
        return entity;
    }
}
