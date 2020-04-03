package io.remedymatch.person.domain;

import io.remedymatch.person.infrastructure.PersonJpaRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.remedymatch.person.domain.PersonEntityConverter.convert;

@Repository
@AllArgsConstructor
@Transactional
public class PersonRepository {

    private final PersonJpaRepository jpaRepository;

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
