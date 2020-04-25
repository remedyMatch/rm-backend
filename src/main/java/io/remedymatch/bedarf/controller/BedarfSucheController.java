package io.remedymatch.bedarf.controller;

import static io.remedymatch.bedarf.controller.BedarfControllerMapper.mapToBedarfeRO;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.bedarf.domain.service.BedarfSucheService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/bedarf/suche")
@Validated
@Transactional
public class BedarfSucheController {

	private final BedarfSucheService bedarfSucheService;

	@Transactional(readOnly = true)
	@GetMapping
	public ResponseEntity<List<BedarfRO>> getAlleNichtBedienteBedarfe() {
		return ResponseEntity.ok(mapToBedarfeRO(bedarfSucheService.findAlleNichtBedienteBedarfe()));
	}
}
