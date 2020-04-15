package io.remedymatch.person.domain.service;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Validated
@Service
public class PersonSucheService {

	private final PersonJpaRepository jpaRepository;

	@Transactional(readOnly = true)
	public Optional<Person> findByUsername(final @NotBlank String username) {
		return jpaRepository.findByUsername(username).map(PersonEntityConverter::convert);
	}
}
