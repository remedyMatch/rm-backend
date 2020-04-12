package io.remedymatch.person.infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonJpaRepository extends JpaRepository<PersonEntity, UUID> {
	Optional<PersonEntity> findByUsername(final String userName);
}
