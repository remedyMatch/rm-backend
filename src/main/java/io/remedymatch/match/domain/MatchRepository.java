package io.remedymatch.match.domain;


import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.remedymatch.institution.infrastructure.InstitutionEntity;

@Repository
public interface MatchRepository extends CrudRepository<MatchEntity, UUID> {

    List<MatchEntity> findAllByInstitutionVon(InstitutionEntity institutionVon);

    List<MatchEntity> findAllByInstitutionAn(InstitutionEntity institutionAn);

    List<MatchEntity> findAllByStatus(MatchStatus status);
}


