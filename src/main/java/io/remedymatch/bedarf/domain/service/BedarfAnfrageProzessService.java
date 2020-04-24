package io.remedymatch.bedarf.domain.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.model.BedarfId;
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
class BedarfAnfrageProzessService {

	final static ProzessKey PROZESS_KEY = new ProzessKey("bedarf_anfrage_prozess");
	final static MessageKey ANFRAGE_STORNIEREN_MESSAGE = new MessageKey("bedarf_anfrage_stornieren");

	private final EngineClient engineClient;

	ProzessInstanzId prozessStarten(//
			final @NotNull @Valid BedarfId bedarfId, //
			final @NotNull @Valid PersonId bedarfSteller, //
			final @NotNull @Valid BedarfAnfrageId anfrageId, //
			final @NotNull @Valid InstitutionId bedarfInstitutionId) {
		return engineClient.prozessStarten(//
				PROZESS_KEY, //
				new BusinessKey(anfrageId.getValue()), //
				bedarfSteller, //
				Variables.createVariables()//
						.putValue("prozessKey", PROZESS_KEY.getValue()) //
						.putValue("bedarfId", bedarfId.getValue()) //
						.putValue("anfrageId", anfrageId.getValue()) //
						// XXX wozu Institution
						.putValue("bedarfInstitutionId", bedarfInstitutionId.getValue()) //
						.putValue("institution", bedarfInstitutionId.getValue()) //
						.putValue("objektId", anfrageId.getValue()));
	}

	void prozesseStornieren(final @NotNull @Valid BedarfId bedarfId) {
		engineClient.messageKorrelieren(//
				PROZESS_KEY, //
				ANFRAGE_STORNIEREN_MESSAGE, //
				Variables.createVariables().putValue("bedarfId", bedarfId.getValue()));
	}

	void prozessStornieren(final @NotNull @Valid ProzessInstanzId prozessInstanzId) {
		engineClient.messageKorrelieren(//
				PROZESS_KEY, //
				prozessInstanzId, //
				ANFRAGE_STORNIEREN_MESSAGE);
	}
}
