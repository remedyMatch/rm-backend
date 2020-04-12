package io.remedymatch.person.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.person.domain.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
@RequestMapping("/person")
class PersonController {

	private final PersonRepository personRepository;

	@GetMapping
	public ResponseEntity<List<PersonRO>> alleLaden() {
		return ResponseEntity
				.ok(personRepository.getAlle().stream().map(PersonMapper::mapToPersonRO).collect(Collectors.toList()));
	}

//	@PostMapping
//	public ResponseEntity<PersonRO> create(@RequestBody PersonRO person) {
//		return ResponseEntity.ok(mapToPersonRO(personRepository.update(mapToPerson(person))));
//	}

	@GetMapping("userInfo")
	public ResponseEntity<PersonRO> userInfo() {
		Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		val username = jwt.getClaims().get("sub").toString();
		val person = PersonRO.builder().username(username).build();
		return ResponseEntity.ok(person);
	}
}
