package io.remedymatch.angebot.controller;

import static io.remedymatch.angebot.controller.AngebotControllerMapper.mapToAngeboteRO;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.angebot.domain.service.AngebotAnlageService;
import io.remedymatch.angebot.domain.service.AngebotService;
import io.remedymatch.angebot.domain.service.AngebotSucheService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/angebot/suche")
@Validated
@Transactional
public class AngebotSucheController {

	private final AngebotSucheService angebotSucheService;

	@Transactional(readOnly = true)
	@GetMapping("/suche")
	public ResponseEntity<List<AngebotRO>> getAlleNichtBedienteAngebote() {
		return ResponseEntity.ok(mapToAngeboteRO(angebotSucheService.findAlleNichtBedienteAngebote()));
	}
}
