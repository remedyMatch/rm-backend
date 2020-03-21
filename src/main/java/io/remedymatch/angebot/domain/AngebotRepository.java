package io.remedymatch.angebot.domain;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AngebotRepository extends CrudRepository<AngebotEntity, UUID> {

}


