package io.remedymatch.person.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/person")
@Validated
@Transactional
class PersonController {

	private final UserContextService userContextService;

	@Transactional(readOnly = true)
	@GetMapping
	public ResponseEntity<PersonRO> getUserInfo() {
		return ResponseEntity.ok(PersonControllerMapper.mapToPersonRO(userContextService.getContextUser()));
	}
}
