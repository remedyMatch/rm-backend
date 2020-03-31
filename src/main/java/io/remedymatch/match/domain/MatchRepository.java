package io.remedymatch.match.domain;


import io.remedymatch.institution.domain.InstitutionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepository extends CrudRepository<MatchEntity, UUID> {

    List<MatchEntity> findAllByInstitutionVon(InstitutionEntity institutionVon);

    List<MatchEntity> findAllByInstitutionAn(InstitutionEntity institutionAn);

    List<MatchEntity> finAllByStatus(MatchStatus status);
}


