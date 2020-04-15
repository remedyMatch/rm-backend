package io.remedymatch.bedarf.process;

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

import io.remedymatch.bedarf.domain.service.BedarfAnfrageSucheService;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageTestFixtures;
import io.remedymatch.engine.TaskDTO;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		BedarfAnfrageBearbeitenTaskNameHandler.class, //
		BedarfAnfrageSucheService.class //
})
@Tag("Spring")
@DisplayName("BedarfAnfrageBearbeitenTaskNameHandler soll")
public class BedarfAnfrageBearbeitenTaskNameHandlerShould {

	@Autowired
	private BedarfAnfrageBearbeitenTaskNameHandler taskNameHandler;

	@MockBean
	private BedarfAnfrageSucheService anfrageSucheService;

	@Test
	@DisplayName("TaskKey zurueckliefern")
	void taskKey_zurueckliefern() {

		assertEquals(BedarfAnfrageBearbeitenTaskContstants.TASK_KEY, taskNameHandler.taskKey());

		then(anfrageSucheService).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("fuer Task ein Beschreibungstext mit ArtikelVariante zurueckliefern")
	void fuer_Task_ein_Beschreibungstext_mit_ArtikelVariante_zurueckliefern() {

		val institutionName = "Mein Krankenhaus";
		val bedarfAnzahl = BigDecimal.valueOf(346);
		val artikelName = "Kittel";
		val artikelVarianteName = "S";

		val anfrage = BedarfAnfrageTestFixtures.beispielBedarfAnfrage();
		val anfrageId = anfrage.getId();
		anfrage.getInstitution().setName(institutionName);

		val bedarf = anfrage.getBedarf();
		bedarf.setAnzahl(bedarfAnzahl);
		val artikel = bedarf.getArtikel();
		artikel.setName(artikelName);
		val artikelVariante = bedarf.getArtikelVariante();
		artikelVariante.setVariante(artikelVarianteName);

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

		assertEquals("Mein Krankenhaus: Anfrage zu Bedarf von 346 Kittel - S", taskNameHandler.beschreibung(task));

		then(anfrageSucheService).should().getAnfrageOrElseThrow(anfrageId);
		then(anfrageSucheService).shouldHaveNoMoreInteractions();
	}

	@Test
	@DisplayName("fuer Task ein Beschreibungstext ohne ArtikelVariante zurueckliefern")
	void fuer_Task_ein_Beschreibungstext_ohne_ArtikelVariante_zurueckliefern() {

		val institutionName = "Mein Krankenhaus";
		val bedarfAnzahl = BigDecimal.valueOf(522);
		val artikelName = "Kittel";

		val anfrage = BedarfAnfrageTestFixtures.beispielBedarfAnfrage();
		val anfrageId = anfrage.getId();
		anfrage.getInstitution().setName(institutionName);

		val bedarf = anfrage.getBedarf();
		bedarf.setAnzahl(bedarfAnzahl);
		bedarf.setArtikelVariante(null);
		val artikel = bedarf.getArtikel();
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

		assertEquals("Mein Krankenhaus: Anfrage zu Bedarf von 522 Kittel", taskNameHandler.beschreibung(task));

		then(anfrageSucheService).should().getAnfrageOrElseThrow(anfrageId);
		then(anfrageSucheService).shouldHaveNoMoreInteractions();
	}
}
