package io.remedymatch.bedarf.domain;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BedarfRepository extends CrudRepository<BedarfEntity, UUID> {
    List<BedarfEntity> findAllbyBedientFalse();
}


