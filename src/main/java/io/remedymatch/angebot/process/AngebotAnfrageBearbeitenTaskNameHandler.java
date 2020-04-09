package io.remedymatch.angebot.process;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.service.AngebotAnfrageSucheService;
import io.remedymatch.artikel.domain.model.Artikel;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.aufgabe.domain.handler.TaskBeschreibungHandler;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
public class AngebotAnfrageBearbeitenTaskNameHandler implements TaskBeschreibungHandler {

	private final ArtikelSucheService artikelSucheService;
	private final AngebotAnfrageSucheService anfrageSucheService;

	@Override
	public String taskKey() {
		return AnfrageBearbeitenTaskContstants.TASK_KEY;
	}

	@Override
	public String beschreibung(final TaskDTO taskDTO) {
		return formatBeschreibungstext(new AngebotAnfrageId(UUID.fromString(taskDTO.getObjektId())));
	}

	private String formatBeschreibungstext(final @NotNull AngebotAnfrageId anfrageId) {

		val beschreibungTemplate = "%s: Anfrage zu Angebot von %d %s - %s";

		val anfrage = getAnfrage(anfrageId);
		val angebot = anfrage.getAngebot();
		val artikelVariante = angebot.getArtikelVariante();
		val artikel = getArtikel(artikelVariante.getArtikelId());

		return String.format(beschreibungTemplate, //
				anfrage.getInstitution().getName(), //
				angebot.getAnzahl(), //
				artikel.getName(), //
				artikelVariante.getVariante());

	}

	private AngebotAnfrage getAnfrage(final AngebotAnfrageId anfrageId) {
		return anfrageSucheService.getAnfrageOrElseThrow(anfrageId);
	}

	private Artikel getArtikel(final ArtikelId artikelId) {
		return artikelSucheService.getArtikelOrElseThrow(artikelId);
	}
}
