package io.remedymatch.person.infrastructure;

import java.util.Optional;
import java.util.UUID;

import io.remedymatch.shared.infrastructure.ReadOnlyRepository;

public interface PersonJpaRepository extends ReadOnlyRepository<PersonEntity, UUID> {

	PersonEntity save(final PersonEntity entity);

	Optional<PersonEntity> findOneByUsername(final String userName);
}
