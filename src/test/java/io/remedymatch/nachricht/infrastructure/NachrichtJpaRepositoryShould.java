package io.remedymatch.nachricht.infrastructure;

import io.remedymatch.TestApplication;
import io.remedymatch.nachricht.domain.model.NachrichtReferenzTyp;
import lombok.val;
import org.junit.jupiter.api.Disabled;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles(profiles = {"test", "disableexternaltasks"})
@Tag("InMemory")
@Tag("SpringBoot")
@Disabled
@DisplayName("NachrichtJpaRepository soll")
public class NachrichtJpaRepositoryShould {
    @Autowired
    private NachrichtJpaRepository jpaRepository;

    @Autowired
    private KonversationJpaRepository konversationJpaRepository;

    @Test
    @Transactional
    @Rollback(true)
    @DisplayName("Artikel anhand Teil von Name suchen koennen")
    public void nachrichten_speichern_und_anhand_der_referenz_id_suchen() {

        val konversationId = UUID.randomUUID();
        val konversation = konversationJpaRepository.save(konversation(konversationId));

        val referenzId = UUID.randomUUID();
        jpaRepository.save(nachricht("Meine 1te Nachricht", referenzId, konversationId));
        jpaRepository.save(nachricht("Meine 2te Nachricht", referenzId, konversationId));
        jpaRepository.save(nachricht("Meine 3te Nachricht", referenzId, konversationId));

        List<NachrichtEntity> nachrichtenFuerReferenzId = jpaRepository.findAll();

        assertEquals(3, nachrichtenFuerReferenzId.size());
    }

    private NachrichtEntity nachricht(final String nachricht, UUID referenzId, UUID konversation) {
        return NachrichtEntity.builder()
                .nachricht(nachricht)
                .erstellerInstitution(referenzId)
                .konversation(konversation)
                .build();
    }

    private KonversationEntity konversation(UUID id) {
        return KonversationEntity.builder()
                .id(id)
                .referenzId(UUID.randomUUID())
                .referenzTyp(NachrichtReferenzTyp.ANGEBOT_ANFRAGE)
                .build();
    }
}
