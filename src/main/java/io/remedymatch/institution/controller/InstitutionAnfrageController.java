package io.remedymatch.institution.controller;

import static io.remedymatch.institution.controller.InstitutionAnfrageControllerMapper.mapToAnfragenRO;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.institution.domain.service.InstitutionAnfragenSucheService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/institution/anfragen")
public class InstitutionAnfrageController {

	private final InstitutionAnfragenSucheService institutionAnfragenSucheService;

	@Transactional(readOnly = true)
	@GetMapping("/gestellt")
	public ResponseEntity<List<InstitutionAnfrageRO>> gestellteAnfragen() {
		return ResponseEntity
				.ok(mapToAnfragenRO(institutionAnfragenSucheService.getGestellteUserInstitutionAnfragen()));
	}

	@Transactional(readOnly = true)
	@GetMapping("/erhalten")
	public ResponseEntity<List<InstitutionAnfrageRO>> erhalteneAnfragen() {
		return ResponseEntity
				.ok(mapToAnfragenRO(institutionAnfragenSucheService.getErhalteneUserInstitutionAnfragen()));
	}
}
