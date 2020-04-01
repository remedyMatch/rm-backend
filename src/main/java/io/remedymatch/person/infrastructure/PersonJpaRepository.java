package io.remedymatch.person.infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonJpaRepository extends CrudRepository<PersonEntity, UUID> {
	Optional<PersonEntity> findByUsername(String userName);
}
