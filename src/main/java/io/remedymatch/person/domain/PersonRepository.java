package io.remedymatch.person.domain;

import static io.remedymatch.person.domain.PersonEntityConverter.convert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import io.remedymatch.person.infrastructure.PersonJpaRepository;

@Repository
public class PersonRepository {
	@Autowired
	private PersonJpaRepository jpaRepository;

	public List<Person> getAlle() {
		return jpaRepository.findAll().stream().map(PersonEntityConverter::convert).collect(Collectors.toList());
	}

	public Optional<Person> findByUsername(final String username) {
		Assert.isTrue(StringUtils.isNotBlank(username), "Username ist blank");

		return jpaRepository.findByUsername(username).map(PersonEntityConverter::convert);
	}

	public Person add(final Person person) {
		Assert.notNull(person, "person ist null");

		return convert(jpaRepository.save(convert(person)));
	}

	public Person update(final Person person) {
		Assert.notNull(person, "Person ist null");

		return convert(jpaRepository.save(convert(person)));
	}
}
