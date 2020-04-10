package io.remedymatch.bedarf.process;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import io.remedymatch.aufgabe.domain.handler.TaskBeschreibungHandler;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageSucheService;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
class BedarfAnfrageBearbeitenTaskNameHandler implements TaskBeschreibungHandler {

	private static final String BESCHREIBUNG_TEMPLATE_MIT_ARTIKEL_VARIANTE = "%s: Anfrage zu Bedarf von %d %s - %s";
	private static final String BESCHREIBUNG_TEMPLATE_OHNE_ARTIKEL_VARIANTE = "%s: Anfrage zu Bedarf von %d %s";

	private final BedarfAnfrageSucheService anfrageSucheService;

	@Override
	public String taskKey() {
		return BedarfAnfrageBearbeitenTaskContstants.TASK_KEY;
	}

	@Override
	public String beschreibung(final TaskDTO taskDTO) {
		return formatBeschreibungstext(new BedarfAnfrageId(UUID.fromString(taskDTO.getObjektId())));
	}

	private String formatBeschreibungstext(final @NotNull BedarfAnfrageId anfrageId) {
		val anfrage = getAnfrage(anfrageId);
		val bedarf = anfrage.getBedarf();

		if (bedarf.getArtikelVariante() != null) {
			return String.format(BESCHREIBUNG_TEMPLATE_MIT_ARTIKEL_VARIANTE, //
					anfrage.getInstitution().getName(), //
					bedarf.getAnzahl(), //
					bedarf.getArtikel().getName(), //
					bedarf.getArtikelVariante().getVariante());
		}

		return String.format(BESCHREIBUNG_TEMPLATE_OHNE_ARTIKEL_VARIANTE, //
				anfrage.getInstitution().getName(), //
				bedarf.getAnzahl(), //
				bedarf.getArtikel().getName());
	}

	private BedarfAnfrage getAnfrage(final BedarfAnfrageId anfrageId) {
		return anfrageSucheService.getAnfrageOrElseThrow(anfrageId);
	}
}
