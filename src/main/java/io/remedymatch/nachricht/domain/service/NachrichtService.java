package io.remedymatch.nachricht.domain.service;

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.nachricht.domain.model.*;
import io.remedymatch.nachricht.infrastructure.*;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Validated
@Transactional
public class NachrichtService {

    private final NachrichtJpaRepository nachrichtRepository;
    private final KonversationJpaRepository konversationJpaRepository;
    private final UserContextService userContextService;

    public void nachrichtSenden(final @NotNull KonversationId konversationId, final @Valid NeueNachricht neueNachricht) {

        val konversation = konversationJpaRepository.findByIdAndInstitutionId(konversationId.getValue(), userContextService.getContextInstitutionId().getValue())
                .orElseThrow(() -> new ObjectNotFoundException("Die Konversation zu der eine Nachricht gesendet werden soll existiert nicht."));

        var nachricht = NachrichtEntity.builder()
                .nachricht(neueNachricht.getNachricht())
                .konversation(konversation.getId())
                .erstellerInstitution(userContextService.getContextInstitutionId().getValue())
                .build();

        nachricht = nachrichtRepository.save(nachricht);

        konversation.getNachrichten().add(nachricht);
        konversationJpaRepository.save(konversation);

        //TODO Benachrichtigung senden
    }

    public Konversation konversationLaden(final @NotNull KonversationId konversationId) {
        val konversation = konversationJpaRepository.findByIdAndInstitutionId(konversationId.getValue(), userContextService.getContextInstitutionId().getValue())
                .orElseThrow(() -> new ObjectNotFoundException("Die angegebene Konversation konnte nicht gefunden werden"));
        return KonversationEntityConverter.convert(konversation);
    }

    public List<Konversation> konversationenZuInstitutionLaden(final @NotNull InstitutionId institutionId) {
        val konversationen = konversationJpaRepository.findAllByInstitutionId(institutionId.getValue());
        return KonversationEntityConverter.convert(konversationen);
    }

    public List<Konversation> beteiligteKonversationenLaden() {
        val konversationen = konversationJpaRepository.findAllByInstitutionId(userContextService.getContextInstitutionId().getValue());
        return KonversationEntityConverter.convert(konversationen);
    }

    public void konversationStarten(final @NotNull NachrichtReferenz referenz, @NotNull NachrichtReferenzTyp referenzTyp, @NotNull String nachricht, List<InstitutionEntity> beteiligte) {

        var konversation = konversationJpaRepository.save(KonversationEntity.builder()
                .referenzId(referenz.getValue())
                .referenzTyp(referenzTyp)
                .build());

        konversation.getBeteiligte().addAll(beteiligte.stream().map(b -> this.mapBeteiligter(b, konversation.getId())).collect(Collectors.toList()));
        konversationJpaRepository.save(konversation);

        this.nachrichtSenden(new KonversationId(konversation.getId()), NeueNachricht.builder().nachricht(nachricht).build());
    }

    private Konversation2InstitutionEntity mapBeteiligter(final InstitutionEntity beteiligter, final UUID konversation) {
        return Konversation2InstitutionEntity.builder()
                .institution(beteiligter)
                .konversation(konversation)
                .build();
    }

}
