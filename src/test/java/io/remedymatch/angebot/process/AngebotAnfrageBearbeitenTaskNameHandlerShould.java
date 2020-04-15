package io.remedymatch.angebot.process;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.service.AngebotAnfrageSucheService;
import io.remedymatch.angebot.domain.service.AngebotAnfrageTestFixtures;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.artikel.domain.service.ArtikelTestFixtures;
import io.remedymatch.engine.TaskDTO;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		AngebotAnfrageBearbeitenTaskNameHandler.class, //
		AngebotAnfrageSucheService.class, //
		ArtikelSucheService.class //
})
@Tag("Spring")
@DisplayName("AngebotAnfrageBearbeitenTaskNameHandler soll")
public class AngebotAnfrageBearbeitenTaskNameHandlerShould {

	@Autowired
	private AngebotAnfrageBearbeitenTaskNameHandler taskNameHandler;

	@MockBean
	private AngebotAnfrageSucheService anfrageSucheService;

	@MockBean
	private ArtikelSucheService artikelSucheService;

	@Test
	@DisplayName("TaskKey zurueckliefern")
	void taskKey_zurueckliefern() {

		assertEquals(AngebotAnfrageBearbeitenTaskContstants.TASK_KEY, taskNameHandler.taskKey());

		then(anfrageSucheService).shouldHaveNoInteractions();
		then(artikelSucheService).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("fuer Task ein Beschreibungstext zurueckliefern")
	void fuer_Task_ein_Beschreibungstext_zurueckliefern() {

		val institutionName = "Mein Krankenhaus";
		val angebotAnzahl = BigDecimal.valueOf(125);
		val artikelName = "Kittel";
		val artikelVarianteName = "S";

		val anfrage = AngebotAnfrageTestFixtures.beispielAngebotAnfrage();
		val anfrageId = anfrage.getId();
		anfrage.getInstitution().setName(institutionName);

		val angebot = anfrage.getAngebot();
		angebot.setAnzahl(angebotAnzahl);
		val artikelVariante = angebot.getArtikelVariante();
		val artikelId = artikelVariante.getArtikelId();
		artikelVariante.setVariante(artikelVarianteName);

		val artikel = ArtikelTestFixtures.beispielArtikel();
		artikel.setId(artikelId);
		artikel.setName(artikelName);

		val task = TaskDTO.builder() //
				.taskId("egal") //
				.prozessInstanceId("egal") //
				.institution("egal") //
				.objektId(anfrageId.getValue().toString()) //
				.displayName("egal") //
				.taskKey("egal") //
				.taskName("egal") //
				.build();

		given(anfrageSucheService.getAnfrageOrElseThrow(anfrageId)).willReturn(anfrage);
		given(artikelSucheService.getArtikelOrElseThrow(artikelId)).willReturn(artikel);

		assertEquals("Mein Krankenhaus: Anfrage zu Angebot von 125 Kittel - S", taskNameHandler.beschreibung(task));

		then(anfrageSucheService).should().getAnfrageOrElseThrow(anfrageId);
		then(anfrageSucheService).shouldHaveNoMoreInteractions();
		then(artikelSucheService).should().getArtikelOrElseThrow(artikelId);
		then(artikelSucheService).shouldHaveNoMoreInteractions();
	}
}
