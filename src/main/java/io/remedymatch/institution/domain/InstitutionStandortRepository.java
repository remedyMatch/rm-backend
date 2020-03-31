package io.remedymatch.institution.domain;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstitutionStandortRepository extends CrudRepository<InstitutionStandortEntity, UUID> {

}


