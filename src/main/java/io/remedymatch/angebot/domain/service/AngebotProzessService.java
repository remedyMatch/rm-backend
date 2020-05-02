package io.remedymatch.angebot.domain.service;

import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.engine.domain.MessageKey;
import io.remedymatch.engine.domain.ProzessInstanzId;
import io.remedymatch.engine.domain.ProzessKey;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.person.domain.model.PersonId;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Validated
@Service
@Transactional
class AngebotProzessService {

    final static ProzessKey PROZESS_KEY = new ProzessKey("angebot_prozess");
    final static MessageKey ANFRAGE_MESSAGE = new MessageKey("angebot_prozess_anfrage_erhalten");
    final static MessageKey ANFRAGE_STORNIEREN_MESSAGE = new MessageKey("angebot_prozess_anfrage_storniert");
    final static MessageKey REST_ANGEBOT_AENDERN_MESSAGE = new MessageKey("angebot_prozess_rest_geaendert_message");
    final static MessageKey ANGEBOT_UNGUELTIG_MESSAGE = new MessageKey("angebot_prozess_rest_geaendert_message");

    private final EngineClient engineClient;

    void prozessStarten(
            final @NotNull @Valid AngebotId angebotId, //
            final @NotNull @Valid PersonId angebotErsteller, //
            final @NotNull @Valid AngebotAnfrageId anfrageId, //
            final @NotNull @Valid InstitutionId angebotInstitutionId) {

        engineClient.prozessStarten(//
                PROZESS_KEY, //
                new BusinessKey(anfrageId.getValue()), //
                angebotErsteller, //
                Variables.createVariables()//
                        .putValue("prozessKey", PROZESS_KEY.getValue()) //
                        .putValue("angebotId", angebotId.getValue()) //
                        .putValue("institution", angebotInstitutionId.getValue()));
    }

    void restAngebotAendern(final @NotNull @Valid AngebotId angebotId) {
        engineClient.messageKorrelieren(//
                PROZESS_KEY, //
                ANFRAGE_STORNIEREN_MESSAGE, //
                Variables.createVariables().putValue("angebotId", angebotId.getValue()));
    }

    void angebotSchliessen(final @NotNull @Valid ProzessInstanzId prozessInstanzId) {
        engineClient.messageKorrelieren(//
                PROZESS_KEY, //
                prozessInstanzId, //
                ANFRAGE_STORNIEREN_MESSAGE);
    }
}
