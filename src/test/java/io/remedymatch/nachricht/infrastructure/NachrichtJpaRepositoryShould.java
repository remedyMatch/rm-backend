package io.remedymatch.nachricht.infrastructure;

import io.remedymatch.TestApplication;
import io.remedymatch.nachricht.domain.model.NachrichtReferenzTyp;
import lombok.val;
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
@DisplayName("NachrichtJpaRepository soll")
public class NachrichtJpaRepositoryShould {
    @Autowired
    private NachrichtJpaRepository jpaRepository;

    @Test
    @Transactional
    @Rollback(true)
    @DisplayName("Artikel anhand Teil von Name suchen koennen")
    public void nachrichten_speichern_und_anhand_der_referenz_id_suchen() {

        val referenzId = UUID.randomUUID();
        val nachricht1Id = jpaRepository.save(nachricht("Meine 1te Nachricht", referenzId)).getId();
        val nachricht2Id = jpaRepository.save(nachricht("Meine 2te Nachricht", referenzId)).getId();
        val nachricht3Id = jpaRepository.save(nachricht("Meine 3te Nachricht", referenzId)).getId();

        val expectedNachricht1 = nachricht("Meine 1te Nachricht", referenzId);
        expectedNachricht1.setId(nachricht1Id);
        val expectedNachricht2 = nachricht("Meine 2te Nachricht", referenzId);
        expectedNachricht2.setId(nachricht2Id);
        val expectedNachricht3 = nachricht("Meine 3te Nachricht", referenzId);
        expectedNachricht3.setId(nachricht3Id);

        List<NachrichtEntity> nachrichtenFuerReferenzId = jpaRepository.findAllByReferenzId(referenzId);

        assertEquals(3, nachrichtenFuerReferenzId.size());
    }

    private NachrichtEntity nachricht(final String nachricht, UUID referenzId) {
        return NachrichtEntity.builder()
                .referenzId(referenzId)
                .nachricht(nachricht)
                .referenzTyp(NachrichtReferenzTyp.ANGEBOT_ANFRAGE)
                .build();
    }
}
