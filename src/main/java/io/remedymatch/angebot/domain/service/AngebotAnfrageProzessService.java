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
			final @NotNull @Valid AngebotAnfrageId anfrageId, //
			final @NotNull @Valid InstitutionId angebotInstitutionId) {
		return engineClient.prozessStarten(//
				PROZESS_KEY, //
				new BusinessKey(anfrageId.getValue()), //
				Variables.createVariables()//
						.putValue("prozessKey", PROZESS_KEY.getValue()) //
						.putValue("angebotId", angebotId.getValue()) //
						.putValue("anfrageId", anfrageId.getValue()) //
						// XXX wozu Institution
						.putValue("angebotInstitutionId", angebotInstitutionId.getValue()) //
						.putValue("institution", angebotInstitutionId.getValue()) //
						.putValue("objektId", anfrageId.toString()));
	}

	void prozesseStornieren(final @NotNull @Valid AngebotId angebotId) {
		engineClient.messageKorrelieren(//
				PROZESS_KEY, //
				ANFRAGE_STORNIEREN_MESSAGE, //
				Variables.createVariables()//
				.putValue("prozessKey", PROZESS_KEY.getValue()) //
				.putValue("angebotId", angebotId.getValue()));
	}

	void prozessStornieren(final @NotNull @Valid AngebotAnfrageId anfrageId) {
		engineClient.messageKorrelieren(//
				PROZESS_KEY, //
				ANFRAGE_STORNIEREN_MESSAGE, //
				Variables.createVariables()//
				.putValue("prozessKey", PROZESS_KEY.getValue()) //
				.putValue("anfrageId", anfrageId.getValue()));
	}
}
