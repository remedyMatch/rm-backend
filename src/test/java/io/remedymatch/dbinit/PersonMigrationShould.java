package io.remedymatch.dbinit;

import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortJpaRepository;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import io.remedymatch.person.infrastructure.PersonStandortEntity;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"dbmigration", "test"})
@Tag("InMemory")
@Tag("SpringBoot")
public class PersonMigrationShould {

    @Autowired
    private PersonMigration personMigration;

    @Autowired
    private PersonJpaRepository personRepository;

    @Autowired
    private InstitutionJpaRepository institutionRepository;

    @Autowired
    private InstitutionStandortJpaRepository standortRepository;

    @Test
    @Transactional
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void kategorienUndArtikelSolltenAngelegtWerden() {

        InstitutionStandortEntity standort = standortRepository.save(InstitutionStandortEntity.builder() //
                .name("ITest Spender Hauptstandort") //
                .strasse("ITest Spender Strasse") //
                .hausnummer("55c") //
                .plz("88456") //
                .ort("ITest Spender Ort") //
                .land("Deutschland") //
                .longitude(BigDecimal.valueOf(123)) //
                .latitude(BigDecimal.valueOf(444)) //
                .build());

        InstitutionEntity institution = institutionRepository.save(InstitutionEntity.builder() //
                .name("ITest Spender Institution") //
                .institutionKey("itest_spender_institution") //
                .typ(InstitutionTyp.GEWERBE_UND_INDUSTRIE) //
                .standorte(Arrays.asList(standort)) //
                .build());

        PersonEntity personVorMgration = personRepository.save(PersonEntity.builder() //
                .username("samplePerson") //
                .vorname("Sample Vorname") //
                .nachname("Sample Nachname") //
                .email("itest_user@remedymetch.local") //
                .telefon("12345678") //
                .institutionId(institution.getId()) //
                .standortId(standort.getId()) //
                .build());

        personMigration.onApplicationEvent(Mockito.mock(ContextRefreshedEvent.class));

        PersonEntity personNachMigration = personRepository.getOne(personVorMgration.getId());

        PersonStandortEntity aktuellesStandort = personNachMigration.getAktuellesStandort();
        assertNotNull(aktuellesStandort);
        assertEquals(institution, aktuellesStandort.getInstitution());
        assertEquals(standort, aktuellesStandort.getStandort());
    }
}
