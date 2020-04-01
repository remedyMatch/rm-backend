package io.remedymatch.bedarf.domain;


import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedarfRepository extends CrudRepository<BedarfEntity, UUID> {
    List<BedarfEntity> findAllByBedientFalse();
	List<BedarfEntity> findAllByInstitution_Id(UUID id);
}


