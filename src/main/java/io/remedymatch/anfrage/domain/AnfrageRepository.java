package io.remedymatch.anfrage.domain;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnfrageRepository extends CrudRepository<AnfrageEntity, UUID> {

    AnfrageEntity findByProzessInstanzId(String prozessInstanzId);

}


