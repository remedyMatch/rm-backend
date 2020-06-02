package io.remedymatch.bedarf.domain.service;

import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.engine.domain.MessageKey;
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
import java.math.BigDecimal;

@AllArgsConstructor
@Validated
@Service
@Transactional
class BedarfProzessService {

    final static ProzessKey PROZESS_KEY = new ProzessKey("bedarf_prozess");
    final static MessageKey ANFRAGE_MESSAGE = new MessageKey("bedarf_prozess_anfrage_erhalten_message");
    final static MessageKey ANFRAGE_BEARBEITET_MESSAGE = new MessageKey("bedarf_prozess_anfrage_bearbeitet_message");
    final static MessageKey ANFRAGE_STORNIEREN_MESSAGE = new MessageKey("bedarf_prozess_anfrage_storniert_message");
    final static MessageKey REST_BEDARF_AENDERN_MESSAGE = new MessageKey("bedarf_prozess_rest_geaendert_message");
    final static MessageKey BEDARF_UNGUELTIG_MESSAGE = new MessageKey("bedarf_prozess_rest_geaendert_message");
    final static MessageKey BEDARF_SCHLIESSEN_MESSAGE = new MessageKey("bedarf_prozess_geschlossen_message");
    final static String VAR_ANFRAGE_ID = "anfrage_id";
    final static String VAR_ANZAHL = "bedarf_anzahl";
    final static String VAR_BEDARF_GESCHLOSSEN = "bedarf_geschlossen";
    final static String VAR_BEDARF_ANGENOMMEN = "anfrage_angenommen";
    final static String VAR_ANFRAGE_ANGEBOT_ID = "anfrage_angebot_id";

    private final EngineClient engineClient;

    void prozessStarten(
            final @NotNull @Valid BedarfId angebotId, //
            final @NotNull @Valid PersonId angebotErsteller, //
            final @NotNull @Valid InstitutionId angebotInstitutionId) {

        engineClient.prozessStarten(//
                PROZESS_KEY, //
                new BusinessKey(angebotId.getValue()), //
                angebotErsteller, //
                Variables.createVariables()//
                        .putValue("prozessKey", PROZESS_KEY.getValue()) //
                        .putValue("angebotId", angebotId.getValue()) //
                        .putValue("institution", angebotInstitutionId.getValue()));
    }

    void restBedarfAendern(final @NotNull @Valid BedarfId angebotId, BigDecimal anzahl) {
        engineClient.messageKorrelieren(//
                PROZESS_KEY, //
                new BusinessKey(angebotId.getValue()), //
                REST_BEDARF_AENDERN_MESSAGE,
                Variables.createVariables()
                        .putValue(VAR_ANZAHL, anzahl)
        );
    }

    void bedarfSchliessen(final @NotNull @Valid BedarfId bedarfId) {
        engineClient.messageKorrelieren(//
                PROZESS_KEY, //
                new BusinessKey(bedarfId.getValue()), //
                BEDARF_SCHLIESSEN_MESSAGE,
                Variables.createVariables()
                        .putValue(VAR_BEDARF_GESCHLOSSEN, true));
    }

    void anfrageErhalten(
            final @NotNull @Valid BedarfAnfrageId bedarfAnfrageId,
            final @NotNull @Valid BedarfId bedarfId,
            final @NotNull @Valid AngebotId angebotId) {
        engineClient.messageKorrelieren(//
                PROZESS_KEY, //
                new BusinessKey(bedarfId.getValue()), //
                ANFRAGE_MESSAGE,
                Variables.createVariables()
                        .putValue(VAR_ANFRAGE_ID, bedarfAnfrageId.getValue().toString())
                        .putValue(VAR_ANFRAGE_ANGEBOT_ID, angebotId.getValue().toString()));
    }

    void anfrageStornieren(final @NotNull @Valid BedarfAnfrageId bedarfAnfrageId, final @NotNull @Valid BedarfId bedarfId) {
        engineClient.messageKorrelieren(//
                PROZESS_KEY, //
                new BusinessKey(bedarfId.getValue()), //
                Variables.createVariables()
                        .putValue(VAR_ANFRAGE_ID, bedarfAnfrageId.getValue().toString()),
                ANFRAGE_STORNIEREN_MESSAGE,
                Variables.createVariables());
    }

    void anfrageBeantworten(
            final @NotNull @Valid BedarfAnfrageId bedarfAnfrageId,
            final @NotNull @Valid BedarfId bedarfId,
            final @NotNull @Valid Boolean entscheidung,
            final @NotNull @Valid BigDecimal restAnzahl) {
        engineClient.messageKorrelieren(//
                PROZESS_KEY, //
                new BusinessKey(bedarfId.getValue()), //
                Variables.createVariables()
                        .putValue(VAR_ANFRAGE_ID, bedarfAnfrageId.getValue().toString()),
                ANFRAGE_BEARBEITET_MESSAGE,
                Variables.createVariables()
                        .putValue(VAR_BEDARF_ANGENOMMEN, entscheidung)
                        .putValue(VAR_ANZAHL, restAnzahl));
    }
}
