package io.remedymatch.angebot.domain.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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

@AllArgsConstructor
@Validated
@Service
@Transactional
class AngebotAnfrageProzessService {

	final static ProzessKey PROZESS_KEY = new ProzessKey("angebot_anfrage_prozess");
	final static MessageKey ANFRAGE_STORNIEREN_MESSAGE = new MessageKey("angebot_anfrage_stornieren");

	private final EngineClient engineClient;

	ProzessInstanzId prozessStarten(//
			final @NotNull @Valid AngebotId angebotId, //
			final @NotNull @Valid PersonId angebotSteller, //
			final @NotNull @Valid AngebotAnfrageId anfrageId, //
			final @NotNull @Valid InstitutionId angebotInstitutionId) {
		return engineClient.prozessStarten(//
				PROZESS_KEY, //
				new BusinessKey(anfrageId.getValue()), //
				angebotSteller, //
				Variables.createVariables()//
						.putValue("prozessKey", PROZESS_KEY.getValue()) //
						.putValue("angebotId", angebotId.getValue()) //
						.putValue("anfrageId", anfrageId.getValue()) //
						// XXX wozu Institution
						.putValue("angebotInstitutionId", angebotInstitutionId.getValue()) //
						.putValue("institution", angebotInstitutionId.getValue()) //
						.putValue("objektId", anfrageId.getValue()));
	}

	void prozesseStornieren(final @NotNull @Valid AngebotId angebotId) {
		engineClient.messageKorrelieren(//
				PROZESS_KEY, //
				ANFRAGE_STORNIEREN_MESSAGE, //
				Variables.createVariables().putValue("angebotId", angebotId.getValue()));
	}

	void prozessStornieren(final @NotNull @Valid ProzessInstanzId prozessInstanzId) {
		engineClient.messageKorrelieren(//
				PROZESS_KEY, //
				prozessInstanzId, //
				ANFRAGE_STORNIEREN_MESSAGE);
	}
}
