package io.remedymatch.nachricht.domain.service;

import io.remedymatch.nachricht.domain.model.Nachricht;
import io.remedymatch.nachricht.domain.model.NachrichtReferenz;
import io.remedymatch.nachricht.domain.model.NeueNachricht;
import io.remedymatch.nachricht.infrastructure.NachrichtEntity;
import io.remedymatch.nachricht.infrastructure.NachrichtJpaRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static io.remedymatch.nachricht.domain.service.NachrichtEntityConverter.convert;

@AllArgsConstructor
@Service
@Validated
@Transactional
public class NachrichtService {

    private final NachrichtJpaRepository nachrichtRepository;

    public void nachrichtSenden(final @Valid NeueNachricht neueNachricht) {

        val nachricht = NachrichtEntity.builder()
                .nachricht(neueNachricht.getNachricht())
                .referenzTyp(neueNachricht.getReferenzTyp())
                .referenzId(neueNachricht.getReferenzId().getValue())
                .build();

        nachrichtRepository.save(nachricht);

        //TODO Benachrichtigung senden
    }

    public List<Nachricht> nachrichtenZuReferenzLaden(final @NotNull NachrichtReferenz referenz) {
        val nachrichten = nachrichtRepository.findAllByReferenzId(referenz.getValue());
        return convert(nachrichten);
    }

}
