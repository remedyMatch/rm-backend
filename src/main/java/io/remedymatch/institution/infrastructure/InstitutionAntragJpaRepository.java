package io.remedymatch.institution.infrastructure;

import io.remedymatch.shared.infrastructure.ReadOnlyRepository;

import java.util.List;
import java.util.UUID;

public interface InstitutionAntragJpaRepository extends ReadOnlyRepository<InstitutionAntragEntity, UUID> {

    InstitutionAntragEntity save(final InstitutionAntragEntity entity);

    List<InstitutionAntragEntity> findAllByAntragsteller(UUID antragsteller);

}
