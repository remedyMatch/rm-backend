package io.remedymatch.bedarf.controller;

import io.remedymatch.artikel.infrastructure.ArtikelJpaRepository;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
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
public abstract class BedarfControllerTestBasis {

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

    protected BedarfId bedarfId;

    protected void prepareBedarfEntities() {

        PersonEntity spenderEntity = spenderAnlegen();
        entityManager.flush();
        spender = personSucheService.getByUsername(SPENDER_USERNAME);

        PersonEntity suchenderEntity = suchenderAnlegen();
        entityManager.flush();
        suchender = personSucheService.getByUsername(SUCHENDER_USERNAME);

        val artikel = artikelRepository.findAll().get(0);

        TestUserContext.setContextUser(spender);
        BedarfEntity bedarf = persist(BedarfEntity.builder() //
                .createdBy(spenderEntity.getId()) //
                .createdDate(LocalDateTime.now()) //
                .artikel(artikel) //
                .anzahl(BigDecimal.valueOf(1000)) //
                .rest(BigDecimal.valueOf(1000)) //
                .institution(spenderEntity.getAktuellesStandort().getInstitution()) //
                .standort(spenderEntity.getAktuellesStandort().getStandort()) //
                .kommentar("ITest Bedarf Kommentar") //
                .steril(true) //
                .build());
        TestUserContext.clear();

        bedarfId = new BedarfId(bedarf.getId());

        TestUserContext.setContextUser(suchender);
        persist(BedarfAnfrageEntity.builder() //
                .createdBy(suchenderEntity.getId()) //
                .createdDate(LocalDateTime.now()) //
                .bedarf(bedarf) //
                .angebotId(UUID.randomUUID()) //
                .anzahl(BigDecimal.valueOf(200)) //
                .institution(suchenderEntity.getAktuellesStandort().getInstitution()) //
                .standort(suchenderEntity.getAktuellesStandort().getStandort()) //
                .kommentar("ITest Bedarf Anfrage Kommentar") //
                .status(BedarfAnfrageStatus.OFFEN) //
                .build());
        TestUserContext.clear();

        entityManager.flush();

        log.info("Spender: " + spenderEntity);
        log.info("Suchender: " + suchenderEntity);
        log.info("Bedarf: " + bedarf);
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
