package io.remedymatch.angebot.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import io.remedymatch.angebot.domain.AngebotEntity;

public interface AngebotJpaRepository extends CrudRepository<AngebotEntity, UUID> {

	List<AngebotEntity> findAllByBedientFalse();

}
