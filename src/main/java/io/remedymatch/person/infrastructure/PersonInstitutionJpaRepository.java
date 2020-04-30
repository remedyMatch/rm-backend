package io.remedymatch.person.infrastructure;

import java.util.UUID;

import io.remedymatch.shared.infrastructure.ReadOnlyRepository;

public interface PersonInstitutionJpaRepository extends ReadOnlyRepository<PersonInstitutionEntity, UUID> {

	PersonInstitutionEntity save(final PersonInstitutionEntity entity);
}
