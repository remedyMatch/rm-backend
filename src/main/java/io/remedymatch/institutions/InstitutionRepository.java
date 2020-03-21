package io.remedymatch.institutions;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstitutionRepository extends CrudRepository<InstitutionEntity, UUID> {

    InstitutionEntity findByInstitutionKey(String institutionKey);

}


