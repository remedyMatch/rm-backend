package io.remedymatch.bedarf.domain;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BedarfAnfrageRepository extends CrudRepository<BedarfAnfrageEntity, UUID> {

}


